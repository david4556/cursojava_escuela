package DAVID.escuela.entities;

import DAVID.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HORARIOS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIA", nullable = false)
    private DiaSemana diasemana;

    @Column(name = "HORA_INICIO", length = 5, nullable = false)
    private String horaInicio;

    @Column(name = "HORA_FIN", length = 5, nullable = false)
    private String horaFin;


    public void validarDatos(
            Grupo grupo,
            DiaSemana diasemana,
            String horaInicio,
            String horaFin
    ) {

        if (grupo == null) {
            throw new IllegalArgumentException("El grupo es requerido");
        }

        if (diasemana == null) {
            throw new IllegalArgumentException("El día es requerido");
        }

        if (horaInicio == null || horaInicio.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora de inicio es requerida");
        }

        if (horaFin == null || horaFin.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora de fin es requerida");
        }

        if (!horaInicio.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) {
            throw new IllegalArgumentException("La hora de inicio debe tener el formato HH:mm");
        }

        if (!horaFin.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) {
            throw new IllegalArgumentException("La hora de fin debe tener el formato HH:mm");
        }

        if (horaFin.compareTo(horaInicio) <= 0) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }


    public void asignarDatos(
            Grupo grupo,
            DiaSemana diasemana,
            String horaInicio,
            String horaFin
    ) {

        validarDatos(
                grupo,
                diasemana,
                horaInicio,
                horaFin
        );

        this.grupo = grupo;
        this.diasemana = diasemana;
        this.horaInicio = horaInicio.trim();
        this.horaFin = horaFin.trim();
    }



    public void actualizar(
            Grupo grupo,
            DiaSemana diasemana,
            String horaInicio,
            String horaFin
    ) {

        asignarDatos(
                grupo,
                diasemana,
                horaInicio,
                horaFin
        );
    }
}