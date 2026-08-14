package DAVID.escuela.services.inscripciones;

import DAVID.escuela.dto.inscripciones.InscripcionRequest;
import DAVID.escuela.dto.inscripciones.InscripcionResponse;
import DAVID.escuela.entities.Alumno;
import DAVID.escuela.entities.Grupo;
import DAVID.escuela.entities.Inscripcion;
import DAVID.escuela.mapers.InscripcionMapper;
import DAVID.escuela.repositories.AlumnoRepository;
import DAVID.escuela.repositories.GrupoRepository;
import DAVID.escuela.repositories.InscripcionRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    private final AlumnoRepository alumnoRepository;

    private final GrupoRepository grupoRepository;

    private final InscripcionMapper inscripcionMapper;



    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {

        Inscripcion inscripcion = prepararInscripcion(request);

        guardar(inscripcion);

        return inscripcionMapper.entidadAResponse(inscripcion);
    }




    @Override
    @Transactional(readOnly = true)
    public InscripcionResponse obtenerPorId(Long id) {

        return inscripcionMapper.entidadAResponse(
                obtenerInscripcion(id)
        );
    }



    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listar() {

        return inscripcionRepository
                .findAll()
                .stream()
                .map(inscripcionMapper::entidadAResponse)
                .toList();
    }



    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {

        Inscripcion inscripcion = obtenerInscripcion(id);

        actualizarDatos(
                inscripcion,
                request
        );

        return inscripcionMapper.entidadAResponse(
                inscripcion
        );
    }


    @Override
    public void eliminar(Long id) {

        Inscripcion inscripcion =
                obtenerInscripcion(id);

        validarPuedeEliminar(inscripcion);

        eliminarInscripcion(inscripcion);
    }


    private Inscripcion prepararInscripcion(InscripcionRequest request) {

        Alumno alumno = obtenerAlumno(request.idAlumno());

        Grupo grupo = obtenerGrupo(request.idGrupo());
        validarDuplicidad(
                alumno.getId(),
                grupo.getId()
        );

        Inscripcion inscripcion = inscripcionMapper.requestAEntidad(request);

        inscripcion.asignarDatos(
                alumno,
                grupo
        );

        return inscripcion;
    }


    private void actualizarDatos(Inscripcion inscripcion, InscripcionRequest request
    ) {

        Alumno alumno = obtenerAlumno(request.idAlumno());

        Grupo grupo = obtenerGrupo(request.idGrupo());

        validarDuplicidadActualizacion(
                alumno.getId(),
                grupo.getId(),
                inscripcion.getId()
        );

        inscripcion.actualizar(
                alumno,
                grupo
        );
    }


    private Inscripcion obtenerInscripcion(Long id) {

        return inscripcionRepository.findById(id).orElseThrow(() ->
                        new IllegalArgumentException("La inscripción no existe")
        );
    }


    private Alumno obtenerAlumno(Long id) {

        return alumnoRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El alumno no existe")
                );
    }


    private Grupo obtenerGrupo(Long id) {

        return grupoRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El grupo no existe")
                );
    }


    private void validarDuplicidad(
            Long idAlumno,
            Long idGrupo
    ) {

        boolean existe = inscripcionRepository.existsByAlumnoIdAndGrupoId(idAlumno, idGrupo);

        if (existe) {

            throw new IllegalArgumentException("El alumno ya está inscrito en este grupo"
            );
        }
    }


    private void validarDuplicidadActualizacion(Long idAlumno, Long idGrupo, Long idInscripcion) {

        boolean existe = inscripcionRepository.existsByAlumnoIdAndGrupoIdAndIdNot(idAlumno, idGrupo, idInscripcion);

        if (existe) {
            throw new IllegalArgumentException(
                    "No se puede actualizar la inscripción. " + "El alumno con id " + idAlumno + " ya está inscrito en el grupo con id " + idGrupo
            );
        }
    }


    private void validarPuedeEliminar(Inscripcion inscripcion) {

        if (inscripcion.getCalificacion() != null) {

            throw new IllegalArgumentException("No se puede eliminar la inscripción porque tiene una calificación asociada");
        }
    }


    private void guardar(
            Inscripcion inscripcion
    ) {

        inscripcionRepository.save(inscripcion);
    }


    private void eliminarInscripcion(
            Inscripcion inscripcion
    ) {

        inscripcionRepository.delete(inscripcion);
    }
}