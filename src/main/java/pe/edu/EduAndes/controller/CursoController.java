package pe.edu.EduAndes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.EduAndes.dto.CursoRequestDTO;
import pe.edu.EduAndes.dto.CursoResponseDTO;
import pe.edu.EduAndes.service.service.CursoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponseDTO> guardar(@Valid @RequestBody CursoRequestDTO dto) {
        return new ResponseEntity<>(cursoService.guardar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(cursoService.listarTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CursoResponseDTO>> buscar(
            @RequestParam(name = "carreraId", required = false) Long carreraId,
            @RequestParam(name = "ciclo", required = false) Integer ciclo,
            @RequestParam(name = "conVacantes", required = false) Boolean conVacantes,
            @RequestParam(name = "orden", defaultValue = "id") String orden,
            @RequestParam(name = "dir", defaultValue = "asc") String dir) {
        return ResponseEntity.ok(cursoService.buscar(carreraId, ciclo, conVacantes, orden, dir));    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.buscarPorId(id));
    }

    @GetMapping("/carrera/{carreraId}")
    public ResponseEntity<List<CursoResponseDTO>> listarPorCarrera(@PathVariable Long carreraId) {
        return ResponseEntity.ok(cursoService.listarPorCarrera(carreraId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO dto) {
        return ResponseEntity.ok(cursoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}