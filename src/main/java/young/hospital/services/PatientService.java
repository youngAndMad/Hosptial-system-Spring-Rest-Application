package young.hospital.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import young.hospital.dto.AppointmentDTO;
import young.hospital.model.Appointment;
import young.hospital.model.Patient;
import young.hospital.repositories.AppointmentRepository;
import young.hospital.repositories.PatientRepository;
import young.hospital.utils.Converter;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    public void add(Patient patient){
        patientRepository.save(patient);
    }

    public List<Patient> getAll(){
        List<Patient> patients = patientRepository.findAll();
        return patients;
    }

    public Patient getById(Long id){
      return  patientRepository.findById(id).orElse(null);
    }
    public void delete(Long id){
        patientRepository.deleteById(id);
    }

}
