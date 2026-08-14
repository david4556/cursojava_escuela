package DAVID.escuela.controller;

import DAVID.escuela.dto.alumnos.AlumnoRequest;
import DAVID.escuela.dto.alumnos.AlumnoResponse;
import DAVID.escuela.dto.aulas.AulaRequest;
import DAVID.escuela.dto.aulas.AulaResponse;
import DAVID.escuela.services.alumnos.AlumnoService;
import DAVID.escuela.services.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController  extends  CommonController<AulaRequest, AulaResponse, AulaService> {
    public AulaController(AulaService service){
        super(service);}
}
