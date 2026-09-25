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

    private final MatriculaRepository matriculaRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    @Override
    @Transactional
    public MatriculaResponseDTO registrarMatricula(MatriculaRequestDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + dto.getEstudianteId()));

        if (Boolean.FALSE.equals(estudiante.getEstado())) {
            throw new ReglaNegocioException("El estudiante no se encuentra activo");
        }

        if (matriculaRepository.existsByEstudianteIdAndPeriodoAndEstado(
                dto.getEstudianteId(), dto.getPeriodo(), EstadoMatricula.REGISTRADA)) {
            throw new ReglaNegocioException("El estudiante ya tiene una matrícula activa en el periodo " + dto.getPeriodo());
        }

        int totalCreditos = 0;
        BigDecimal montoTotal = BigDecimal.ZERO;
        List<Curso> cursosAProcesar = new ArrayList<>();

        for (Long cursoId : dto.getCursosIds()) {
            Curso curso = cursoRepository.findById(cursoId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con ID: " + cursoId));

            if (!curso.getCarrera().getId().equals(estudiante.getCarrera().getId())) {
                throw new ReglaNegocioException("El curso " + curso.getNombre() + " no pertenece a la carrera del estudiante");
            }

            if (curso.getVacantes() <= 0) {
                throw new ReglaNegocioException("El curso " + curso.getNombre() + " no cuenta con vacantes disponibles");
            }

            totalCreditos += curso.getCreditos();

            BigDecimal costoCurso = BigDecimal.valueOf(curso.getCreditos()).multiply(new BigDecimal("120.00"));
            montoTotal = montoTotal.add(costoCurso);

            cursosAProcesar.add(curso);
        }

        if (totalCreditos > 20) {
            throw new ReglaNegocioException("La matrícula no puede superar el límite de 20 créditos. Créditos solicitados: " + totalCreditos);
        }

        Matricula matricula = Matricula.builder()
                .fecha(LocalDateTime.now())
                .periodo(dto.getPeriodo())
                .estado(EstadoMatricula.REGISTRADA)
                .totalCreditos(totalCreditos)
                .montoTotal(montoTotal)
                .estudiante(estudiante)
                .detalles(new ArrayList<>())
                .build();

        for (Curso curso : cursosAProcesar) {
            curso.setVacantes(curso.getVacantes() - 1);
            cursoRepository.save(curso);

            BigDecimal costoCurso = BigDecimal.valueOf(curso.getCreditos()).multiply(new BigDecimal("120.00"));

            DetalleMatricula detalle = DetalleMatricula.builder()
                    .creditos(curso.getCreditos())
                    .costo(costoCurso)
                    .curso(curso)
                    .build();

            matricula.agregarDetalle(detalle);
        }

        Matricula guardada = matriculaRepository.save(matricula);
        return construirDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarTodas() {
        return matriculaRepository.findAll().stream()
                .map(this::construirDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MatriculaResponseDTO buscarPorId(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Matrícula no encontrada con ID: " + id));
        return construirDTO(matricula);
    }

    @Override
    @Transactional
    public MatriculaResponseDTO anularMatricula(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Matrícula no encontrada con ID: " + id));

        if (EstadoMatricula.ANULADA.equals(matricula.getEstado())) {
            throw new ReglaNegocioException("La matrícula ya se encuentra anulada");
        }

        matricula.setEstado(EstadoMatricula.ANULADA);

        for (DetalleMatricula detalle : matricula.getDetalles()) {
            Curso curso = detalle.getCurso();
            curso.setVacantes(curso.getVacantes() + 1);
            cursoRepository.save(curso);
        }

        Matricula actualizada = matriculaRepository.save(matricula);
        return construirDTO(actualizada);
    }

    private MatriculaResponseDTO construirDTO(Matricula m) {
        List<DetalleMatriculaResponseDTO> detallesDTO = m.getDetalles().stream()
                .map(d -> DetalleMatriculaResponseDTO.builder()
                        .id(d.getId())
                        .cursoId(d.getCurso().getId())
                        .cursoCodigo(d.getCurso().getCodigo())
                        .cursoNombre(d.getCurso().getNombre())
                        .creditos(d.getCreditos())
                        .costo(d.getCosto())
                        .build())
                .collect(Collectors.toList());

        return MatriculaResponseDTO.builder()
                .id(m.getId())
                .fecha(m.getFecha())
                .periodo(m.getPeriodo())
                .estado(m.getEstado())
                .totalCreditos(m.getTotalCreditos())
                .montoTotal(m.getMontoTotal())
                .estudianteId(m.getEstudiante().getId())
                .estudianteCodigo(m.getEstudiante().getCodigo())
                .estudianteNombreCompleto(m.getEstudiante().getNombres() + " " + m.getEstudiante().getApellidos())
                .detalles(detallesDTO)
                .build();
    }
}