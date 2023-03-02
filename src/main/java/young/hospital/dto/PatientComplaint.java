package young.hospital.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientComplaint {

    @Min(value = 1 , message = "please specify valid id")
    private Long patientId;

    @NotBlank
    @Size(min = 10 , message = "please fill out your complaints clearly")
    private String patientСomplaints;

    @NotBlank
    private String doctorRole;

}
