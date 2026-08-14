package DAVID.escuela.repositories;

import DAVID.escuela.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepository
        extends JpaRepository<Calificacion, Long> {

    boolean existsByInscripcionId(Long idInscripcion);

    boolean existsByInscripcionIdAndIdNot(
            Long idInscripcion,
            Long idCalificacion
    );
}