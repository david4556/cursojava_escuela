package DAVID.escuela.controller;


import DAVID.escuela.dto.grupos.GrupoRequest;
import DAVID.escuela.dto.grupos.GrupoResponse;
import DAVID.escuela.services.aulas.AulaService;
import DAVID.escuela.services.grupos.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends  CommonController<GrupoRequest, GrupoResponse, GrupoService> {
    public GrupoController(GrupoService service){
            super(service);}
}
