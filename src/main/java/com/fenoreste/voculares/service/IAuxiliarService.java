package com.fenoreste.voculares.service;


import com.fenoreste.voculares.entity.Auxiliar;
import com.fenoreste.voculares.entity.AuxiliarPK;
import com.fenoreste.voculares.entity.PersonaPK;

public interface IAuxiliarService {

    public Auxiliar buscarPorId(AuxiliarPK auxiliarPK);
    public Auxiliar buscarPorOgsyProducto(PersonaPK pk, Integer idproducto, Integer estatus);

}
