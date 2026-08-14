package DAVID.escuela.mapers;

import DAVID.escuela.dto.datos.DatosAlumnoInscripcion;
import DAVID.escuela.dto.datos.DatosGrupo;
import DAVID.escuela.dto.inscripciones.InscripcionRequest;
import DAVID.escuela.dto.inscripciones.InscripcionResponse;
import DAVID.escuela.entities.Inscripcion;
import DAVID.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion> {



    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {

        if (request == null) {
            return null;
        }

        return Inscripcion.builder()
                .build();
    }


    @Override
    public InscripcionResponse entidadAResponse(Inscripcion entidad) {

        if (entidad == null) {
            return null;
        }

        return new InscripcionResponse(
                entidad.getId(),
                entidadAAlumno(entidad),
                entidadAGrupo(entidad),
                obtenerCalificacion(entidad),
                obtenerFechaInscripcion(entidad)
        );
    }



    private DatosAlumnoInscripcion entidadAAlumno(
            Inscripcion entidad
    ) {

        return new DatosAlumnoInscripcion(
                obtenerNombreAlumno(entidad),
                entidad.getAlumno().getMatricula(),
                entidad.getAlumno().getEmail(),
                StringCustomUtils.localDateAString(
                        entidad.getAlumno().getFechaIngreso()
                )
        );
    }


    private String obtenerNombreAlumno(Inscripcion entidad) {

        return String.join(
                " ",
                entidad.getAlumno().getNombre(),
                entidad.getAlumno().getApellidoPaterno(),
                entidad.getAlumno().getApellidoMaterno()
        );
    }




    private DatosGrupo entidadAGrupo(Inscripcion entidad
    ) {

        return new DatosGrupo(
                entidad.getGrupo().getCurso().getNombre(),
                obtenerNombreMaestro(entidad),
                entidad.getGrupo().getAula().getNombre(),
                entidad.getGrupo().getPeriodo()
        );
    }


    private String obtenerNombreMaestro(Inscripcion entidad) {

        return String.join(
                " ",
                entidad.getGrupo().getMaestro().getNombre(),
                entidad.getGrupo().getMaestro().getApellidoPaterno(),
                entidad.getGrupo().getMaestro().getApellidoMaterno()
        );
    }


    private BigDecimal obtenerCalificacion(Inscripcion entidad) {

        if (entidad.getCalificacion() == null) {
            return null;
        }

        return entidad.getCalificacion().getCalificacion();
    }



    private String obtenerFechaInscripcion(Inscripcion entidad) {

        if (entidad.getFechaInscripcion() == null) {
            return null;
        }

        return StringCustomUtils.localDateAString(
                entidad.getFechaInscripcion()
        );
    }
}