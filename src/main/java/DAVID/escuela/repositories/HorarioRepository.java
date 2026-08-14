
    package DAVID.escuela.repositories;

import DAVID.escuela.entities.Horario;
import DAVID.escuela.enums.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

    @Repository
    public interface HorarioRepository extends JpaRepository<Horario, Long> {

        boolean existsByGrupoId(Long idGrupo);
        @Query("""
        SELECT COUNT(h) > 0
        FROM Horario h
        WHERE h.diasemana = :dia
        AND (
            h.grupo.id = :idGrupo
            OR h.grupo.aula.id = :idAula
        )
        AND h.horaInicio < :horaFin
        AND h.horaFin > :horaInicio
    """)
        boolean existeTraslape(
                @Param("idGrupo") Long idGrupo,
                @Param("idAula") Long idAula,
                @Param("dia") DiaSemana dia,
                @Param("horaInicio") String horaInicio,
                @Param("horaFin") String horaFin
        );


        @Query("""
        SELECT COUNT(h) > 0
        FROM Horario h
        WHERE h.id <> :idHorario
        AND h.diasemana = :dia
        AND (
            h.grupo.id = :idGrupo
            OR h.grupo.aula.id = :idAula
        )
        AND h.horaInicio < :horaFin
        AND h.horaFin > :horaInicio
    """)
        boolean existeTraslapeActualizacion(
                @Param("idGrupo") Long idGrupo,
                @Param("idAula") Long idAula,
                @Param("dia") DiaSemana dia,
                @Param("horaInicio") String horaInicio,
                @Param("horaFin") String horaFin,
                @Param("idHorario") Long idHorario
        );
    }

