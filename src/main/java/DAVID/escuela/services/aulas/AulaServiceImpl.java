package DAVID.escuela.services.aulas;
import DAVID.escuela.dto.aulas.AulaRequest;
import DAVID.escuela.dto.aulas.AulaResponse;
import DAVID.escuela.dto.cursos.CursoRequest;
import DAVID.escuela.entities.Aula;
import DAVID.escuela.entities.Curso;
import DAVID.escuela.exceptions.EntidadRelacionadaException;
import DAVID.escuela.mapers.AulaMapper;
import DAVID.escuela.repositories.AulaRepository;
import DAVID.escuela.repositories.GrupoRepository;
import DAVID.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;
    private final GrupoRepository grupoRepository;
    private final AulaMapper aulaMapper;


    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar() {
        log.info("Listando Aulas");

        return aulaRepository.findAll().stream()
                .map(aulaMapper:: entidadAResponse).toList();
    }

    @Override
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entidadAResponse(obtenerAula(id));
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {

        log.info("Registrando nueva aula ");
        validarDatosUnicos(request);
       Aula aula = aulaMapper.requestAEntidad(request);

        aulaRepository.save(aula);
        log.info("nuevo curso {} registrado", aula.getNombre());
        return aulaMapper.entidadAResponse(aula);

    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        Aula aula  = obtenerAula(id);

        log.info("actualizando registro de cursos:{}", id);

        validarCambiosUnicos(request , id );


        aula.actualizar(
                request.nombre(),
                request.capacidad()
        );
        log.info("Aula {} actualizado", aula.getNombre());

        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public void eliminar(Long id) {
        Aula aula = obtenerAula(id);

        log.info("Eliminando curso con id :{}", id);

        if (grupoRepository.existsByAulaId(id))
            throw new EntidadRelacionadaException(
                    "nose puede eliminar"
            );



    }




    private Aula obtenerAula(Long id){
        return ServiceUtils.obtenerEntidadOException(aulaRepository,id, Aula.class);
    }

    private void validarDatosUnicos(AulaRequest request){
        log.info("validando nombre unico");

        if (aulaRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("ya existe una clase con este nombre  " +request.nombre());

    }

    private void validarCambiosUnicos(AulaRequest request ,Long id){
        log.info("validando email unico");

        if (aulaRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(),id))
            throw new IllegalArgumentException("ya existe un maestro " +request.nombre());

    }
}
