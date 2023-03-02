package young.hospital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import young.hospital.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient , Long> {

}
