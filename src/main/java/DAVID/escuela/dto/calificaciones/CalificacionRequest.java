package DAVID.escuela.dto.calificaciones;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalificacionRequest(

        @NotNull(message = "La inscripción es requerida")
        Long idInscripcion,


        @NotNull(message = "La calificación es requerida")
        @DecimalMin(value = "0", message = "La calificación no puede ser menor a 0")
        @DecimalMax(value = "10", message = "La calificación no puede ser mayor a 10")
        BigDecimal calificacion) {
}
