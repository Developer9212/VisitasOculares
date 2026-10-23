package com.fenoreste.voculares.service;


import com.fenoreste.voculares.entity.Persona;
import com.fenoreste.voculares.entity.PersonaPK;

public interface IPersonaService {
	
	public Persona buscarPorId(PersonaPK pk);
    public Persona buscarPorOGS(PersonaPK pk);


}
 