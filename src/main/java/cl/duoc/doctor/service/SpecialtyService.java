package cl.duoc.doctor.service;

import cl.duoc.doctor.dto.SpecialtyResponseDto;

import java.util.List;

public interface SpecialtyService {
    List<SpecialtyResponseDto> findAll();
    SpecialtyResponseDto findById(Long id);
}
