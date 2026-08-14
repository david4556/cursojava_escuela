package DAVID.escuela.mapers;

import DAVID.escuela.dto.calificaciones.CalificacionRequest;
import DAVID.escuela.dto.calificaciones.CalificacionResponse;

import DAVID.escuela.dto.datos.DatosAlumnoInscripcion;
import DAVID.escuela.dto.datos.DatosGrupo;
import DAVID.escuela.dto.datos.DatosInscripcionCalificacion;
import DAVID.escuela.entities.Alumno;
import DAVID.escuela.entities.Calificacion;
import DAVID.escuela.entities.Grupo;
import DAVID.escuela.entities.Inscripcion;
import DAVID.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

@Component
public class CalificacionMapper {



    public Calificacion requestAEntidad(CalificacionRequest request) {

        if (request == null) {
            return null;
        }

        return Calificacion.builder().calificacion(request.calificacion()).build();
    }




    public CalificacionResponse entidadAResponse(Calificacion entidad) {

        if (entidad == null) {
            return null;
        }

        Inscripcion inscripcion = entidad.getInscripcion();

        DatosInscripcionCalificacion datosInscripcion = entidadADatosInscripcion(inscripcion);

        return new CalificacionResponse(
                entidad.getId(),
                datosInscripcion,
                entidad.getCalificacion(),
                StringCustomUtils.localDateAString(
                        entidad.getFechaRegistro()
                )
        );
    }




    private DatosInscripcionCalificacion entidadADatosInscripcion(Inscripcion inscripcion) {

        if (inscripcion == null) {
            return null;
        }

        DatosAlumnoInscripcion alumno = entidadADatosAlumno(inscripcion);

        DatosGrupo grupo = entidadADatosGrupo(inscripcion);

        return new DatosInscripcionCalificacion(alumno, grupo, StringCustomUtils.localDateAString(inscripcion.getFechaInscripcion())
        );
    }




    private DatosAlumnoInscripcion entidadADatosAlumno(
            Inscripcion inscripcion
    ) {

        if (inscripcion == null ||
                inscripcion.getAlumno() == null) {

            return null;
        }

        Alumno alumno = inscripcion.getAlumno();

        return new DatosAlumnoInscripcion(alumno.getMatricula(),

                String.join(" ",
                        alumno.getNombre(),
                        alumno.getApellidoPaterno(),
                        alumno.getApellidoMaterno()
                ),

                alumno.getEmail(),

                StringCustomUtils.localDateAString(
                        alumno.getFechaIngreso()
                )

        );
    }




    private DatosGrupo entidadADatosGrupo(
            Inscripcion inscripcion
    ) {

        if (inscripcion == null ||
                inscripcion.getGrupo() == null) {

            return null;
        }

        Grupo grupo = inscripcion.getGrupo();

        return new DatosGrupo(

                grupo.getCurso().getNombre(),

                grupo.getMaestro().getNombre(),

                grupo.getAula().getNombre(),

                grupo.getPeriodo()
        );
    }
}