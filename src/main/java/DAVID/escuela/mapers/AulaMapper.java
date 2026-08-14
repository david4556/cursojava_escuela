package DAVID.escuela.mapers;

import DAVID.escuela.dto.aulas.AulaRequest;
import DAVID.escuela.dto.aulas.AulaResponse;
import DAVID.escuela.dto.cursos.CursoResponse;
import DAVID.escuela.dto.datos.DatosCurso;
import DAVID.escuela.entities.Aula;
import DAVID.escuela.entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper implements CommonMapper<AulaRequest, AulaResponse, Aula> {
    @Override
    public Aula requestAEntidad(AulaRequest request) {

        if (request == null) return null;

        return Aula.builder()
                .nombre(request.nombre().trim())
                .capacidad(request.capacidad())
                .build();


    }

    @Override
    public AulaResponse entidadAResponse(Aula entidad) {
        if (entidad == null) return null;

        return new AulaResponse(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getCapacidad()


        );
    }


}
