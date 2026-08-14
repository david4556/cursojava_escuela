package DAVID.escuela.controller;

import DAVID.escuela.dto.alumnos.AlumnoRequest;
import DAVID.escuela.dto.alumnos.AlumnoResponse;
import DAVID.escuela.services.alumnos.AlumnoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController  extends  CommonController<AlumnoRequest, AlumnoResponse, AlumnoService> {


    public AlumnoController(AlumnoService service){
        super(service);
    }
}
