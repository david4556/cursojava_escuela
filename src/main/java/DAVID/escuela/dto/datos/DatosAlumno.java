package DAVID.escuela.dto.datos;

import java.math.BigDecimal;

public record DatosAlumno(

        String matricula,

        String nombre,

        String email,

        String fechaIngreso,

        BigDecimal promedio

) {
}