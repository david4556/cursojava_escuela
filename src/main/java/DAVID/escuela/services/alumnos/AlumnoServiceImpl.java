package DAVID.escuela.services.alumnos;

import DAVID.escuela.dto.alumnos.AlumnoRequest;
import DAVID.escuela.dto.alumnos.AlumnoResponse;
import DAVID.escuela.entities.Alumno;
import DAVID.escuela.exceptions.EntidadRelacionadaException;
import DAVID.escuela.mapers.AlumnoMapper;
import DAVID.escuela.repositories.AlumnoRepository;
import DAVID.escuela.repositories.InscripcionRepository;
import DAVID.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;



@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AlumnoServiceImpl  implements AlumnoService{

    private final AlumnoMapper alumnoMapper;
    private final AlumnoRepository alumnoRepository;

    private final InscripcionRepository inscripcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {

        log.info("listando todos lo s alumnos ");
        return  alumnoRepository.findAll().stream()
        .map(alumnoMapper:: entidadAResponse).toList();
    }



    @Override
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {

        log.info("Registrando nuevo alumno...");

        Alumno alumno = alumnoMapper.requestAEntidad(
                request,
                generarEmail(request),
                generarMatricula(request)


        );
        alumnoRepository.save(alumno);

        log.info("nuevo alumno {} registrandp", alumno.getNombre());

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {

        Alumno alumno = obtenerAlumno(id);

        log.info("Actualizando alumno con id {}",id);


        if (alumno.cambioDeDatos(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()
            )
        ){

            alumno.actualizar(
                    request.nombre(),
                    request.apellidoPaterno(),
                    request.apellidoMaterno(),
                    generarEmail(request),
                    generarMatricula(request)

            );

            log.info("datos actualizados {} para el alumno" , id);
        }
        return alumnoMapper.entidadAResponse(alumno);

    }

    @Override
    public void eliminar(Long id) {

        Alumno alumno = obtenerAlumno(id);

        log.info("eliminando con alumno con id {}", id);

        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar"
            );

        alumnoRepository.delete(alumno);

        log.info("alumno con id {} eliminado", id);



    }
     private Alumno obtenerAlumno(Long id){
        return ServiceUtils.obtenerEntidadOException(alumnoRepository, id , Alumno.class);
     }

     private String generarMatricula(AlumnoRequest request){
        log.info("generando matricula ");

        String matricula = alumnoRepository.generarMatricula(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()

        );

         return matricula;
     }

    private String generarEmail(AlumnoRequest request){
        log.info("generando email ");

        String email = alumnoRepository.generarEmail(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()

        );
return  email;
    }
}
