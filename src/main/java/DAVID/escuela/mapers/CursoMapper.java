package DAVID.escuela.mapers;

import DAVID.escuela.dto.cursos.CursoRequest;
import DAVID.escuela.dto.cursos.CursoResponse;
import DAVID.escuela.dto.datos.DatosCurso;
import DAVID.escuela.dto.maestros.MaestroResponse;
import DAVID.escuela.entities.Curso;
import DAVID.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso> {

    @Override
    public Curso requestAEntidad(CursoRequest request){

        if (request== null) return null;
        String descripcion = request.descripcion()!= null
                ? request.descripcion().trim() : null;

        return  Curso.builder()
                .nombre(request.nombre().trim())
                .descripcion(descripcion)
                .creditos(request.creditos())
                .build();
    }
    @Override
    public CursoResponse entidadAResponse (Curso entidad ) {

        if (entidad == null) return null;

        String descripcion = entidad.getDescripcion() == null
                ? "sin descripcion" : entidad.getDescripcion();

        return new CursoResponse(
                entidad.getId(),
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()


        );
    }


    public DatosCurso entidadADatosCurso (Curso entidad ) {

        if (entidad == null) return null;

        String descripcion = entidad.getDescripcion() == null
                ? "sin descripcion" : entidad.getDescripcion();

        return new DatosCurso(

                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()


        );
    }
}
