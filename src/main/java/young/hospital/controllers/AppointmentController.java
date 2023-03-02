package young.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import young.hospital.dto.AppointmentDTO;
import young.hospital.dto.PatientComplaint;
import young.hospital.exceptions.AppointmentException;
import young.hospital.exceptions.PatientComplaintException;
import young.hospital.model.Appointment;
import young.hospital.model.DoctorRole;
import young.hospital.services.AppointmentService;
import young.hospital.services.DoctorService;
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
    private final AppointmentValidate appointmentValidate;

    @GetMapping("/add")
    public ResponseEntity<?> requestToRegisterAppointment(@RequestBody @Valid PatientComplaint patientComplaint, BindingResult bindingResult) {
        appointmentValidate.validate(patientComplaint, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new PatientComplaintException(toErrorResponse(bindingResult));
        }
        if (doctorService.getByRole(DoctorRole.valueOf(patientComplaint.getDoctorRole())).isEmpty()) {
            throw new PatientComplaintException("at the moment our hospital does not have free doctors in the referral " + patientComplaint.getDoctorRole());
        }
        return ResponseEntity.ok().header("doctor properties" ,toDoctorDTO(appointmentService.getRecommendedDoctor(patientComplaint)).toString()).build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody PatientComplaint patientComplaint , @RequestParam boolean accepted ) {
        if(!accepted){
            return ResponseEntity.ok().body(new ErrorResponse(
                    "appointment canceled",
                    System.currentTimeMillis()
            ));
        }
        appointmentService. save(patientComplaint);
        return ResponseEntity.ok( "appointment registered");
    }


    @GetMapping("/all")
    public List<AppointmentDTO> getAll() {
        return appointmentService.getAll().stream().map(Converter::toAppointmentDTO).collect(Collectors.toList());
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<HttpStatus> attachResultToAppointment(@PathVariable Long id , @RequestParam String result){
       Appointment appointment = appointmentService.getAppointmentById(id);
       if (appointment == null){
           throw new AppointmentException("invalid id property for appointment");
       }
       appointmentService.saveResultsOfAppointment(appointment , result);
       return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandle(PatientComplaintException exception) {
        return new ResponseEntity<>(new ErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        ), HttpStatus.BAD_REQUEST);
    }

}
