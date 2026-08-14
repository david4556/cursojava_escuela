package DAVID.escuela.dto.cursos;

import jakarta.validation.constraints.*;

public record CursoRequest(

        @NotBlank(message = "El nombre es requerido ")
                           @Size(min = 5, max = 100, message = "el nombre debe tener entre 5 y 100 caracteres")
                           String nombre,


                           @Size(max = 200, message = "la descripcion debe tener maximo 200 caracteres")
                           String descripcion,

                           @NotNull(message = "los creditos son requeridos")
                           @Min(value = 1, message = "los creditos minimos son 1")
                           @Max(value = 10 , message = "los creditos maximos son 10")
                           Integer creditos) {
}
