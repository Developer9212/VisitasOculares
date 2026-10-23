package com.fenoreste.voculares.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "identidad_creada_voculares")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class IdentidadCreada implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private AuxiliarPK auxiliarPK;
    private Integer idorigen_participante_indirecto;
    private Integer idgrupo_participante_indirecto;
    private Integer idsocio_participante_indirecto;
    private String ididentidad_primaria;
    private String ididentidad_adicional;
    private String ididentidad_participante_indirecto;
    private String ididentidad_participante_indirecto_adicional;
    private boolean confirmada_primaria = false;
    private boolean confirmada_adicional = false;
    private boolean confirmada_participante_indirecto = false;
    private boolean confirmada_participante_indirecto_adicional = false;

}
