package DAVID.escuela.services.maestros;

import DAVID.escuela.dto.maestros.MaestroRequest;
import DAVID.escuela.dto.maestros.MaestroResponse;
import DAVID.escuela.entities.Maestro;
import DAVID.escuela.exceptions.EntidadRelacionadaException;
import DAVID.escuela.mapers.MaestroMapper;
import DAVID.escuela.repositories.GrupoRepository;
import DAVID.escuela.repositories.MaestroRepository;
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
public class MaestroServiceImpl implements MaestroService {


      private final MaestroRepository maestroRepository;

      private final GrupoRepository  grupoRepository;

      private final MaestroMapper maestroMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MaestroResponse> listar() {
        log.info("listando maestros");

        return maestroRepository.findAll().stream()
                .map(maestroMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MaestroResponse obtenerPorId(Long id) {
        return maestroMapper.entidadAResponse(obtenerMaestro(id));
    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {

        log.info("Registrando nuevo maestro");
        validarDatosUnicos(request);
        Maestro maestro = maestroMapper.requestAEntidad(request);

        maestroRepository.save(maestro);

        log.info("nuevo maestro {} registrado", maestro.getNombre());

        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {
        Maestro maestro = obtenerMaestro(id);

        log.info("Actualizand maestro con id:{}", id);

        validarCambiosUnicos(request, id);

        maestro.actualizar(
            request.nombre(),
            request.apellidoPaterno(),
            request.apellidoMaterno(),
                request.email(),
                request.telefono()
        );
        log.info("Mestro {} actualizado", maestro.getNombre());
        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public void eliminar(Long id) {

        Maestro maestro = obtenerMaestro(id);

        log.info("Eliminado maestro con id:{}", id);

        if (grupoRepository.existsByMaestroId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar"
            );

        maestroRepository.delete(maestro);

        log.info("Maestro {} eliminando correctamente", maestro.getNombre());

    }

    private Maestro obtenerMaestro(Long id){
        return ServiceUtils.obtenerEntidadOException(maestroRepository,id,Maestro.class);
    }

    private void validarDatosUnicos(MaestroRequest request){
         log.info("validando email unico");

         if (maestroRepository.existsByEmailIgnoreCase(request.email().trim()))
             throw new IllegalArgumentException("ya existe un maestro " +request.email());

         log.info("validando telefono unco");

         if (maestroRepository.existsByTelefono(request.telefono().trim()))
             throw new IllegalArgumentException("ya existe un maestro con este telefono " +request.telefono());
    }

    private void validarCambiosUnicos(MaestroRequest request ,Long id){
        log.info("validando email unico");

        if (maestroRepository.existsByEmailIgnoreCaseAndIdNot(request.email().trim(),id))
            throw new IllegalArgumentException("ya existe un maestro " +request.email());

        log.info("validando telefono unco");

        if (maestroRepository.existsByTelefonoAndIdNot(request.telefono().trim(), id))
            throw new IllegalArgumentException("ya existe un maestro con este telefono " +request.telefono());
    }
}

