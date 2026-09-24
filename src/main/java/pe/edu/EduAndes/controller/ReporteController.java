package pe.edu.EduAndes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.EduAndes.dto.MatriculadosPorCursoDTO;
import pe.edu.EduAndes.service.service.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/matriculados-por-curso")
    public ResponseEntity<List<MatriculadosPorCursoDTO>> obtenerMatriculadosPorCurso() {
        return ResponseEntity.ok(reporteService.obtenerMatriculadosPorCurso());
    }
}