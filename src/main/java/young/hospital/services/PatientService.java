package young.hospital.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import young.hospital.model.Patient;
import young.hospital.repositories.PatientRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    public Patient add(Patient patient){
        return patientRepository.save(patient);
    }

    public List<Patient> getAll(){
        return patientRepository.findAll();
    }

    public Patient getById(Long id){
      return  patientRepository.findById(id).orElse(null);
    }
    public void delete(Long id){
        patientRepository.deleteById(id);
    }

}
