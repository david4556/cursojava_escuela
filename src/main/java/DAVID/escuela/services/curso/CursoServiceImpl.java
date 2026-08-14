
        package DAVID.escuela.services.curso;

import DAVID.escuela.dto.cursos.CursoRequest;
import DAVID.escuela.dto.cursos.CursoResponse;
import DAVID.escuela.entities.Curso;
import DAVID.escuela.exceptions.EntidadRelacionadaException;
import DAVID.escuela.mapers.CursoMapper;
import DAVID.escuela.repositories.CursoRepository;
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
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final GrupoRepository grupoRepository;
    private final CursoMapper cursoMapper;


    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {

        log.info("Listando cursos");

        return cursoRepository.findAll()
                .stream()
                .map(cursoMapper::entidadAResponse)
                .toList();
    }


    @Override
    public CursoResponse obtenerPorId(Long id) {

        return cursoMapper.entidadAResponse(
                obtenerCurso(id)
        );
    }


    @Override
    public CursoResponse registrar(CursoRequest request) {

        log.info("Registrando nuevo curso");

        validarDatosUnicos(request);

        Curso curso = cursoMapper.requestAEntidad(request);

        curso.validarDatos(
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getCreditos()
        );

        cursoRepository.save(curso);

        log.info("Nuevo curso {} registrado", curso.getNombre());

        return cursoMapper.entidadAResponse(curso);
    }


    @Override
    public CursoResponse actualizar(
            CursoRequest request,
            Long id
    ) {

        Curso curso = obtenerCurso(id);

        log.info("Actualizando curso con id: {}", id);

        validarCambiosUnicos(request, id);

        curso.actualizar(
                request.nombre(),
                request.descripcion(),
                request.creditos()
        );

        log.info("Curso {} actualizado", curso.getNombre());

        return cursoMapper.entidadAResponse(curso);
    }


    @Override
    public void eliminar(Long id) {

        Curso curso = obtenerCurso(id);

        log.info("Eliminando curso con id: {}", id);

        validarQueNoTengaGrupos(id);

        cursoRepository.delete(curso);

        log.info("Curso {} eliminado", curso.getNombre());
    }


    private Curso obtenerCurso(Long id) {

        return ServiceUtils.obtenerEntidadOException(
                cursoRepository,
                id,
                Curso.class
        );
    }


    private void validarDatosUnicos(
            CursoRequest request
    ) {

        if (cursoRepository.existsByNombreIgnoreCase(
                request.nombre().trim()
        )) {

            throw new IllegalArgumentException(
                    "Ya existe un curso con este nombre: "
                            + request.nombre()
            );
        }
    }


    private void validarCambiosUnicos(
            CursoRequest request,
            Long id
    ) {

        if (cursoRepository.existsByNombreIgnoreCaseAndIdNot(
                request.nombre().trim(),
                id
        )) {

            throw new IllegalArgumentException(
                    "Ya existe otro curso con este nombre: "
                            + request.nombre()
            );
        }
    }


    private void validarQueNoTengaGrupos(
            Long id
    ) {

        if (grupoRepository.existsByCursoId(id)) {

            throw new EntidadRelacionadaException(
                    "No se puede eliminar el curso porque tiene grupos asignados"
            );
        }
    }
}

