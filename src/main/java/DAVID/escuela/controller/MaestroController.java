package DAVID.escuela.controller;

import DAVID.escuela.dto.maestros.MaestroRequest;
import DAVID.escuela.dto.maestros.MaestroResponse;
import DAVID.escuela.services.maestros.MaestroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maestros")
public class MaestroController extends  CommonController<MaestroRequest, MaestroResponse, MaestroService> {

    public MaestroController(MaestroService service ){
        super(service);
    }
}
