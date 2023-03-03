package young.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patient")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(min = 2, message = "patient name should be between 2 - 30 characters", max = 30)
    private String name;

    @Column(name = "surname", nullable = false)
    @Size(min = 2, message = "patient surname should be between 2 - 30 characters", max = 30)
    private String surname;

    @Column(name = "age", nullable = false)
    @Min(value = 0, message = "patient age should be greater than 0")
    @Max(value = 120, message = "patient age should be less than 120")
    private int age;

    @Column(name = "gender", nullable = false)
    @NotBlank
    private String gender;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "patient" , orphanRemoval = true , fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Set<Appointment> appointments;

    @PrePersist
    public void prePersist(){
        this.addedAt = LocalDateTime.now();
        this.appointments = new HashSet<>();
    }


}

