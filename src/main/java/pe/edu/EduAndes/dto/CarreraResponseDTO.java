package pe.edu.EduAndes.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarreraResponseDTO {

    private Long id;
    private String nombre;
    private String codigo;
    private Boolean estado;
}