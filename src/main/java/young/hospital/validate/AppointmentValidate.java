package young.hospital.validate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import young.hospital.dto.PatientComplaint;
import young.hospital.model.DoctorRole;
import young.hospital.services.PatientService;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class AppointmentValidate implements Validator {

    private final PatientService patientService;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        PatientComplaint patientComplaint = (PatientComplaint) target;
        System.out.println(patientComplaint);
        if (patientService.getById(patientComplaint.getPatientId()) == null) {
            errors.rejectValue("id", "invalid patient id");
        }
        if (patientComplaint.getDoctorRole() == null ||
                Arrays.stream(DoctorRole.values()).map(String::valueOf).noneMatch(role -> role.equals(patientComplaint.getDoctorRole()))) {
            errors.rejectValue("doctorRole", "invalid doctor role property");
        }
        if (patientComplaint.getPatientСomplaints().length() < 8) {
            errors.rejectValue("patient complaints", "please fill out your complaints more detailed");
        }
    }
}
