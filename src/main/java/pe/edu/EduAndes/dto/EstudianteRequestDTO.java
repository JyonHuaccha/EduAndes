package pe.edu.EduAndes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "Los nombres no deben exceder los 100 caracteres")
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "Los apellidos no deben exceder los 100 caracteres")
    private String apellidos;

    @NotBlank(message = "El código es obligatorio")
    @Size(min = 6, max = 10, message = "El código debe tener entre 6 y 10 caracteres")
    private String codigo;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener exactamente 8 dígitos numéricos")
    private String dni;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String correo;

    private Boolean estado;

    @NotNull(message = "El ID de la carrera es obligatorio")
    private Long carreraId;
}