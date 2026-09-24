package pe.edu.EduAndes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.EduAndes.dto.MatriculadosPorCursoDTO;
import pe.edu.EduAndes.repository.MatriculaRepository;
import pe.edu.EduAndes.service.service.ReporteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final MatriculaRepository matriculaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MatriculadosPorCursoDTO> obtenerMatriculadosPorCurso() {
        return matriculaRepository.obtenerMatriculadosPorCurso();
    }
}