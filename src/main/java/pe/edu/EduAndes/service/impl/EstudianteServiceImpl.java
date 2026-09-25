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

    @Override
    public EstudianteResponseDTO guardar(EstudianteRequestDTO dto) {
        return null;
        //Falta implementacion del metodo, caso de Landa
    }

    @Override
    public List<EstudianteResponseDTO> listarTodos() {
        return List.of();
        //Falta implementacion del metodo, caso de Landa
    }

    @Override
    public EstudianteResponseDTO buscarPorId(Long id) {
        return null;
        //Falta implementacion del metodo, caso de Landa

    }

    @Override
    public List<EstudianteResponseDTO> listarPorCarrera(Long carreraId) {
        return List.of();
        //Falta implementacion del metodo, caso de Landa

    }

    @Override
    public EstudianteResponseDTO actualizar(Long id, EstudianteRequestDTO dto) {
        return null;
        //Falta implementacion del metodo, caso de Landa

    }

    @Override
    public void eliminar(Long id) {
        //Falta implementacion del metodo, caso de Landa

    }
}