package pe.edu.EduAndes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.EduAndes.entity.Carrera;

import java.util.Optional;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    @Query("SELECT COUNT(c) > 0 FROM Carrera c WHERE LOWER(TRIM(c.nombre)) = LOWER(TRIM(:nombre))")
    boolean existsByNombreIgnoreCaseAndTrimmed(@Param("nombre") String nombre);
    boolean existsByCodigo(String codigo);
}
