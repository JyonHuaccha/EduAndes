package pe.edu.EduAndes.service.service;

import pe.edu.EduAndes.dto.CursoRequestDTO;
import pe.edu.EduAndes.dto.CursoResponseDTO;
import pe.edu.EduAndes.service.generic.CrudService;

import java.util.List;

public interface CursoService extends CrudService<CursoRequestDTO, CursoResponseDTO, Long> {
    List<CursoResponseDTO> listarPorCarrera(Long carreraId);
    List<CursoResponseDTO> buscar(Long carreraId, Integer ciclo, Boolean conVacantes, String orden, String dir);
}
