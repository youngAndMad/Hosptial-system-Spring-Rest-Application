package young.hospital.validate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import young.hospital.dto.DoctorDTO;
import young.hospital.model.Doctor;
import young.hospital.model.DoctorRole;

import java.util.Arrays;
import java.util.Locale;

@Component
@AllArgsConstructor
public class DoctorValidate implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Doctor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Doctor doctor = (Doctor) target;
        if (doctor.getRole() == null ||
                Arrays.stream(DoctorRole.values())
                        .noneMatch(doctorRole -> String.valueOf(doctorRole).equals(doctor.getRole()))) {
            errors.rejectValue("role", "invalid role property");
        }
        if (doctor.getAge() < 20) {
            errors.rejectValue("age", "minimum age 20");
        }
        if (doctor.getExperience() < 2) {
            errors.rejectValue("experience", "doctor experience should be more than 2 years");
        }
        if (doctor.getGender().compareToIgnoreCase("MALE")!=0 ||
                doctor.getGender().compareToIgnoreCase("FEMALE")!=0){
            errors.rejectValue("gender" , "invalid gender type");
        }else {
            doctor.setGender(doctor.getGender().toUpperCase(Locale.ROOT));
        }
    }

}

