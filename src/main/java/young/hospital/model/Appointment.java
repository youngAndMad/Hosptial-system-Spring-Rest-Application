package young.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Table(name = "appointment")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date",nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "patient_id" ,referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id" ,referencedColumnName = "id")
    private Doctor doctor;

    @Column(name = "result")
    private String result;

    @Column(name = "patient_complaints")
    @NotBlank
    private String patientСomplaints;

    @Column(name = "price")
    private int price;

}
