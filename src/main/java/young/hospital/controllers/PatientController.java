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
import young.hospital.services.AppointmentService;
import young.hospital.services.PatientService;
import young.hospital.utils.Converter;
import young.hospital.utils.ErrorResponse;
import young.hospital.utils.Response;
import young.hospital.validate.PatientValidate;

import java.util.List;
import java.util.stream.Collectors;

import static young.hospital.utils.Converter.*;

@RestController
@AllArgsConstructor
@RequestMapping("patient")
public class PatientController {
    private final PatientService patientService;
    private final PatientValidate patientValidate;
    private  final Converter converter;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult) {
        Patient patient = toPatient(patientDTO);
        patientValidate.validate(patient, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new PatientException(toErrorResponse(bindingResult));
        }
        patientService.add(patient);
        return ResponseEntity.ok()
                .header("response", "patient was added").
                body(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> getAll() {
        return new ResponseEntity<>(converter.toPatientDTOList(patientService.getAll()), HttpStatus.OK);
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
