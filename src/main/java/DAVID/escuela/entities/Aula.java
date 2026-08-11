package DAVID.escuela.entities;

import DAVID.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AULAS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    public void validarDatos(String nombre,  Integer capacidad) {

        StringCustomUtils.validarTamanio(nombre, 1, 100,
                "El nombre del curso es requerido y debe tener entre 1 y 100 caracteres"
        );


        if (capacidad == null || capacidad <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad deben ser mayores a 0"
            );
        }
    }

    public void actualizar(String nombre,
                           Integer capacidad) {

        validarDatos(nombre, capacidad);


        this.nombre = nombre.trim();

        this.capacidad = capacidad;

    }
}