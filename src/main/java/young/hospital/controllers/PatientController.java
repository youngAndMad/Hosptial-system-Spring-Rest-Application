package young.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import young.hospital.dto.PatientDTO;
import young.hospital.exceptions.PatientComplaintException;
import young.hospital.exceptions.PatientException;
import young.hospital.model.Patient;
import young.hospital.services.PatientService;
import young.hospital.utils.Converter;
import young.hospital.utils.ErrorResponse;
import young.hospital.utils.Response;
import young.hospital.validate.PatientValidate;

import java.util.List;

import static young.hospital.utils.Converter.*;

@RestController
@AllArgsConstructor
@RequestMapping("patient")
public class PatientController {
    private final PatientService patientService;
    private final PatientValidate patientValidate;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult) {
        Patient patient = toPatient(patientDTO);
        patientValidate.validate(patient, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new PatientException(toErrorResponse(bindingResult));
        }
        patientService.add(patient);
        return new  ResponseEntity<>(new Response("patient was added") , HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> getAll() {
        return new ResponseEntity<>(patientService.getAll().stream().map(Converter::toPatientDTO).toList(), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id , @RequestBody @Valid PatientDTO patientDTO,
                                           BindingResult bindingResult){
        Patient patient = patientService.getById(id);
        if (patient == null){
            throw new PatientException("patient by id does not found");
        }
        patientValidate.validate(patientDTO , bindingResult);
        if (bindingResult.hasErrors()){
            throw new PatientException(toErrorResponse(bindingResult));
        }
        return new ResponseEntity<>(toPatientDTO(patientService.add(updatedPatient(patient , patientDTO))) , HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public PatientDTO getByID(@PathVariable Long id){
        Patient patient = patientService.getById(id);
        if (patient == null) {
            throw new PatientException("patient by id does not found");
        }
        return toPatientDTO(patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Patient patient = patientService.getById(id);
        if (patient == null) {
            throw new PatientComplaintException("patient with " + id + " doesn't found");
        }
        patientService.delete(id);
        return new ResponseEntity<>(new Response("patient was deleted"), HttpStatus.OK);
    }


    @ExceptionHandler
    public ResponseEntity<?> exceptionHandler(PatientException exception) {
        return new ResponseEntity<>(new ErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        ), HttpStatus.BAD_REQUEST);
    }

}
