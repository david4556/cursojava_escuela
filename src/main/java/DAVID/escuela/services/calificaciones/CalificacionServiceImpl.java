package DAVID.escuela.services.calificaciones;

import DAVID.escuela.dto.calificaciones.CalificacionRequest;
import DAVID.escuela.dto.calificaciones.CalificacionResponse;
import DAVID.escuela.entities.Calificacion;
import DAVID.escuela.entities.Inscripcion;
import DAVID.escuela.mapers.CalificacionMapper;
import DAVID.escuela.repositories.CalificacionRepository;
import DAVID.escuela.repositories.InscripcionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CalificacionServiceImpl implements CalificacionService {


    private final CalificacionRepository calificacionRepository;

    private final InscripcionRepository inscripcionRepository;

    private final CalificacionMapper calificacionMapper;



    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {

        Inscripcion inscripcion = obtenerInscripcion(request.idInscripcion());


        validarDuplicidad(request.idInscripcion());


        Calificacion calificacion = calificacionMapper.requestAEntidad(request);

        calificacion.asignarDatos(inscripcion, request.calificacion());


        guardar(calificacion);


        return calificacionMapper.entidadAResponse(
                calificacion
        );
    }


    @Override
    @Transactional(readOnly = true)
    public CalificacionResponse obtenerPorId(Long id) {

        Calificacion calificacion = obtenerCalificacion(id);

        return calificacionMapper.entidadAResponse(calificacion);
    }



    @Override
    @Transactional(readOnly = true)
    public List<CalificacionResponse> listar() {

        return calificacionRepository.findAll().stream().map(calificacionMapper::entidadAResponse).toList();
    }



    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id
    ) {

        Calificacion calificacion = obtenerCalificacion(id);


        validarDuplicidadActualizacion(request.idInscripcion(), id);


        Inscripcion inscripcion = obtenerInscripcion(request.idInscripcion());


        calificacion.asignarDatos(inscripcion, request.calificacion());


        return calificacionMapper.entidadAResponse(calificacion);
    }



    @Override
    public void eliminar(Long id) {

        Calificacion calificacion = obtenerCalificacion(id);

        calificacionRepository.delete(calificacion);
    }





    private Calificacion obtenerCalificacion(
            Long id
    ) {

        return calificacionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("La calificación no existe"));
    }


    private Inscripcion obtenerInscripcion(Long id) {

        return inscripcionRepository.findById(id).orElseThrow(() ->
                        new IllegalArgumentException("La inscripción no existe"));
    }


    private void validarDuplicidad(Long idInscripcion) {

        boolean existe = calificacionRepository.existsByInscripcionId(idInscripcion);

        if (existe) {

            throw new IllegalArgumentException("La inscripción ya tiene una calificación");
        }
    }


    private void validarDuplicidadActualizacion(Long idInscripcion, Long idCalificacion) {

        boolean existe = calificacionRepository.existsByInscripcionIdAndIdNot(idInscripcion, idCalificacion);

        if (existe) {

            throw new IllegalArgumentException("La inscripción ya tiene otra calificación");
        }
    }


    private void guardar(Calificacion calificacion) {

        calificacionRepository.save(calificacion);
    }
}