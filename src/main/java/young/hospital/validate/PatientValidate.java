package young.hospital.validate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import young.hospital.dto.PatientDTO;
import young.hospital.model.Patient;

import java.util.Locale;

@Component
@AllArgsConstructor
public class PatientValidate implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Patient.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PatientDTO patient = (PatientDTO) target;

        if(patient.getGender().compareToIgnoreCase("MALE") !=0 && patient.getGender().compareToIgnoreCase("FEMALE") !=0){
            errors.rejectValue("gender" , "please specify valid gender");
        }else {
            patient.setGender(patient.getGender().toUpperCase(Locale.ROOT));
        }

        if (patient.getAge() < 18 ){
            errors.rejectValue("age" , "age should be greater than 18");
        }
     }
}
