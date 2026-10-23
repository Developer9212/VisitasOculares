package com.fenoreste.voculares.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TablaPK implements Serializable{

	@Column(name="idtabla")
	private String idtabla;
	@Column(name="idelemento")
	private String idelemento;

}
