package DAVID.escuela.dto.cursos;

import jakarta.validation.constraints.*;

public record CursoResponse(

       Long id,
        String nombre,
        String descripcion,
        Integer creditos
) {
}
