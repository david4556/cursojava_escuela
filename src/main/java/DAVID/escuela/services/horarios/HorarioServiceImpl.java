package DAVID.escuela.services.horarios;

import DAVID.escuela.dto.horarios.HorarioRequest;
import DAVID.escuela.dto.horarios.HorarioResponse;
import DAVID.escuela.entities.Grupo;
import DAVID.escuela.entities.Horario;
import DAVID.escuela.enums.DiaSemana;
import DAVID.escuela.mapers.HorarioMapper;
import DAVID.escuela.repositories.GrupoRepository;
import DAVID.escuela.repositories.HorarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final GrupoRepository grupoRepository;
    private final HorarioMapper horarioMapper;

    @Override
    public HorarioResponse registrar(HorarioRequest request) {

        Grupo grupo = grupoRepository.findById(request.idGrupo()).orElseThrow(() -> new IllegalArgumentException("El grupo no existe"));

        DiaSemana dia = horarioMapper.convertirDia(request.dia());

        boolean existe = horarioRepository.existeTraslape(
                        grupo.getId(),
                        grupo.getAula().getId(),
                        dia,
                        request.horaInicio(),
                        request.horaFin()
                );

        if (existe) {throw new IllegalArgumentException(
                    "No se puede registrar el horario porque chcoca con otro horario del mismo grupo o aula");
        }

        Horario horario = horarioMapper.requestAEntidad(request);

        horario.asignarDatos(
                grupo,
                dia,
                request.horaInicio(),
                request.horaFin()
        );

        horarioRepository.save(horario);

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HorarioResponse> listar() {

        return horarioRepository.findAll()
                .stream()
                .map(horarioMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HorarioResponse obtenerPorId(Long id) {

        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El horario no existe"
                        )
                );

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(
            HorarioRequest request,
            Long id
    ) {

        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El horario no existe"
                        )
                );

        Grupo grupo = grupoRepository.findById(request.idGrupo())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El grupo no existe"
                        )
                );

        DiaSemana dia =
                horarioMapper.convertirDia(request.dia());

        boolean existe =
                horarioRepository.existeTraslapeActualizacion(
                        grupo.getId(),
                        grupo.getAula().getId(),
                        dia,
                        request.horaInicio(),
                        request.horaFin(),
                        id
                );

        if (existe) {
            throw new IllegalArgumentException(
                    "No se puede actualizar el horario porque ya ocupa con otro horario del mismo grupo o aula"
            );
        }

        horario.actualizar(
                grupo,
                dia,
                request.horaInicio(),
                request.horaFin()
        );

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {

        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El horario no existe"
                        )
                );

        horarioRepository.delete(horario);
    }
}