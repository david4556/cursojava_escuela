package DAVID.escuela.entities;

import DAVID.escuela.utils.StringCustomUtils;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GRUPOS" ,uniqueConstraints = @UniqueConstraint(
        name ="GRUPO_CU_MA_AU_PE_UK",
        columnNames ={"ID_CURSO", "ID_MAESTRO", "ID_AULA" , "PERIODO"}
))
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MAESTRO", nullable = false)
    private Maestro maestro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AULA", nullable = false)
    private Aula aula;

    @Column(name = "PERIODO", nullable = false, length = 20)
    private String periodo;
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Horario> horarios = new ArrayList<>();


    public void validarDatos(Curso curso, Maestro maestro, Aula aula, String periodo) {

        if (curso == null) {
            throw new IllegalArgumentException("El curso es requerido");
        }

        if (maestro == null) {
            throw new IllegalArgumentException("El maestro es requerido");
        }

        if (aula == null) {
            throw new IllegalArgumentException("El aula es requerida");
        }

        StringCustomUtils.validarTamanio(periodo, 1, 20,
                "El periodo es requerido"
        );
    }


    public void asignarDatos(Curso curso, Maestro maestro, Aula aula, String periodo) {

        validarDatos(curso, maestro, aula, periodo);

        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;
        this.periodo = periodo.trim();
    }


    public void actualizar(Curso curso, Maestro maestro, Aula aula, String periodo) {

        asignarDatos(curso, maestro, aula, periodo);
    }
}