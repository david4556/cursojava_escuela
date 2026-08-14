package DAVID.escuela.dto.inscripciones;

import DAVID.escuela.dto.datos.*;

import java.math.BigDecimal;

public record InscripcionResponse(

        Long id,

        DatosAlumnoInscripcion alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion




) {
}