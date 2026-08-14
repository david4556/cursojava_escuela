package DAVID.escuela.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "INSCRIPCIONES" ,uniqueConstraints = @UniqueConstraint(
        name ="INSCRIPCION_ALU_GRU_UK",
        columnNames ={"ID_ALUMNO", "ID_GRUPO"}
))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSCRIPCION")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUMNO", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Column(name = "FECHA_INSCRIPCION")
    private LocalDate fechaInscripcion = LocalDate.now();

     @OneToOne(mappedBy = "inscripcion")
     private Calificacion calificacion;
    public void validarDatos(Alumno alumno, Grupo grupo) {

        if (alumno == null) {
            throw new IllegalArgumentException("El alumno es requerido");
        }

        if (grupo == null) {
            throw new IllegalArgumentException("El grupo es requerido");
        }
    }


    public void asignarDatos(
            Alumno alumno,
            Grupo grupo) {

        validarDatos(alumno, grupo);

        this.alumno = alumno;
        this.grupo = grupo;

        if (this.fechaInscripcion == null) {
            this.fechaInscripcion = LocalDate.now();
        }
    }


    public void actualizar(
            Alumno alumno,
            Grupo grupo) {

        asignarDatos(alumno, grupo);
    }
}