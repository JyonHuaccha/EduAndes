package pe.edu.EduAndes.service.service;


import pe.edu.EduAndes.dto.MatriculaRequestDTO;
import pe.edu.EduAndes.dto.MatriculaResponseDTO;

import java.util.List;

public interface MatriculaService {

    MatriculaResponseDTO registrarMatricula(MatriculaRequestDTO dto);

    List<MatriculaResponseDTO> listarTodas();

    MatriculaResponseDTO buscarPorId(Long id);

    MatriculaResponseDTO anularMatricula(Long id);
}