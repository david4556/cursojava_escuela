package DAVID.escuela.dto.horarios;

import DAVID.escuela.dto.datos.DatosGrupo;

public record HorarioResponse(

        Long id,

        DatosGrupo grupo,

        String horario

) {
}