DROP TABLE IF EXISTS digital_voculares;
CREATE TABLE voculares_digital(
    idorigenp integer,
    idproducto integer,
    idauxiliar integer,
    idorigen integer,
    idgrupo integer,
    idsocio integer,
    fecha_captura date,
    status varchar(45),
    ok_identidad boolean,
	mensajeFinal text,
    primary key (idorigenp,idproducto,idauxiliar)
);




DROP TABLE IF EXISTS identidad_creada_voculares;
CREATE TABLE identidad_creada_voculares(
    idorigenp    integer,
    idproducto   integer,
    idauxiliar   integer,
    idorigen_participante_indirecto   integer,
    idgrupo_participante_indirecto integer,
    idsocio_participante_indirecto integer,
    ididentidad_primaria  text,
    ididentidad_adicional  text,
    ididentidad_participante_indirecto text,
    ididentidad_participante_indirecto_adicional text;
    confirmada_primaria   boolean,
    confirmada_adicional boolean,
    confirmada_participante_indirecto boolean,
    confirmada_participante_indirecto_adicional boolean,

    PRIMARY KEY (idorigenp,idproducto,idauxiliar)
);
