package com.fenoreste.voculares.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "voculares_digital")
@Cacheable(false)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DigitalVOcular implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AuxiliarPK auxiliarPK;
    private Integer idorigen;
    private Integer idgrupo;
    private Integer idsocio;
    @Temporal(TemporalType.DATE)
    private Date fecha_captura;
    private String status;
    private boolean ok_identidad = false;
    private String mensajeFinal;

}
