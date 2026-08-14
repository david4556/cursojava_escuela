package DAVID.escuela.dto.alumnos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AlumnoRequest(


        @NotBlank(message = "El nombre es requerido")
        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(max = 50, message = "El apellido paterno no puede superar los 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Size(max = 50, message = "El apellido materno no puede superar los 50 caracteres")
        String apellidoMaterno


) {
}
