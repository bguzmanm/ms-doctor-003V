package cl.duoc.doctor.service.impl;

import cl.duoc.doctor.dto.DoctorResponseDto;
import cl.duoc.doctor.dto.SpecialtyResponseDto;
import cl.duoc.doctor.model.Doctor;
import cl.duoc.doctor.model.Specialty;
import cl.duoc.doctor.repository.DoctorRepository;
import cl.duoc.doctor.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;

    DoctorResponseDto toDto(Doctor entity) {
        return new DoctorResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone(),
                toDto(entity.getSpecialty())
        );
    }

    SpecialtyResponseDto toDto(Specialty entity) {
        return new SpecialtyResponseDto(
                entity.getId(), entity.getName(), entity.getDescription()
        );
    }

    Doctor toEntity(DoctorResponseDto dto) {
        return new Doctor(
                dto.getId(),
                dto.getName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhone(),
                toEntity(dto.getSpecialty())
        );
    }

    Specialty toEntity(SpecialtyResponseDto dto) {
        return new Specialty(
                dto.getId(), dto.getName(), dto.getDescription()
        );
    }


    @Override
    public List<DoctorResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public DoctorResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }
}
