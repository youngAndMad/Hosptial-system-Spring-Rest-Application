package young.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import young.hospital.dto.AppointmentDTO;
import young.hospital.dto.DoctorDTO;
import young.hospital.dto.PatientComplaint;
import young.hospital.exceptions.AppointmentException;
import young.hospital.exceptions.DoctorException;
import young.hospital.exceptions.PatientComplaintException;
import young.hospital.exceptions.PatientException;
import young.hospital.model.Appointment;
import young.hospital.model.DoctorRole;
import young.hospital.services.AppointmentService;
import young.hospital.services.DoctorService;
import young.hospital.services.PatientService;
import young.hospital.utils.Converter;
import young.hospital.utils.ErrorResponse;
import young.hospital.validate.AppointmentValidate;

import java.util.List;
import java.util.stream.Collectors;

import static young.hospital.utils.Converter.toDoctorDTO;
import static young.hospital.utils.Converter.toErrorResponse;


@RestController
@AllArgsConstructor
@RequestMapping("appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentValidate appointmentValidate;

    @GetMapping("/add")
    public ResponseEntity<DoctorDTO> requestToRegisterAppointment(@RequestBody @Valid PatientComplaint patientComplaint, BindingResult bindingResult) {
        appointmentValidate.validate(patientComplaint, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new PatientComplaintException(toErrorResponse(bindingResult));
        }
        if (doctorService.getByRole(DoctorRole.valueOf(patientComplaint.getDoctorRole())).isEmpty()) {
            throw new PatientComplaintException("at the moment our hospital does not have free doctors in the referral " + patientComplaint.getDoctorRole());
        }
        DoctorDTO recommendedDoctor = toDoctorDTO(appointmentService.getRecommendedDoctor(patientComplaint));
        return  ResponseEntity.ok().header("expected price" ,
                String.valueOf(DoctorRole.valueOf(recommendedDoctor.getRole()).getPrice()
                        * recommendedDoctor.getExperience() * 0.5))
                .body(recommendedDoctor);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody PatientComplaint patientComplaint , @RequestParam boolean accepted ) {
        if(!accepted){
            return ResponseEntity.ok().body(new ErrorResponse(
                    "appointment canceled",
                    System.currentTimeMillis()
            ));
        }
        appointmentService.save(patientComplaint);
        return ResponseEntity.ok( "appointment registered");
    }


    @GetMapping("/all")
    public List<AppointmentDTO> getAll() {
        return appointmentService.getAll().stream().map(Converter::toAppointmentDTO).collect(Collectors.toList());
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<HttpStatus> attachResultToAppointment(@PathVariable Long id , @RequestBody String result){
       Appointment appointment = appointmentService.getAppointmentById(id);
       if (appointment == null){
           throw new AppointmentException("invalid id property for appointment");
       }
       appointmentService.saveResultsOfAppointment(appointment , result);
       return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<?> getPatientAppointments(@PathVariable Long id){
        if (patientService.getById(id) == null){
            throw new PatientException("patient by id does not found");
        }
        return new ResponseEntity<>(appointmentService.getAppointmentsByPatientId(id) , HttpStatus.OK);
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<?> getDoctorAppointments(@PathVariable Long id){
         if (doctorService.getById(id) == null){
            throw new DoctorException("doctor by id does not found");
        }
        return new ResponseEntity<>(appointmentService.getAppointmentsByDoctorId(id) , HttpStatus.OK);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandle(PatientComplaintException exception) {
        return new ResponseEntity<>(new ErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        ), HttpStatus.BAD_REQUEST);
    }

}
