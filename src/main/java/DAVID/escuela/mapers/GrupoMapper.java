package DAVID.escuela.mapers;

import DAVID.escuela.dto.datos.DatosAula;
import DAVID.escuela.dto.datos.DatosCurso;
import DAVID.escuela.dto.datos.DatosMaestro;
import DAVID.escuela.dto.grupos.GrupoRequest;
import DAVID.escuela.dto.grupos.GrupoResponse;
import DAVID.escuela.entities.Grupo;
import DAVID.escuela.entities.Horario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrupoMapper
        implements CommonMapper<GrupoRequest, GrupoResponse, Grupo> {



    @Override
    public Grupo requestAEntidad(GrupoRequest request) {

        if (request == null) {
            return null;
        }

        return Grupo.builder()
                .periodo(request.periodo().trim())
                .build();
    }


    @Override
    public GrupoResponse entidadAResponse(Grupo entidad) {

        if (entidad == null) {
            return null;
        }

        return new GrupoResponse(
                entidad.getId(),
                entidadACurso(entidad),
                entidadAMaestro(entidad),
                entidadAAula(entidad),
                entidadAHorarios(entidad),
                entidad.getPeriodo()
        );
    }



    private DatosCurso entidadACurso(Grupo entidad) {

        return new DatosCurso(
                entidad.getCurso().getNombre(),
                entidad.getCurso().getDescripcion(),
                entidad.getCurso().getCreditos()
        );
    }


    private DatosMaestro entidadAMaestro(Grupo entidad) {

        String nombre = String.join(
                " ",
                entidad.getMaestro().getNombre(),
                entidad.getMaestro().getApellidoPaterno(),
                entidad.getMaestro().getApellidoMaterno()
        );

        return new DatosMaestro(
                nombre,
                entidad.getMaestro().getEmail(),
                entidad.getMaestro().getTelefono()
        );
    }


    private DatosAula entidadAAula(Grupo entidad) {

        return new DatosAula(
                entidad.getAula().getNombre(),
                entidad.getAula().getCapacidad()
        );
    }


    private List<String> entidadAHorarios(Grupo entidad) {

        if (entidad.getHorarios() == null ||
                entidad.getHorarios().isEmpty()) {

            return List.of();
        }

        return entidad.getHorarios()
                .stream()
                .map(this::horarioAString)
                .toList();
    }



    private String horarioAString(Horario horario) {

        return horario.getDiasemana().getDescripcion()
                + " "
                + horario.getHoraInicio()
                + " - "
                + horario.getHoraFin();
    }
}