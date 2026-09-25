package pe.edu.EduAndes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.EduAndes.dto.DetalleMatriculaResponseDTO;
import pe.edu.EduAndes.dto.MatriculaRequestDTO;
import pe.edu.EduAndes.dto.MatriculaResponseDTO;
import pe.edu.EduAndes.entity.*;
import pe.edu.EduAndes.enums.EstadoMatricula;
import pe.edu.EduAndes.exception.RecursoNoEncontradoException;
import pe.edu.EduAndes.exception.ReglaNegocioException;
import pe.edu.EduAndes.repository.*;
import pe.edu.EduAndes.service.service.MatriculaService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    @Override
    public MatriculaResponseDTO registrarMatricula(MatriculaRequestDTO dto) {
        return null;
        //Falta implementacion del metodo, caso de Landa
    }

    @Override
    public List<MatriculaResponseDTO> listarTodas() {
        return List.of();
        //Falta implementacion del metodo, caso de Landa

    }

    @Override
    public MatriculaResponseDTO buscarPorId(Long id) {
        return null;
        //Falta implementacion del metodo, caso de Landa

    }

    @Override
    public MatriculaResponseDTO anularMatricula(Long id) {
        return null;
        //Falta implementacion del metodo, caso de Landa
    }
}