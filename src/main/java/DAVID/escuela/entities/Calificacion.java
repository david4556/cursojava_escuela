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
    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDate fechaRegistro = LocalDate.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSCRIPCION", nullable = false, unique = true)
    private Inscripcion inscripcion;


    public void validarDatos(Inscripcion inscripcion, BigDecimal calificacion) {

        if (inscripcion == null) {
            throw new IllegalArgumentException("La inscripción es requerida");
        }

        if (calificacion == null) {
            throw new IllegalArgumentException("La calificación es requerida");
        }

        if (calificacion.compareTo(BigDecimal.ZERO) < 0 ||
                calificacion.compareTo(BigDecimal.TEN) > 0) {

            throw new IllegalArgumentException("La calificación debe estar entre 0 y 10");
        }
    }


    public void asignarDatos(Inscripcion inscripcion, BigDecimal calificacion) {

        validarDatos(inscripcion, calificacion);

        this.inscripcion = inscripcion;
        this.calificacion = calificacion;
    }


    public void actualizar(BigDecimal calificacion) {

        validarDatos(this.inscripcion, calificacion);

        this.calificacion = calificacion;
    }
}