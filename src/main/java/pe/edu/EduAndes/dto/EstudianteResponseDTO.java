package pe.edu.EduAndes.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteResponseDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String codigo;
    private String dni;
    private String correo;
    private Boolean estado;
    private Long carreraId;
    private String carreraNombre;
}