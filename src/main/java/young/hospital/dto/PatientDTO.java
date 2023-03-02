package young.hospital.dto;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.collection.spi.PersistentSet;
import young.hospital.model.Appointment;

import java.util.Set;

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

    private Set<AppointmentDTO> appointments;
}

