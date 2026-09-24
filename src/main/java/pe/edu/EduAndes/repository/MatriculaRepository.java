package pe.edu.EduAndes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.EduAndes.dto.MatriculadosPorCursoDTO;
import pe.edu.EduAndes.entity.Matricula;
import pe.edu.EduAndes.enums.EstadoMatricula;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    @Query("SELECT new pe.edu.EduAndes.dto.MatriculadosPorCursoDTO(" +
            "d.curso.id, d.curso.codigo, d.curso.nombre, COUNT(d.id)) " +
            "FROM DetalleMatricula d " +
            "WHERE d.matricula.estado = pe.edu.EduAndes.enums.EstadoMatricula.REGISTRADA " +
            "GROUP BY d.curso.id, d.curso.codigo, d.curso.nombre")
    List<MatriculadosPorCursoDTO> obtenerMatriculadosPorCurso();

    boolean existsByEstudianteIdAndPeriodoAndEstado(Long estudianteId, String periodo, EstadoMatricula estado);
}