package DAVID.escuela.controller;



import DAVID.escuela.dto.horarios.HorarioRequest;
import DAVID.escuela.dto.horarios.HorarioResponse;

import DAVID.escuela.services.horarios.HorarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorariosController extends  CommonController<HorarioRequest, HorarioResponse, HorarioService> {
    public HorariosController(HorarioService service){
        super(service);
    }
}

