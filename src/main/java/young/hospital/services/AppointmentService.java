package young.hospital.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import young.hospital.dto.AppointmentDTO;
import young.hospital.dto.PatientComplaint;
import young.hospital.model.Appointment;
import young.hospital.model.Doctor;
import young.hospital.model.DoctorRole;
import young.hospital.repositories.AppointmentRepository;
import young.hospital.repositories.DoctorRepository;
import young.hospital.repositories.PatientRepository;
import young.hospital.utils.Converter;

import java.util.*;

import static young.hospital.utils.Converter.toAppointment;


@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public void save(PatientComplaint patientComplaint) {
        appointmentRepository.save(
                toAppointment(
                        patientComplaint,
                        patientRepository.findById(patientComplaint.getPatientId()).get(),
                        getRecommendedDoctor(patientComplaint)
                ));
    }

    public Doctor getRecommendedDoctor(PatientComplaint patientComplaint){
        return  doctorRepository.getDoctorsByRole(
                        DoctorRole.valueOf(
                                patientComplaint.getDoctorRole()
                        )).
                stream().
                sorted((a, b) -> 0).
                findAny().
                get();
    }

    public Appointment getAppointmentById(Long id){
        return appointmentRepository.findById(id).orElse(null);
    }

    public void saveResultsOfAppointment(Appointment appointment, String result) {
        appointment.setResult(result);
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAll(){
        return appointmentRepository.findAll();
    }

    public Set<Appointment> getAppointmentsByPatientId(Long id) {
        return new HashSet<>(appointmentRepository.getAppointmentsByPatientId(id));
    }
}
