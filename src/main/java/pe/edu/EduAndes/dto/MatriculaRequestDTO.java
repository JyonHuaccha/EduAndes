package pe.edu.EduAndes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatriculaRequestDTO {

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId;

    @NotBlank(message = "El periodo es obligatorio")
    @Pattern(regexp = "^\\d{4}-[12]$", message = "El periodo debe tener el formato YYYY-1 o YYYY-2 (ej. 2026-1)")
    private String periodo;

    @NotEmpty(message = "Debe incluir al menos un curso para matricular")
    private List<Long> cursosIds;
}