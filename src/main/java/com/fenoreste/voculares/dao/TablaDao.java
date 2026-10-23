package com.fenoreste.voculares.dao;

import com.fenoreste.voculares.entity.Tabla;
import com.fenoreste.voculares.entity.TablaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TablaDao extends JpaRepository<Tabla, TablaPK>{

}
