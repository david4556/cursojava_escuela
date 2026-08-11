package DAVID.escuela.entities;

import DAVID.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CURSOS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;


    public void validarDatos(String nombre, String descripcion, Integer creditos) {

        StringCustomUtils.validarTamanio(nombre, 1, 100,
                "El nombre del curso es requerido y debe tener entre 1 y 100 caracteres"
        );

        StringCustomUtils.validarTamanio(descripcion, 1, 200,
                "La descripción es requerida y debe tener entre 1 y 200 caracteres"
        );

        if (creditos == null || creditos <= 0) {
            throw new IllegalArgumentException(
                    "Los créditos deben ser mayores a 0"
            );
        }
    }

    public void actualizar(String nombre, String descripcion,
                           Integer creditos) {

        validarDatos(nombre, descripcion, creditos);


        this.nombre = nombre.trim();
        this.descripcion = descripcion.trim();
        this.creditos = creditos;

    }

}