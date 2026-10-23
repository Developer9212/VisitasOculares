package com.fenoreste.voculares.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class IdentidadVoResponse implements Serializable {

    private boolean success;
    private JsonNode data;
    private String message;

    private static final long serialVersionUID = 1L;
}
