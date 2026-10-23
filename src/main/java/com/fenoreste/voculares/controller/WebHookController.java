package com.fenoreste.voculares.controller;


import com.fenoreste.voculares.model.ConfirmaIdentidadReqVo;
import com.fenoreste.voculares.model.ConfirmaIdentidadVo;
import com.fenoreste.voculares.service.VOcularesServiceGeneral;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping({"/api/webhook/v1"})
@Slf4j
public class WebHookController {

    @Autowired
    private VOcularesServiceGeneral vOcularesServiceGeneral;

    @PostMapping(value = "confirma")
    public ConfirmaIdentidadVo confirmaIdentidadFirma(@RequestBody ConfirmaIdentidadReqVo confirmaIdentidadReqVo) {
        log.info(":::::::::::::::::Usando WebHook:"+confirmaIdentidadReqVo+"::::::::::::::::::::");
        /*Gson gson = new Gson();
        JsonObject json = gson.fromJson(cadena, JsonObject.class);
        ConfirmaIdentidadReqVo confirma = gson.fromJson(cadena, ConfirmaIdentidadReqVo.class);


        log.info(":::Json:"+json.toString());*/
        log.info(":::::Confirma Req:::"+confirmaIdentidadReqVo);
        return vOcularesServiceGeneral.confirmaIdentidadVo(confirmaIdentidadReqVo);
    }



}
