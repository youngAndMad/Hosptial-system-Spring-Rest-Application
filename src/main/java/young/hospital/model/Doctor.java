package young.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctor")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(min = 2, message = "doctor name should be between 2 - 30 characters", max = 30)
    private String name;

    @Column(name = "surname", nullable = false)
    @Size(min = 2, message = "doctor surname should be between 2 - 30 characters", max = 30)
    private String surname;

    @Column(name = "age", nullable = false)
    @Min(value = 20, message = "doctor age should be greater than 20")
    @Max(value = 65, message = "doctor age should be less than 65")
    private int age;

    @Column(name = "gender", nullable = false)
    @NotBlank
    private String gender;

    @Column(name = "experience")
    private int experience;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private DoctorRole role;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @OneToMany(mappedBy = "doctor" , cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    @PrePersist
    public void prePersist(){
        this.addedAt = LocalDateTime.now();
        this.appointments = new HashSet<>();
    }

}

