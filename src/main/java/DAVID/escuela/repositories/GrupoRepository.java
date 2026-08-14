package DAVID.escuela.repositories;

import DAVID.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository  extends JpaRepository<Grupo, Long> {

    boolean existsByMaestroId(Long idMaestro);
    boolean existsByCursoId(Long idCurso);
    boolean existsByAulaId(Long idAula);

    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(
            Long idCurso,
            Long idMaestro,
            Long idAula,
            String periodo
    );

    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
            Long idCurso,
            Long idMaestro,
            Long idAula,
            String periodo,
            Long idGrupo
    );
}
