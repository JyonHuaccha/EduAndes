package pe.edu.EduAndes.service.service;

import pe.edu.EduAndes.dto.CarreraRequestDTO;
import pe.edu.EduAndes.dto.CarreraResponseDTO;
import pe.edu.EduAndes.dto.CursoResponseDTO;
import pe.edu.EduAndes.service.generic.CrudService;

import java.util.List;

public interface CarreraService extends CrudService<CarreraRequestDTO, CarreraResponseDTO, Long> {
    List<CursoResponseDTO> listarCursosPorCarrera(Long carreraId);
}
