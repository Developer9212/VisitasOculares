package com.fenoreste.voculares.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipanteIndirectoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fullname;
    private String email;
    private String phone;
    private String flow_name;


}
