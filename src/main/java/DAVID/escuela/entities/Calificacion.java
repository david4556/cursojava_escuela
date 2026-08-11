package DAVID.escuela.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CALIFICACIONES")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALIFICACION")
    private Long id;


    @Column(name = "CALIFICACION", nullable = false)
    private BigDecimal calificacion;

    @Builder.Default
    @Column(name = "FECHA_REGISTRO", nullable = false   )
    private LocalDate fechaRegistro = LocalDate.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSCRIPCION", nullable = false,unique = true)
    private Inscripcion inscripcion;

}
