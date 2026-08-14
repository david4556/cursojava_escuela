package DAVID.escuela.dto.grupos;

import DAVID.escuela.dto.datos.DatosAula;
import DAVID.escuela.dto.datos.DatosCurso;
import DAVID.escuela.dto.datos.DatosMaestro;

import java.util.List;

public record GrupoResponse(
        Long id,

        DatosCurso nombreCurso,
        DatosMaestro nombreMaestro,
        DatosAula nombreAula,

        List<String>horarios,
        String periodo
) {
}