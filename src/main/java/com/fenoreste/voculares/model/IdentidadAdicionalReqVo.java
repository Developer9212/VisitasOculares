package com.fenoreste.voculares.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentidadAdicionalReqVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String onboarding_id;
    private String flow_id;
    private boolean send_invite;

    //Ultimo cambio asignado
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ParticipanteIndirectoVo associate_participant;




}
