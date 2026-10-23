package com.fenoreste.voculares.service;


import com.fenoreste.voculares.entity.AuxiliarPK;
import com.fenoreste.voculares.entity.IdentidadCreada;

import java.util.Date;
import java.util.List;

public interface IIdentidadService {

    public IdentidadCreada guardar(IdentidadCreada identidad);
    public IdentidadCreada buscarPorIdIdentidad(String idIdentidad);
    public IdentidadCreada buscarPorId(AuxiliarPK auxiliarPK);


}
