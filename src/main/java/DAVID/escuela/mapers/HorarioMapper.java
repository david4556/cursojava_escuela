package DAVID.escuela.mapers;

import DAVID.escuela.dto.datos.DatosGrupo;
import DAVID.escuela.dto.horarios.HorarioRequest;
import DAVID.escuela.dto.horarios.HorarioResponse;
import DAVID.escuela.entities.Grupo;
import DAVID.escuela.entities.Horario;
import DAVID.escuela.enums.DiaSemana;
import org.springframework.stereotype.Component;

@Component
public class HorarioMapper
        implements CommonMapper<HorarioRequest, HorarioResponse, Horario> {

    @Override
    public Horario requestAEntidad(HorarioRequest request) {

        if (request == null) {
            return null;
        }

        return Horario.builder()
                .diasemana(convertirDia(request.dia()))
                .horaInicio(request.horaInicio().trim())
                .horaFin(request.horaFin().trim())
                .build();
    }

    public DiaSemana convertirDia(String dia) {

        return DiaSemana.obtenerDiasPorDescripcion(dia);
    }

    @Override
    public HorarioResponse entidadAResponse(Horario entidad) {

        if (entidad == null) {
            return null;
        }

        Grupo grupo = entidad.getGrupo();

        DatosGrupo datosGrupo = new DatosGrupo(

                grupo.getCurso().getNombre(),

                String.join(
                        " ",
                        grupo.getMaestro().getNombre(),
                        grupo.getMaestro().getApellidoPaterno(),
                        grupo.getMaestro().getApellidoMaterno()
                ),

                grupo.getAula().getNombre(),

                grupo.getPeriodo()
        );

        String horario = String.join(
                " ",
                entidad.getDiasemana().getDescripcion(),
                entidad.getHoraInicio(),
                entidad.getHoraFin()
        );

        return new HorarioResponse(
                entidad.getId(),
                datosGrupo,
                horario
        );
    }
}