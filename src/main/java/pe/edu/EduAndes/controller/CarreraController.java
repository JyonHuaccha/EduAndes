package pe.edu.EduAndes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.EduAndes.dto.CarreraRequestDTO;
import pe.edu.EduAndes.dto.CarreraResponseDTO;
import pe.edu.EduAndes.dto.CursoResponseDTO;
import pe.edu.EduAndes.service.service.CarreraService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carreras")
@RequiredArgsConstructor
public class CarreraController {

    private final CarreraService carreraService;

    @PostMapping
    public ResponseEntity<CarreraResponseDTO> guardar(@Valid @RequestBody CarreraRequestDTO dto) {
        return new ResponseEntity<>(carreraService.guardar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CarreraResponseDTO>> listarTodos() {
        return ResponseEntity.ok(carreraService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carreraService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CarreraRequestDTO dto) {
        return ResponseEntity.ok(carreraService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carreraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/cursos")
    public ResponseEntity<List<CursoResponseDTO>> obtenerCursosPorCarrera(@PathVariable("id") Long id) {
        List<CursoResponseDTO> cursos = carreraService.listarCursosPorCarrera(id);
        return ResponseEntity.ok(cursos);
    }
}