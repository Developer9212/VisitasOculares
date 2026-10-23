package com.fenoreste.voculares.service;

import com.fenoreste.voculares.dao.TablaDao;
import com.fenoreste.voculares.entity.Tabla;
import com.fenoreste.voculares.entity.TablaPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TablaServiceImpl implements ITablaService{
    
	@Autowired
	private TablaDao tablaDao;
	
	@Override
	public Tabla buscarPorId(TablaPK pk) {
		return tablaDao.findById(pk).orElse(null);
	}

	@Override
	public void insertar(Tabla tabla) {
		tablaDao.save(tabla);
	}



}
