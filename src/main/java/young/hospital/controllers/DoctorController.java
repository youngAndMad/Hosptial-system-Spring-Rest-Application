package young.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import young.hospital.dto.DoctorDTO;
import young.hospital.exceptions.DoctorException;
import young.hospital.model.Doctor;
import young.hospital.services.DoctorService;
import young.hospital.utils.Converter;
import young.hospital.utils.ErrorResponse;
import young.hospital.utils.Response;
import young.hospital.validate.DoctorValidate;

import java.util.List;
import java.util.stream.Collectors;

import static young.hospital.utils.Converter.*;


@RestController
@AllArgsConstructor
@RequestMapping("doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorValidate doctorValidate;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid DoctorDTO doctorDTO
            , BindingResult bindingResult) {
        Doctor doctor = toDoctor(doctorDTO);
        doctorValidate.validate(doctor, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new DoctorException(toErrorResponse(bindingResult));
        }
        doctorService.save(doctor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<DoctorDTO> getAll() {
        return doctorService.getAll().stream().map(Converter::toDoctorDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable Long id) {
        Doctor doctor = doctorService.getById(id);
        if(doctor == null){
            throw new DoctorException("doctor does not found. Incorrect id");
        }
        doctorService.deleteById(id);
        return ResponseEntity
                .ok()
                .body(new Response("doctor " + doctor.getName() + " " + doctor.getSurname() + " was deleted"));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(DoctorException doctorException) {
        ErrorResponse errorResponse = new ErrorResponse(
                doctorException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



}
