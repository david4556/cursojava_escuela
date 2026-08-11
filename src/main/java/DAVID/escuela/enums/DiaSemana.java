package DAVID.escuela.enums;

import DAVID.escuela.exceptions.RecursoNoEncontradoException;
import DAVID.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {

    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sabado");

    private final String descripcion;

    public  static DiaSemana obtenerDiasPorDescripcion(String descripcion){
        StringCustomUtils.validarNoVacio(descripcion, "la descricion es requerida");

        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);

        for(DiaSemana diaSemana : values()){
            if(StringCustomUtils.quitarAcentos(diaSemana.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return diaSemana;
        }

        throw new RecursoNoEncontradoException("no existe un dia de la semana con:" + descripcion);
    }

}
