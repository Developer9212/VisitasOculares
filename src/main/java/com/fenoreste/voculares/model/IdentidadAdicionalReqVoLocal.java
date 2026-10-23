package com.fenoreste.voculares.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentidadAdicionalReqVoLocal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String opa;
    private String ogs;
    private int idOperacion;
}
