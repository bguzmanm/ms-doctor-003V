package cl.duoc.doctor.service;

import cl.duoc.doctor.dto.DoctorResponseDto;

import java.util.List;

public interface DoctorService {
    List<DoctorResponseDto> findAll();
    DoctorResponseDto findById(Long id);
}
