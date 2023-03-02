package young.hospital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import young.hospital.model.Doctor;
import young.hospital.model.DoctorRole;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor , Long> {
    List<Doctor> getDoctorsByRole(DoctorRole doctorRole);
}
