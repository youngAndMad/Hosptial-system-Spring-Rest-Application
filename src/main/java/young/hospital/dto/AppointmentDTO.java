package young.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private LocalDate date;

    private String patientFullName;

    private String doctorFullName;

    private String result;

    private String patientСomplaints;

    private int price;
}
