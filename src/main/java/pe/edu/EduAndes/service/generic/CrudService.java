package pe.edu.EduAndes.service.generic;

import java.util.List;

public interface CrudService<REQ, RES, ID> {

    RES guardar(REQ requestDTO);
    List<RES> listarTodos();
    RES buscarPorId(ID id);
    RES actualizar(ID id, REQ requestDTO);
    void eliminar(ID id);
}