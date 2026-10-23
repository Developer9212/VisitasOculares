package com.fenoreste.voculares.controller;


import com.fenoreste.voculares.model.IdentidadAdicionalReqVoLocal;
import com.fenoreste.voculares.model.IdentidadAdicionalResVo;
import com.fenoreste.voculares.model.IdentidadVoResponse;
import com.fenoreste.voculares.service.VOcularesServiceGeneral;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping({"/api/v1"})
@Slf4j
public class VOcularesController {

    @Autowired
    private VOcularesServiceGeneral vOcularesServiceGeneral;

    @GetMapping("/valida-identidad")
    private IdentidadVoResponse creaDocumento(@RequestParam(name = "opa") String opa) {
        log.info(":::::::::::::::Generando identidad para opa:" + opa + ":::::::::::::::::::");
        return vOcularesServiceGeneral.identidadCrear(opa);
    }

    @PostMapping("/valida-identidad-adicional")
    private IdentidadAdicionalResVo crearIdentidadAdicional(@RequestBody IdentidadAdicionalReqVoLocal identidadAdicional) {
        log.info("Generando identidad adicional para opA:" + identidadAdicional.getOpa()+ " con opcion:"+identidadAdicional.getIdOperacion());
        return vOcularesServiceGeneral.identidadCrearAdicional(identidadAdicional);
    }


}
