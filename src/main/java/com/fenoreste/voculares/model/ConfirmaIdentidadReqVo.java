package com.fenoreste.voculares.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfirmaIdentidadReqVo implements Serializable {

    private String onboarding_id;
    private String estatus;
    private String type;
    private String user_email;
    private String fullname;
    private String phone;

    private static final long serialVersionUID = 1L;
}
