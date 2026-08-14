package DAVID.escuela.dto.aulas;

import DAVID.escuela.dto.datos.DatosCalificacion;

import java.math.BigDecimal;
import java.util.List;

public record AulaResponse(
        Long id,
        String nombre,
        Integer capacidad
) {

}
