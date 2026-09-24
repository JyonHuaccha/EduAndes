package pe.edu.EduAndes.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoRequestDTO {

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El código del curso es obligatorio")
    @Pattern(regexp = "^[A-Z]{2}\\d{3}$", message = "El código debe cumplir con el patrón de 2 letras mayúsculas y 3 números (ej. CS101)")
    private String codigo;

    @NotNull(message = "Los créditos son obligatorios")
    @Min(value = 1, message = "El curso debe tener al menos 1 crédito")
    @Max(value = 6, message = "El curso no puede tener más de 6 créditos")
    private Integer creditos;

    @NotNull(message = "El costo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El costo debe ser mayor a 0")
    private BigDecimal costo;

    @NotNull(message = "Las vacantes son obligatorias")
    @Min(value = 0, message = "Las vacantes no pueden ser negativas")
    private Integer vacantes;

    @NotNull(message = "El ciclo es obligatorio")
    @Min(value = 1, message = "El ciclo debe ser entre 1 y 10")
    @Max(value = 10, message = "El ciclo debe ser entre 1 y 10")
    private Integer ciclo;

    @NotNull(message = "El ID de la carrera es obligatorio")
    private Long carreraId;
}