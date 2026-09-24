package pe.edu.EduAndes.dto;


import lombok.*;
import pe.edu.EduAndes.enums.EstadoMatricula;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MatriculaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private String periodo;
    private EstadoMatricula estado;
    private Integer totalCreditos;
    private BigDecimal montoTotal;
    private Long estudianteId;
    private String estudianteCodigo;
    private String estudianteNombreCompleto;
    private List<DetalleMatriculaResponseDTO> detalles;
}