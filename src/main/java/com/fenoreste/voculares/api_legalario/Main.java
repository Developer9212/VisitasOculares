package com.fenoreste.voculares.api_legalario;

import com.fenoreste.voculares.model.IdentidadAdicionalReqVo;

import java.time.LocalDate;
import java.time.Period;

public class Main {

    public static void main(String[] args) {
        LocalDate fechaNacimiento = LocalDate.parse("1994-12-15");

        int edad = Period.between(
                fechaNacimiento,
                LocalDate.now()
        ).getYears();

        System.out.println("Edad: " + edad);


        ApisHttp api = new ApisHttp();

        IdentidadAdicionalReqVo id = new IdentidadAdicionalReqVo();
        id.setOnboarding_id("6a45b91010bd93426c08a7d7");
        id.setSend_invite(true);
        id.setFlow_id("6a45bffc5c475372a5028a26");

        api.identidadCrearAdditional(id);
    }
}
