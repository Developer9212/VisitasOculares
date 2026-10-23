package com.fenoreste.voculares.dao;

import com.fenoreste.voculares.entity.Persona;
import com.fenoreste.voculares.entity.PersonaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PersonaDao extends JpaRepository<Persona, PersonaPK> {
	
	@Query(value = "SELECT * FROM personas WHERE idorigen=?1 AND idgrupo=?2 and idsocio=3 and estatus=true",nativeQuery = true)
	public Persona findByOGS(Integer idorigen,Integer idgrupo,Integer idsocio);
      
}
