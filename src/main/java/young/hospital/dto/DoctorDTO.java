package young.hospital.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import young.hospital.model.DoctorRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    @Size(min = 2, message = "doctor name should be between 2 - 30 characters", max = 30)
    private String name;

    @Size(min = 2, message = "doctor surname should be between 2 - 30 characters", max = 30)
    private String surname;

    @Min(value = 20, message = "doctor age should be greater than 20")
    @Max(value = 65, message = "doctor age should be less than 65")
    private int age;

    @NotBlank
    private String gender;

    @Column(name = "experience")
    private int experience;

    private String role;
}
