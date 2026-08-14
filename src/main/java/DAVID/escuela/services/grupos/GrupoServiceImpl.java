package DAVID.escuela.services.grupos;

import DAVID.escuela.dto.grupos.GrupoRequest;
import DAVID.escuela.dto.grupos.GrupoResponse;
import DAVID.escuela.entities.Aula;
import DAVID.escuela.entities.Curso;
import DAVID.escuela.entities.Grupo;
import DAVID.escuela.entities.Maestro;
import DAVID.escuela.mapers.GrupoMapper;
import DAVID.escuela.repositories.AulaRepository;
import DAVID.escuela.repositories.CursoRepository;
import DAVID.escuela.repositories.GrupoRepository;
import DAVID.escuela.repositories.HorarioRepository;
import DAVID.escuela.repositories.InscripcionRepository;
import DAVID.escuela.repositories.MaestroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class GrupoServiceImpl implements GrupoService {


    private final GrupoRepository grupoRepository;

    private final CursoRepository cursoRepository;

    private final MaestroRepository maestroRepository;

    private final AulaRepository aulaRepository;

    private final HorarioRepository horarioRepository;

    private final InscripcionRepository inscripcionRepository;

    private final GrupoMapper grupoMapper;




    @Override
    public GrupoResponse registrar(GrupoRequest request) {

        Grupo grupo = prepararGrupo(request);

        guardar(grupo);

        return grupoMapper.entidadAResponse(grupo);
    }



    @Override
    @Transactional(readOnly = true)
    public GrupoResponse obtenerPorId(Long id) {

        Grupo grupo = obtenerGrupo(id);

        return grupoMapper.entidadAResponse(grupo);
    }


    @Override
    @Transactional(readOnly = true)
    public List<GrupoResponse> listar() {

        return grupoRepository.findAll()
                .stream()
                .map(grupoMapper::entidadAResponse)
                .toList();
    }


    @Override
    public GrupoResponse actualizar(
            GrupoRequest request,
            Long id
    ) {

        Grupo grupo = obtenerGrupo(id);

        actualizarDatos(grupo, request);

        return grupoMapper.entidadAResponse(grupo);
    }




    @Override
    public void eliminar(Long id) {

        Grupo grupo = obtenerGrupo(id);

        validarPuedeEliminar(id);

        eliminarGrupo(grupo);
    }



    private Grupo prepararGrupo(GrupoRequest request) {

        Curso curso = obtenerCurso(request.idCurso());

        Maestro maestro = obtenerMaestro(request.idMaestro());

        Aula aula = obtenerAula(request.idAula());

        validarDuplicidad(
                curso,
                maestro,
                aula,
                request.periodo()
        );

        Grupo grupo = grupoMapper.requestAEntidad(request);

        grupo.asignarDatos(
                curso,
                maestro,
                aula,
                request.periodo()
        );

        return grupo;
    }



    private void actualizarDatos(
            Grupo grupo,
            GrupoRequest request
    ) {

        Curso curso = obtenerCurso(request.idCurso());

        Maestro maestro = obtenerMaestro(request.idMaestro());

        Aula aula = obtenerAula(request.idAula());

        validarDuplicidadActualizacion(
                curso,
                maestro,
                aula,
                request.periodo(),
                grupo.getId()
        );

        grupo.actualizar(
                curso,
                maestro,
                aula,
                request.periodo()
        );
    }




    private Grupo obtenerGrupo(Long id) {

        return grupoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El grupo no existe"));
    }



    private Curso obtenerCurso(Long id) {

        return cursoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El curso no existe"));
    }



    private Maestro obtenerMaestro(Long id) {

        return maestroRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El maestro no existe")
                );
    }


    private Aula obtenerAula(Long id) {

        return aulaRepository
                .findById(id).orElseThrow(() -> new IllegalArgumentException("El aula no existe")
                );
    }

    private void validarDuplicidad(
            Curso curso,
            Maestro maestro,
            Aula aula,
            String periodo
    ) {

        boolean existe = grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(
                                curso.getId(),
                                maestro.getId(),
                                aula.getId(),
                                periodo.trim()
                        );

        if (existe) {
            throw new IllegalArgumentException("Ya existe un grupo con el mismo curso, maestro, aula y periodo");
        }
    }



    private void validarDuplicidadActualizacion(
            Curso curso,
            Maestro maestro,
            Aula aula,
            String periodo,
            Long idGrupo
    ) {

        boolean existe = grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
                                curso.getId(),
                                maestro.getId(),
                                aula.getId(),
                                periodo.trim(),
                                idGrupo
                );

        if (existe) {

            throw new IllegalArgumentException("Ya existe otro grupo con el mismo curso, maestro, aula y periodo");
        }
    }




    private void validarPuedeEliminar(Long idGrupo) {

        validarSinInscripciones(idGrupo);

        validarSinHorarios(idGrupo);
    }



    private void validarSinInscripciones(Long idGrupo) {

        boolean tieneInscripciones = inscripcionRepository.existsByGrupoId(idGrupo);

        if (tieneInscripciones) {

            throw new IllegalArgumentException("No se puede eliminar el grupo porque tiene inscripciones asociadas");
        }
    }



    private void validarSinHorarios(Long idGrupo) {

        boolean tieneHorarios = horarioRepository.existsByGrupoId(idGrupo);

        if (tieneHorarios) {
            throw new IllegalArgumentException("No se puede eliminar el grupo porque tiene horarios asociados");
        }
    }




    private void guardar(Grupo grupo) {

        grupoRepository.save(grupo);
    }



    private void eliminarGrupo(Grupo grupo) {

        grupoRepository.delete(grupo);
    }
}