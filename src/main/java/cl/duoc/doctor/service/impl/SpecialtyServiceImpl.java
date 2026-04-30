package cl.duoc.doctor.service.impl;

import cl.duoc.doctor.dto.SpecialtyResponseDto;
import cl.duoc.doctor.model.Specialty;
import cl.duoc.doctor.repository.SpecialtyRepository;
import cl.duoc.doctor.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository repository;

    Specialty toEntity(SpecialtyResponseDto dto) {
        return new Specialty(
                dto.getId(),
                dto.getName(),
                dto.getDescription()
        );
    }

    SpecialtyResponseDto toDto(Specialty entity) {
        return new SpecialtyResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    @Override
    public List<SpecialtyResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public SpecialtyResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }
}
