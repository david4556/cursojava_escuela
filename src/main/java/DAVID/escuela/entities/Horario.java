package DAVID.escuela.entities;
import DAVID.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    @JoinColumn(name = "ID_GRUPO ", nullable = false)
    private Grupo grupo;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIA", nullable = false   )
    private DiaSemana diasemana;


    @Column(name = "HORA_INICIO", length = 5, nullable = false  )
    private String horaInicio;

    @Column(name = "HORA_FIN", length = 5, nullable = false  )
    private String horaFin;
}
