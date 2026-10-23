package com.fenoreste.voculares.service;

import com.fenoreste.voculares.entity.AuxiliarPK;
import com.fenoreste.voculares.entity.DigitalVOcular;

public interface IDigitalVOcularService {

    public void guardar(DigitalVOcular digitalVOcular);
    public DigitalVOcular buscarPorId(AuxiliarPK auxiliarPK);
}
