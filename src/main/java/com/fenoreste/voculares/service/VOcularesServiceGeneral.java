package com.fenoreste.voculares.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fenoreste.voculares.api_legalario.ApisHttp;
import com.fenoreste.voculares.entity.*;
import com.fenoreste.voculares.model.*;
import com.fenoreste.voculares.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@Slf4j
public class VOcularesServiceGeneral {

    private String idTabla = "voculares";

    @Autowired
    private ITablaService tablaService;
    @Autowired
    private IPersonaService personaService;
    @Autowired
    private Util util;
    @Autowired
    private IIdentidadService identidadService;
    @Autowired
    private IAuxiliarService auxiliarService;
    @Autowired
    private IDigitalVOcularService digitalVOcularService;

    @Autowired
    private ApisHttp apisHttp;


    private Tabla tabla;
    private TablaPK tablaPK;

    public IdentidadVoResponse identidadCrear(String opa) {
        IdentidadVoResponse identidadVoResponse = new IdentidadVoResponse();
        try {
            IdentidadCreada identidadCreada = new IdentidadCreada();
            DigitalVOcular digitalVOcular = new DigitalVOcular();

            OpaVo opaVo = util.opa(opa);
            AuxiliarPK auxiliarPK = new AuxiliarPK(opaVo.getIdorigenp(), opaVo.getIdproducto(), opaVo.getIdauxiliar());
            Auxiliar a = auxiliarService.buscarPorId(auxiliarPK);

            if (a != null) {
                PersonaPK personaPK = new PersonaPK(a.getIdorigen(), a.getIdgrupo(), a.getIdsocio());
                Persona persona = personaService.buscarPorId(personaPK);

                if (persona != null) {
                    log.info("::::::Vamos a guardar registros digitales:::::::::::");
                    //Guardamos registros
                    digitalVOcular.setAuxiliarPK(a.getPk());
                    digitalVOcular.setIdorigen(persona.getPk().getIdorigen());
                    digitalVOcular.setIdgrupo(persona.getPk().getIdgrupo());
                    digitalVOcular.setIdsocio(persona.getPk().getIdsocio());
                    digitalVOcular.setFecha_captura(new Date());
                    digitalVOcular.setStatus("Pendiente");
                    digitalVOcular.setMensajeFinal("En proceso...");
                    log.info(":::::::::::::::Guardando::::::::::::::");
                    digitalVOcularService.guardar(digitalVOcular);

                    tablaPK = new TablaPK(idTabla, "signer_data");
                    tabla = tablaService.buscarPorId(tablaPK);

                    String correo = persona.getEmail();

                    SignerReqVo signer = new SignerReqVo();
                    signer.setEmail(correo);
                    signer.setPhone(persona.getCelular());
                    signer.setFullname(String.format("%06d",a.getPk().getIdorigenp())+String.format("%05d",a.getPk().getIdproducto())+String.format("%08d",a.getPk().getIdauxiliar())+" "+persona.getNombre() + " " + persona.getAppaterno() + " " + persona.getApmaterno());
                    signer.setStatus("pending");
                    signer.setStatus_identity("active");
                    signer.setFlow_id(tabla.getDato3());
                    signer.setOrganization_id(tabla.getDato2());

                    SignersVoReq signersVoReq = new SignersVoReq();
                    List<SignerReqVo> lista = new ArrayList<>();
                    lista.add(signer);


                    signersVoReq.setSigners(lista);
                    identidadVoResponse = apisHttp.identidadCrear(signersVoReq);


                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode dataArray = mapper.valueToTree(identidadVoResponse.getData());
                    if (identidadVoResponse.isSuccess()) {
                        //Actualizamos y guardamos datos de la identidad creada
                        JsonNode firstElement = dataArray.get(0);
                        String id = firstElement.get("_id").asText();
                        identidadCreada.setIdidentidad_primaria(id);
                        identidadCreada.setAuxiliarPK(digitalVOcular.getAuxiliarPK());
                        //identidadCreada.setConfirmada_primaria(true);
                        identidadService.guardar(identidadCreada);
                        digitalVOcular.setMensajeFinal("Identidad primaria creada pendiente por confirmar");
                        digitalVOcularService.guardar(digitalVOcular);
                    }
                }
            }


        } catch (Exception e) {
            log.error("::::::::::::Error al crear validacion de identidad:::::::::::" + e.getMessage());
        }
        return identidadVoResponse;
    }


    public IdentidadAdicionalResVo identidadCrearAdicional(IdentidadAdicionalReqVoLocal identidadAdicionalReqVoLocal) {
        IdentidadAdicionalResVo identidadAdicionalResVo = new IdentidadAdicionalResVo();
        try {
            OpaVo opaVo = util.opa(identidadAdicionalReqVoLocal.getOpa());
            AuxiliarPK auxiliarPK = new AuxiliarPK(opaVo.getIdorigenp(), opaVo.getIdproducto(), opaVo.getIdauxiliar());
            IdentidadCreada identidadCreada = identidadService.buscarPorId(auxiliarPK);
            IdentidadAdicionalReqVo identidadAdicionalReqVo = new IdentidadAdicionalReqVo();


            switch (identidadAdicionalReqVoLocal.getIdOperacion()) {
                case 0://Solo adicional para identidad padre
                    identidadAdicionalReqVo.setOnboarding_id(identidadCreada.getIdidentidad_primaria());
                    identidadAdicionalReqVo.setSend_invite(true);
                    //Buscar el idFlowEnTablas
                    tablaPK = new TablaPK(idTabla,"flow_id_adicional");
                    tabla = tablaService.buscarPorId(tablaPK);
                    identidadAdicionalReqVo.setFlow_id(tabla.getDato1());
                    identidadAdicionalResVo= apisHttp.identidadCrearAdditional(identidadAdicionalReqVo);
                    if (identidadAdicionalResVo.isSuccess()) {
                        identidadCreada.setIdidentidad_adicional(identidadAdicionalResVo.getData().getOnboarding().get_id());
                        identidadService.guardar(identidadCreada);
                    }
                    break;
                case 1://Adicional del conyuge
                    identidadAdicionalReqVo.setOnboarding_id(identidadCreada.getIdidentidad_primaria());
                    identidadAdicionalReqVo.setSend_invite(true);
                    //Buscar el idFlowEnTablas
                    tablaPK = new TablaPK(idTabla,"flow_id_adicional_hija");
                    tabla = tablaService.buscarPorId(tablaPK);
                    identidadAdicionalReqVo.setFlow_id(tabla.getDato1());
                    ParticipanteIndirectoVo participanteIndirectoVo = new ParticipanteIndirectoVo();
                    OgsVo ogs = util.ogs(identidadAdicionalReqVoLocal.getOgs());
                    PersonaPK personaPK = new PersonaPK(ogs.getIdorigen(), ogs.getIdgrupo(), ogs.getIdsocio());
                    Persona persona = personaService.buscarPorId(personaPK);
                    participanteIndirectoVo.setFullname(persona.getNombre() + " " + persona.getAppaterno() + " " + persona.getApmaterno());
                    participanteIndirectoVo.setEmail(persona.getEmail());
                    participanteIndirectoVo.setPhone(persona.getCelular());
                    participanteIndirectoVo.setFlow_name("OBLIGADO SOLIDARIO");

                    identidadAdicionalReqVo.setAssociate_participant(participanteIndirectoVo);

                    identidadAdicionalResVo= apisHttp.identidadCrearAdditional(identidadAdicionalReqVo);
                    if (identidadAdicionalResVo.isSuccess()) {
                        identidadCreada.setIdidentidad_participante_indirecto(identidadAdicionalResVo.getData().getOnboarding().get_id());
                        identidadCreada.setIdorigen_participante_indirecto(personaPK.getIdorigen());
                        identidadCreada.setIdgrupo_participante_indirecto(personaPK.getIdgrupo());
                        identidadCreada.setIdsocio_participante_indirecto(personaPK.getIdsocio());
                        identidadService.guardar(identidadCreada);
                    }
                    break;
                case 2://Adicional para identidad padre pero de tipo conyuge
                    identidadAdicionalReqVo.setOnboarding_id(identidadCreada.getIdidentidad_participante_indirecto());
                    identidadAdicionalReqVo.setSend_invite(true);
                    //Buscar el idFlowEnTablas
                    tablaPK = new TablaPK(idTabla,"flow_id_adicional_hija2");
                    tabla = tablaService.buscarPorId(tablaPK);
                    identidadAdicionalReqVo.setFlow_id(tabla.getDato1());
                    identidadAdicionalResVo= apisHttp.identidadCrearAdditional(identidadAdicionalReqVo);
                    if(identidadAdicionalResVo.isSuccess()) {
                        identidadCreada.setIdidentidad_participante_indirecto_adicional(identidadAdicionalResVo.getData().getOnboarding().get_id());
                        identidadService.guardar(identidadCreada);
                    }
                    break;
            }

        } catch (Exception e) {
            log.error("::::::::::::Error al crear validacion de identidad:::::::::::" + e.getMessage());
        }
        return identidadAdicionalResVo;
    }


    public ConfirmaIdentidadVo confirmaIdentidadVo(ConfirmaIdentidadReqVo confirmaIdentidadReqVo) {
        ConfirmaIdentidadVo confirmaIdentidadVo = new ConfirmaIdentidadVo();
        log.info(":::::::::::::::::::Vamos a confirmar identidad voculares:::::::::::::::::::::"+confirmaIdentidadReqVo.getOnboarding_id());
        try {
            IdentidadCreada identidad = identidadService.buscarPorIdIdentidad(confirmaIdentidadReqVo.getOnboarding_id());

            log.info("La identidad que se encontro:::"+identidad);
            boolean paso = false;
            if (identidad != null) {
                //Verificamos que identidad se esta confirmando
                DigitalVOcular digitalVOcular = digitalVOcularService.buscarPorId(identidad.getAuxiliarPK());
                if (confirmaIdentidadReqVo.getOnboarding_id().equals(identidad.getIdidentidad_primaria()) && !identidad.isConfirmada_primaria()) {
                    log.info("::::::::::::Identidad primaria:::::::::::");
                    identidad.setConfirmada_primaria(true);
                    identidadService.guardar(identidad);
                    //Tambien guardamos el registro principal
                    digitalVOcular.setOk_identidad(true);
                    digitalVOcular.setMensajeFinal("Identidad primaria confirmada");
                    confirmaIdentidadVo.setMessage("Confirmada");
                    confirmaIdentidadVo.setSucces(true);
                     paso = true;
                }

                if (!identidad.isConfirmada_adicional() && !paso) {
                    log.info("Si entra en adicional:"+ confirmaIdentidadReqVo.getOnboarding_id()+","+identidad.getIdidentidad_adicional());
                    if (confirmaIdentidadReqVo.getOnboarding_id().trim().equals(identidad.getIdidentidad_adicional().trim()) && identidad.isConfirmada_primaria()) {
                        log.info("::::::::::::Identidad adicional:::::::::::");
                        identidad.setConfirmada_adicional(true);
                        digitalVOcular.setMensajeFinal("Identidad adicional confirmada");
                        confirmaIdentidadVo.setMessage("Confirmada");
                        confirmaIdentidadVo.setSucces(true);
                    }
                }

                if (!identidad.isConfirmada_participante_indirecto()) {
                    if (confirmaIdentidadReqVo.getOnboarding_id().equals(identidad.getIdidentidad_participante_indirecto())) {
                        log.info("::::::::::::Identidad participante indirecto adicional:::::::::::");
                        identidad.setConfirmada_participante_indirecto(true);
                        digitalVOcular.setMensajeFinal("Identidad participante indirecto confirmada");
                        confirmaIdentidadVo.setMessage("Confirmada");
                        confirmaIdentidadVo.setSucces(true);
                        paso = true;
                    }
                }

                if (!identidad.isConfirmada_participante_indirecto_adicional() && !paso) {
                    if (confirmaIdentidadReqVo.getOnboarding_id().equals(identidad.getIdidentidad_participante_indirecto_adicional()) && identidad.isConfirmada_participante_indirecto()) {
                        log.info("::::::::::::Identidad participante indirecto adicional:::::::::::");
                        identidad.setConfirmada_participante_indirecto_adicional(true);
                        digitalVOcular.setMensajeFinal("Identidad participante indirecto adicional confirmada");
                        digitalVOcular.setStatus("Terminado");
                        confirmaIdentidadVo.setMessage("Confirmada");
                        confirmaIdentidadVo.setSucces(true);
                    }
                }
                identidadService.guardar(identidad);
                digitalVOcularService.guardar(digitalVOcular);
            }

        } catch (Exception e) {
            log.error("::::::::::::Error al confirmar identidad:::::::::::::" + e.getMessage() + ",identidad:" + confirmaIdentidadReqVo.getOnboarding_id());
            confirmaIdentidadVo.setMessage("Error al confirmar identidad:::::::::::::" + e.getMessage() + ",identidad:" + confirmaIdentidadReqVo.getOnboarding_id());

        }
        return confirmaIdentidadVo;
    }

}
