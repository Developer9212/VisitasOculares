package com.fenoreste.voculares.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PersonaPK implements Serializable{
    
	private Integer idorigen;
	private Integer idgrupo;
	private Integer idsocio;
	
	private static final long serialVersionUID = 1L;
}
