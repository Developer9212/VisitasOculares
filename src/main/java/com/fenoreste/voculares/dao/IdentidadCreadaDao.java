package com.fenoreste.voculares.dao;

import com.fenoreste.voculares.entity.AuxiliarPK;
import com.fenoreste.voculares.entity.IdentidadCreada;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface IdentidadCreadaDao extends CrudRepository<IdentidadCreada, AuxiliarPK> {

    @Query(value = "SELECT * FROM identidad_creada_voculares WHERE TRIM(ididentidad_primaria) = TRIM(?1) or TRIM(ididentidad_adicional)=TRIM(?1) or TRIM(ididentidad_participante_indirecto) = TRIM(?1) or TRIM(ididentidad_participante_indirecto_adicional) = TRIM(?1)", nativeQuery = true)
    public IdentidadCreada buscarPorIdIdentidad(String id);



}
