package DAVID.escuela.services;

import java.util.List;

public interface CrudServices<RQ,RS> {

    List<RS>  Listar();

     RS obtenerPorId(Long id);

    RS registrar(RQ request);

    RS actualizar(RQ request, Long id);

    void eliminar (Long id);

}
