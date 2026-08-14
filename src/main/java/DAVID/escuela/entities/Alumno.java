package DAVID.escuela.entities;


import DAVID.escuela.utils.StringCustomUtils;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "ALUMNOS")
@Getter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUMNO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "MATRICULA", nullable = false, unique = true, length = 10)
    private String matricula;

    @Builder.Default
    @Column(name = "FECHA_INGRESO")
    private LocalDate fechaIngreso = LocalDate.now()    ;

    @Builder.Default
    @OneToMany(mappedBy = "alumno")
    private List<Inscripcion> inscripciones = new ArrayList<>();

    public void validarDatos(String nombre, String apellidoPaterno,
                             String apellidoMaterno) {

        StringCustomUtils.validarTamanio(nombre,1,50,
                "El nombre es requerido y debe tener entre 5 y 30 caracteres");
        StringCustomUtils.validarTamanio(apellidoPaterno,1,50,
                "El apellido paterno es requerido y debe tener entre 5 y 30 caracteres");
        StringCustomUtils.validarTamanio(apellidoMaterno,1,50,
                "El apellido materno es requerido y debe tener entre 5 y 30 caracteres");





    }
    public boolean cambioDeDatos(String nombre, String apellidoPaterno,
                             String apellidoMaterno) {
return !this.nombre.equals(nombre) ||
        !this.nombre.equals(apellidoPaterno) ||
        !this.nombre.equals(apellidoMaterno) ;

    }


    public void asignarDatosAcademicos(String email, String matricula) {

        StringCustomUtils.validarTamanio(email,1,100,
                "El email es requerido y debe tener entre 5 y 30 caracteres");
        StringCustomUtils.validarTamanio(matricula,1,50,
                "La matricula es requerido y debe tener entre 5 y 30 caracteres");
        this.email = email.trim();
        this.matricula = matricula.trim();

    }

    public void actualizar(String nombre, String apellidoPaterno,
                           String apellidoMaterno,String email,String matricula) {

        validarDatos(nombre,apellidoPaterno,apellidoMaterno );
        asignarDatosAcademicos(email,matricula);

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();

    }
    public BigDecimal calculaPromedio(){



       List <BigDecimal> calificaciones = inscripciones.stream()
                .map(Inscripcion::getCalificacion)
        .filter(Objects::nonNull)
                .map(Calificacion::getCalificacion)
        .filter(Objects::nonNull).toList();

        if (calificaciones.isEmpty())
            return BigDecimal.ZERO;

            BigDecimal suma= calificaciones.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
             return suma.divide(
                BigDecimal.valueOf(calificaciones.size()),
                2, RoundingMode.HALF_UP);

    }


}