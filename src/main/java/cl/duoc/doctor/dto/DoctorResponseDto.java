package cl.duoc.doctor.dto;

import cl.duoc.doctor.model.Specialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DoctorResponseDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    private String email;
    private String phone;
    private SpecialtyResponseDto specialty;
}
