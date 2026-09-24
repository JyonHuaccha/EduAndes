package pe.edu.EduAndes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.EduAndes.dto.CursoRequestDTO;
import pe.edu.EduAndes.dto.CursoResponseDTO;
import pe.edu.EduAndes.entity.Carrera;
import pe.edu.EduAndes.entity.Curso;
import pe.edu.EduAndes.exception.RecursoNoEncontradoException;
import pe.edu.EduAndes.exception.ReglaNegocioException;
import pe.edu.EduAndes.repository.CarreraRepository;
import pe.edu.EduAndes.repository.CursoRepository;
import pe.edu.EduAndes.service.service.CursoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final CarreraRepository carreraRepository;

    @Override
    @Transactional
    public CursoResponseDTO guardar(CursoRequestDTO dto) {
        if (cursoRepository.existsByCodigo(dto.getCodigo())) {
            throw new ReglaNegocioException("Ya existe un curso registrado con el código: " + dto.getCodigo());
        }

        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con el ID: " + dto.getCarreraId()));

        Curso curso = Curso.builder()
                .nombre(dto.getNombre())
                .codigo(dto.getCodigo())
                .creditos(dto.getCreditos())
                .costo(dto.getCosto())
                .vacantes(dto.getVacantes())
                .carrera(carrera)
                .build();

        Curso guardado = cursoRepository.save(curso);

        return CursoResponseDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .codigo(guardado.getCodigo())
                .creditos(guardado.getCreditos())
                .costo(guardado.getCosto())
                .vacantes(guardado.getVacantes())
                .carreraId(guardado.getCarrera().getId())
                .carreraNombre(guardado.getCarrera().getNombre())
                .build();
    }
    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDTO> buscar(Long carreraId, Integer ciclo, Boolean conVacantes, String orden, String dir) {
        Sort.Direction direction = "desc".equalsIgnoreCase(dir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        String campoOrden = (orden != null && !orden.trim().isEmpty()) ? orden : "id";
        Sort sort = Sort.by(direction, campoOrden);

        return cursoRepository.findAll(sort).stream()
                .filter(curso -> carreraId == null || (curso.getCarrera() != null && curso.getCarrera().getId().equals(carreraId)))
                .filter(curso -> ciclo == null || (curso.getCiclo() != null && curso.getCiclo().equals(ciclo)))
                .filter(curso -> conVacantes == null || !conVacantes || (curso.getVacantes() != null && curso.getVacantes() > 0))
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }
    private CursoResponseDTO mapearADTO(Curso curso) {
        return CursoResponseDTO.builder()
                .id(curso.getId())
                .nombre(curso.getNombre())
                .codigo(curso.getCodigo())
                .creditos(curso.getCreditos())
                .ciclo(curso.getCiclo())
                .costo(curso.getCosto())
                .vacantes(curso.getVacantes())
                .carreraId(curso.getCarrera() != null ? curso.getCarrera().getId() : null)
                .carreraNombre(curso.getCarrera() != null ? curso.getCarrera().getNombre() : null)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDTO> listarTodos() {
        return cursoRepository.findAll().stream()
                .map(c -> CursoResponseDTO.builder()
                        .id(c.getId())
                        .nombre(c.getNombre())
                        .codigo(c.getCodigo())
                        .creditos(c.getCreditos())
                        .costo(c.getCosto())
                        .vacantes(c.getVacantes())
                        .carreraId(c.getCarrera().getId())
                        .carreraNombre(c.getCarrera().getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponseDTO buscarPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con el ID: " + id));

        return CursoResponseDTO.builder()
                .id(curso.getId())
                .nombre(curso.getNombre())
                .codigo(curso.getCodigo())
                .creditos(curso.getCreditos())
                .costo(curso.getCosto())
                .vacantes(curso.getVacantes())
                .carreraId(curso.getCarrera().getId())
                .carreraNombre(curso.getCarrera().getNombre())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDTO> listarPorCarrera(Long carreraId) {
        if (!carreraRepository.existsById(carreraId)) {
            throw new RecursoNoEncontradoException("Carrera no encontrada con el ID: " + carreraId);
        }

        return cursoRepository.findByCarreraId(carreraId).stream()
                .map(c -> CursoResponseDTO.builder()
                        .id(c.getId())
                        .nombre(c.getNombre())
                        .codigo(c.getCodigo())
                        .creditos(c.getCreditos())
                        .costo(c.getCosto())
                        .vacantes(c.getVacantes())
                        .carreraId(c.getCarrera().getId())
                        .carreraNombre(c.getCarrera().getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CursoResponseDTO actualizar(Long id, CursoRequestDTO dto) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con el ID: " + id));

        if (!curso.getCodigo().equalsIgnoreCase(dto.getCodigo()) && cursoRepository.existsByCodigo(dto.getCodigo())) {
            throw new ReglaNegocioException("Ya existe otro curso con el código: " + dto.getCodigo());
        }

        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con el ID: " + dto.getCarreraId()));

        curso.setNombre(dto.getNombre());
        curso.setCodigo(dto.getCodigo());
        curso.setCreditos(dto.getCreditos());
        curso.setCosto(dto.getCosto());
        curso.setVacantes(dto.getVacantes());
        curso.setCarrera(carrera);

        Curso actualizado = cursoRepository.save(curso);

        return CursoResponseDTO.builder()
                .id(actualizado.getId())
                .nombre(actualizado.getNombre())
                .codigo(actualizado.getCodigo())
                .creditos(actualizado.getCreditos())
                .costo(actualizado.getCosto())
                .vacantes(actualizado.getVacantes())
                .carreraId(actualizado.getCarrera().getId())
                .carreraNombre(actualizado.getCarrera().getNombre())
                .build();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Curso no encontrado con el ID: " + id);
        }
        cursoRepository.deleteById(id);
    }
}