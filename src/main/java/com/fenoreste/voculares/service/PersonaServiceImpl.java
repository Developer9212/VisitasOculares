package com.fenoreste.voculares.service;


import com.fenoreste.voculares.dao.PersonaDao;
import com.fenoreste.voculares.entity.Persona;
import com.fenoreste.voculares.entity.PersonaPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaServiceImpl implements IPersonaService{
	
	@Autowired
	private PersonaDao personaRepository;

	@Override
	public Persona buscarPorId(PersonaPK pk) {
		return personaRepository.getById(pk);
	}

	@Override
	public Persona buscarPorOGS(PersonaPK pk) {
		return personaRepository.findByOGS(pk.getIdorigen(),pk.getIdgrupo(),pk.getIdsocio());
	}


}
