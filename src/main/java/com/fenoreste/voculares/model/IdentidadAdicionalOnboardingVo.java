package com.fenoreste.voculares.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentidadAdicionalOnboardingVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String _id;
    private String flow_id;
    private String status;
    private String status_identity;
}
