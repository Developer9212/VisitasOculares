package com.fenoreste.voculares.service;

import com.fenoreste.voculares.dao.AuxiliarDao;
import com.fenoreste.voculares.entity.Auxiliar;
import com.fenoreste.voculares.entity.AuxiliarPK;
import com.fenoreste.voculares.entity.PersonaPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuxiliarServiceImpl implements IAuxiliarService {

    @Autowired
    private AuxiliarDao auxiliarDao;

    @Override
    public Auxiliar buscarPorId(AuxiliarPK auxiliarPK) {
        return auxiliarDao.findById(auxiliarPK).orElse(null);
    }

    @Override
    public Auxiliar buscarPorOgsyProducto(PersonaPK pk, Integer idproducto, Integer estatus) {
        return auxiliarDao.bucarPorOgsYProducto(pk.getIdorigen(),pk.getIdgrupo(),pk.getIdsocio(),idproducto,estatus);
    }
}
