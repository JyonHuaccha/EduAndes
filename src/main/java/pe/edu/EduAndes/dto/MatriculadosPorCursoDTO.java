package pe.edu.EduAndes.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatriculadosPorCursoDTO {
    private Long cursoId;
    private String cursoCodigo;
    private String cursoNombre;
    private Long totalMatriculados;
}