package com.fenoreste.voculares.service;


import com.fenoreste.voculares.entity.Tabla;
import com.fenoreste.voculares.entity.TablaPK;

public interface ITablaService {

	public Tabla buscarPorId(TablaPK pk);
	public void insertar(Tabla tabla);


}
