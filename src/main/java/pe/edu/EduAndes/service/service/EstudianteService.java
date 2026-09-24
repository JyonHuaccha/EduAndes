package pe.edu.EduAndes.service.service;

import pe.edu.EduAndes.dto.EstudianteRequestDTO;
import pe.edu.EduAndes.dto.EstudianteResponseDTO;

import java.util.List;

public interface EstudianteService {

    EstudianteResponseDTO guardar(EstudianteRequestDTO dto);

    List<EstudianteResponseDTO> listarTodos();

    EstudianteResponseDTO buscarPorId(Long id);

    List<EstudianteResponseDTO> listarPorCarrera(Long carreraId);

    EstudianteResponseDTO actualizar(Long id, EstudianteRequestDTO dto);

    void eliminar(Long id);
}
