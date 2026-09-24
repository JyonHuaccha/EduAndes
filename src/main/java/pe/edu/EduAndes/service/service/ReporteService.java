package pe.edu.EduAndes.service.service;

import pe.edu.EduAndes.dto.MatriculadosPorCursoDTO;
import java.util.List;

public interface ReporteService {
    List<MatriculadosPorCursoDTO> obtenerMatriculadosPorCurso();
}