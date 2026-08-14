package DAVID.escuela.controller;

import DAVID.escuela.dto.aulas.AulaRequest;
import DAVID.escuela.dto.aulas.AulaResponse;
import DAVID.escuela.dto.calificaciones.CalificacionRequest;
import DAVID.escuela.dto.calificaciones.CalificacionResponse;
import DAVID.escuela.services.aulas.AulaService;
import DAVID.escuela.services.calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/calificaciones")
public class CalificacionController extends  CommonController<CalificacionRequest, CalificacionResponse, CalificacionService> {
    public CalificacionController(CalificacionService service){
        super(service);}
}
