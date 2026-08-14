package DAVID.escuela.controller;

import DAVID.escuela.dto.cursos.CursoRequest;
import DAVID.escuela.dto.cursos.CursoResponse;
import DAVID.escuela.services.curso.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cursos")
public class CursoController  extends  CommonController<CursoRequest, CursoResponse, CursoService> {


    public CursoController(CursoService service){
        super(service);
    }
}
