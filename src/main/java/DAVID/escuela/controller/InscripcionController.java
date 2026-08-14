package DAVID.escuela.controller;

import DAVID.escuela.dto.grupos.GrupoRequest;
import DAVID.escuela.dto.grupos.GrupoResponse;
import DAVID.escuela.dto.inscripciones.InscripcionRequest;
import DAVID.escuela.dto.inscripciones.InscripcionResponse;
import DAVID.escuela.services.grupos.GrupoService;
import DAVID.escuela.services.inscripciones.InscripcionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController extends  CommonController<InscripcionRequest, InscripcionResponse, InscripcionService> {
    public InscripcionController(InscripcionService service){
        super(service);}
}