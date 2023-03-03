package young.hospital.utils;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import young.hospital.dto.AppointmentDTO;
import young.hospital.dto.DoctorDTO;
import young.hospital.dto.PatientComplaint;
import young.hospital.dto.PatientDTO;
import young.hospital.model.Appointment;
import young.hospital.model.Doctor;
import young.hospital.model.DoctorRole;
import young.hospital.model.Patient;
import young.hospital.services.AppointmentService;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Converter {
    private final static ModelMapper modelMapper = new ModelMapper();
    private final AppointmentService appointmentService;

    public static Doctor toDoctor(DoctorDTO doctorDTO) {
        return modelMapper.map(doctorDTO, Doctor.class);
    }

    public static DoctorDTO toDoctorDTO(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    public static Patient toPatient(PatientDTO patientDTO) {
        return modelMapper.map(patientDTO, Patient.class);
    }

    public static PatientDTO toPatientDTO(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    public static String toErrorResponse(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error : errors) {
            sb.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append("; ");

        }
        return sb.toString();
    }

    public static Appointment toAppointment(PatientComplaint patientComplaint, Patient patient, Doctor doctor) {
        Appointment appointment = new Appointment();
        appointment.setPrice(DoctorRole.valueOf(patientComplaint.getDoctorRole()).getPrice());
        appointment.setPatient(patient);
        appointment.setDate(LocalDate.of(2023, 3, 2)); // TODO
        appointment.setPatientComplaints(patientComplaint.getPatientСomplaints());
        appointment.setPrice((int) (doctor.getExperience() * doctor.getRole().getPrice() * 0.5));
        appointment.setDoctor(doctor);
        return appointment;
    }

    public static AppointmentDTO toAppointmentDTO(Appointment appointment) {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            System.out.println(appointment.getPatient());
            System.out.println(appointment.getDoctor());
            appointmentDTO.setDate(appointment.getDate());
            appointmentDTO.setResult(appointment.getResult());
            appointmentDTO.setPatientFullName(appointment.getPatient().getName() + " " + appointment.getPatient().getSurname());
            appointmentDTO.setDoctorFullName(appointment.getDoctor().getName() + " " + appointment.getDoctor().getSurname());
            appointmentDTO.setPrice(appointment.getPrice());
            appointmentDTO.setPatientСomplaints(appointment.getPatientComplaints());
           return appointmentDTO;
    }

    public static Doctor updatedDoctor(Doctor doctor , DoctorDTO doctorDTO){
        doctor.setName(doctorDTO.getName());
        doctor.setSurname(doctorDTO.getSurname());
        doctor.setGender(doctorDTO.getGender());
        doctor.setAge(doctorDTO.getAge());
        doctor.setExperience(doctorDTO.getExperience());
        doctor.setUpdatedAt(LocalDateTime.now());
        doctor.setRole(DoctorRole.valueOf(doctorDTO.getRole()));
        return doctor;
    }

}
