package cl.duoc.doctor.controller;

import cl.duoc.doctor.dto.DoctorResponseDto;
import cl.duoc.doctor.service.DoctorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @GetMapping
    public List<DoctorResponseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DoctorResponseDto findById(@PathVariable Long id) {
        return  service.findById(id);
    }
}
