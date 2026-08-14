package DAVID.escuela.dto.datos;

import java.math.BigDecimal;

public record DatosAlumno(

        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String matricula,
        String email,
        String fechaIngreso,
        BigDecimal promedio
) {
}
