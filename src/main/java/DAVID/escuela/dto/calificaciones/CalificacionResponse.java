package DAVID.escuela.dto.calificaciones;

import DAVID.escuela.dto.datos.DatosInscripcionCalificacion;

import java.math.BigDecimal;

public record CalificacionResponse(

        Long id,

        DatosInscripcionCalificacion inscripcion,

        BigDecimal calificacion,

        String fechaRegistro

) {
}