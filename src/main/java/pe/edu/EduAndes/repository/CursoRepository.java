package pe.edu.EduAndes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.EduAndes.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Curso> findByCodigo(String codigo);

    List<Curso> findByCarreraId(Long carreraId);

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, Long id);
}
