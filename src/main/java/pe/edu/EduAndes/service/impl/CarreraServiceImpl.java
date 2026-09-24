package pe.edu.EduAndes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.EduAndes.dto.CarreraRequestDTO;
import pe.edu.EduAndes.dto.CarreraResponseDTO;
import pe.edu.EduAndes.dto.CursoResponseDTO;
import pe.edu.EduAndes.entity.Carrera;
import pe.edu.EduAndes.exception.RecursoNoEncontradoException;
import pe.edu.EduAndes.exception.ReglaNegocioException;
import pe.edu.EduAndes.repository.CarreraRepository;
import pe.edu.EduAndes.repository.CursoRepository;
import pe.edu.EduAndes.service.service.CarreraService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarreraServiceImpl implements CarreraService {
    private final CarreraRepository carreraRepository;
    private final CursoRepository cursoRepository;

    @Override
    @Transactional
    public CarreraResponseDTO guardar(CarreraRequestDTO dto) {
        String nombreLimpio = dto.getNombre() != null ? dto.getNombre().trim() : "";
        String codigoLimpio = dto.getCodigo() != null ? dto.getCodigo().trim() : "";

        if (carreraRepository.existsByCodigo(codigoLimpio)) {
            throw new ReglaNegocioException("Ya existe una carrera con el código: " + codigoLimpio);
        }

        if (carreraRepository.existsByNombreIgnoreCaseAndTrimmed(nombreLimpio)) {
            throw new ReglaNegocioException("Ya existe una carrera con el nombre: " + nombreLimpio);
        }

        Carrera carrera = Carrera.builder()
                .nombre(nombreLimpio)
                .codigo(codigoLimpio)
                .estado(dto.getEstado() != null ? dto.getEstado() : true)
                .build();

        Carrera guardada = carreraRepository.save(carrera);

        return CarreraResponseDTO.builder()
                .id(guardada.getId())
                .nombre(guardada.getNombre())
                .codigo(guardada.getCodigo())
                .estado(guardada.getEstado())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarreraResponseDTO> listarTodos() {
        return carreraRepository.findAll().stream()
                .map(c -> CarreraResponseDTO.builder()
                        .id(c.getId())
                        .nombre(c.getNombre())
                        .codigo(c.getCodigo())
                        .estado(c.getEstado())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CarreraResponseDTO buscarPorId(Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con el ID: " + id));

        return CarreraResponseDTO.builder()
                .id(carrera.getId())
                .nombre(carrera.getNombre())
                .codigo(carrera.getCodigo())
                .estado(carrera.getEstado())
                .build();
    }

    @Override
    @Transactional
    public CarreraResponseDTO actualizar(Long id, CarreraRequestDTO dto) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con el ID: " + id));

        if (!carrera.getCodigo().equalsIgnoreCase(dto.getCodigo()) && carreraRepository.existsByCodigo(dto.getCodigo())) {
            throw new ReglaNegocioException("Ya existe otra carrera con el código: " + dto.getCodigo());
        }

        carrera.setNombre(dto.getNombre());
        carrera.setCodigo(dto.getCodigo());
        carrera.setEstado(dto.getEstado());

        Carrera actualizada = carreraRepository.save(carrera);

        return CarreraResponseDTO.builder()
                .id(actualizada.getId())
                .nombre(actualizada.getNombre())
                .codigo(actualizada.getCodigo())
                .estado(actualizada.getEstado())
                .build();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!carreraRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Carrera no encontrada con el ID: " + id);
        }
        carreraRepository.deleteById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public List<CursoResponseDTO> listarCursosPorCarrera(Long carreraId) {
        if (!carreraRepository.existsById(carreraId)) {
            throw new RecursoNoEncontradoException("Carrera no encontrada con el ID: " + carreraId);
        }

        return cursoRepository.findByCarreraId(carreraId)
                .stream()
                .map(curso -> CursoResponseDTO.builder()
                        .id(curso.getId())
                        .nombre(curso.getNombre())
                        .codigo(curso.getCodigo())
                        .creditos(curso.getCreditos())
                        .estado(curso.getEstado())
                        .carreraId(curso.getCarrera().getId())
                        .build())
                .collect(Collectors.toList());
    }
}