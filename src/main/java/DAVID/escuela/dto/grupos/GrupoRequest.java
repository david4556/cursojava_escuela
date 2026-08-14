package DAVID.escuela.dto.grupos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record GrupoRequest(

        @NotNull(message = "El curso es requerido")
        @Positive(message = "El id del curso debe ser positivo")
        Long idCurso,

        @NotNull(message = "El maestro es requerido")
        @Positive(message = "El id del maestro debe ser positivo")
        Long idMaestro,

        @NotNull(message = "El aula es requerida")
        @Positive(message = "El id del aula debe ser positivo")
        Long idAula,

        @NotBlank(message = "El periodo es requerido")
        @Pattern(
                regexp = "^\\d{4}-(0[1-9]|1[0-2])$",
                message = "El periodo debe tener el formato YYYY-MM"
        )
        String periodo
) {
}