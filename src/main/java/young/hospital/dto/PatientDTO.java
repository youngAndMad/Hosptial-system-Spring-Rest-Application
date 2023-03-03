package young.hospital.dto;

import jakarta.validation.constraints.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    @Size(min = 2, message = "patient name should be between 2 - 30 characters", max = 30)
    private String name;

    @Size(min = 2, message = "patient surname should be between 2 - 30 characters", max = 30)
    private String surname;

    @Min(value = 0, message = "patient age should be greater than 0")
    @Max(value = 120, message = "patient age should be less than 120")
    private int age;

    @NotBlank
    private String gender;

}

