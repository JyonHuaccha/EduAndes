package pe.edu.EduAndes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.EduAndes.entity.Estudiante;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByCodigo(String codigo);

    Optional<Estudiante> findByDni(String dni);

    boolean existsByCodigo(String codigo);

    boolean existsByDni(String dni);

    List<Estudiante> findByCarreraId(Long carreraId);
}