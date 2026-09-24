package pe.edu.EduAndes.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoResponseDTO {

    private Long id;
    private String nombre;
    private String codigo;
    private Integer creditos;
    private Integer ciclo;
    private BigDecimal costo;
    private Integer vacantes;
    private Boolean estado;
    private Long carreraId;
    private String carreraNombre;
}