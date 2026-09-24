package pe.edu.EduAndes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.EduAndes.dto.EstudianteRequestDTO;
import pe.edu.EduAndes.dto.EstudianteResponseDTO;
import pe.edu.EduAndes.entity.Carrera;
import pe.edu.EduAndes.entity.Estudiante;
import pe.edu.EduAndes.exception.RecursoNoEncontradoException;
import pe.edu.EduAndes.exception.ReglaNegocioException;
import pe.edu.EduAndes.repository.CarreraRepository;
import pe.edu.EduAndes.repository.EstudianteRepository;
import pe.edu.EduAndes.service.service.EstudianteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;

    @Override
    @Transactional
    public EstudianteResponseDTO guardar(EstudianteRequestDTO dto) {
        if (estudianteRepository.existsByCodigo(dto.getCodigo())) {
            throw new ReglaNegocioException("Ya existe un estudiante registrado con el código: " + dto.getCodigo());
        }

        if (estudianteRepository.existsByDni(dto.getDni())) {
            throw new ReglaNegocioException("Ya existe un estudiante registrado con el DNI: " + dto.getDni());
        }

        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + dto.getCarreraId()));

        Estudiante estudiante = Estudiante.builder()
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .codigo(dto.getCodigo())
                .dni(dto.getDni())
                .correo(dto.getCorreo())
                .estado(dto.getEstado() != null ? dto.getEstado() : true)
                .carrera(carrera)
                .build();

        Estudiante guardado = estudianteRepository.save(estudiante);
        return construirDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> listarTodos() {
        return estudianteRepository.findAll().stream()
                .map(this::construirDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteResponseDTO buscarPorId(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id));
        return construirDTO(estudiante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> listarPorCarrera(Long carreraId) {
        if (!carreraRepository.existsById(carreraId)) {
            throw new RecursoNoEncontradoException("Carrera no encontrada con ID: " + carreraId);
        }
        return estudianteRepository.findByCarreraId(carreraId).stream()
                .map(this::construirDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EstudianteResponseDTO actualizar(Long id, EstudianteRequestDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id));

        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + dto.getCarreraId()));

        estudiante.setNombres(dto.getNombres());
        estudiante.setApellidos(dto.getApellidos());
        estudiante.setCodigo(dto.getCodigo());
        estudiante.setDni(dto.getDni());
        estudiante.setCorreo(dto.getCorreo());
        if (dto.getEstado() != null) {
            estudiante.setEstado(dto.getEstado());
        }
        estudiante.setCarrera(carrera);

        Estudiante actualizado = estudianteRepository.save(estudiante);
        return construirDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id);
        }
        estudianteRepository.deleteById(id);
    }

    private EstudianteResponseDTO construirDTO(Estudiante e) {
        return EstudianteResponseDTO.builder()
                .id(e.getId())
                .nombres(e.getNombres())
                .apellidos(e.getApellidos())
                .codigo(e.getCodigo())
                .dni(e.getDni())
                .correo(e.getCorreo())
                .estado(e.getEstado())
                .carreraId(e.getCarrera().getId())
                .carreraNombre(e.getCarrera().getNombre())
                .build();
    }
}