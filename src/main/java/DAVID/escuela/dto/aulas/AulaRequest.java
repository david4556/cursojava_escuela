package DAVID.escuela.dto.aulas;

import jakarta.validation.constraints.*;

public record AulaRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @NotNull
        @Positive(message="la cantidad debe ser positiva ")
        Integer capacidad
) {
}
