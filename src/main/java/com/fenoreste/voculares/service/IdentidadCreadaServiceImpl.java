package com.fenoreste.voculares.service;

import com.fenoreste.voculares.dao.IdentidadCreadaDao;
import com.fenoreste.voculares.entity.AuxiliarPK;
import com.fenoreste.voculares.entity.IdentidadCreada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IdentidadCreadaServiceImpl implements IIdentidadService{

    @Autowired
    private IdentidadCreadaDao identidadCreadaDao;

    @Override
    public IdentidadCreada guardar(IdentidadCreada identidad) {
        return identidadCreadaDao.save(identidad);
    }

    @Override
    public IdentidadCreada buscarPorIdIdentidad(String idIdentidad) {
        System.out.println("Id buscado:" + idIdentidad);
        return identidadCreadaDao.buscarPorIdIdentidad(idIdentidad.trim());
    }

    @Override
    public IdentidadCreada buscarPorId(AuxiliarPK auxiliarPK) {
        return identidadCreadaDao.findById(auxiliarPK).orElse(null);
    }
}
