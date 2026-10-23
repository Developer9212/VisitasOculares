package com.fenoreste.voculares.service;

import com.fenoreste.voculares.dao.DigitalVOcularDao;
import com.fenoreste.voculares.entity.AuxiliarPK;
import com.fenoreste.voculares.entity.DigitalVOcular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DigitalVOcularServiceImpl implements IDigitalVOcularService{

    @Autowired
    private DigitalVOcularDao digitalVOcularDao;

    @Override
    public void guardar(DigitalVOcular digitalVOcular) {
        digitalVOcularDao.save(digitalVOcular);
    }

    @Override
    public DigitalVOcular buscarPorId(AuxiliarPK auxiliarPK) {
        return digitalVOcularDao.findById(auxiliarPK).orElse(null);
    }
}
