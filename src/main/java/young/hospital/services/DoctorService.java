package young.hospital.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import young.hospital.model.Doctor;
import young.hospital.model.DoctorRole;
import young.hospital.repositories.DoctorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public Doctor save(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAll(){
        return doctorRepository.findAll();
    }

    public void deleteById(Long id){
        doctorRepository.deleteById(id);
    }
    public Doctor getById(Long id){
       return doctorRepository.findById(id).orElse(null);
    }
    public List<Doctor> getByRole(DoctorRole role){
        return doctorRepository.getDoctorsByRole(role);
    }
}
