package com.fenoreste.voculares.api_legalario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fenoreste.voculares.entity.Tabla;
import com.fenoreste.voculares.entity.TablaPK;
import com.fenoreste.voculares.model.*;
import com.fenoreste.voculares.service.ITablaService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ApisHttp {

    /*Produccion*/
    private String basePathProduccion = "https://api.legalario.com/";


    //Sandbox Testing
    private String basePathSandbox = "https://sandbox-api.legalario.com/";
    private String pathLogin = "auth/login";
    private String pathToken = "auth/token";
    private String pathIdentidad = "v2/identities";
    private String pathIdentidadAdicional = "v2/identities/additional";

    OkHttpClient client = null;
    MediaType mediaType = null;
    RequestBody body = null;
    Request request = null;
    Response response = null;

    @Autowired
    private static RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private SSLUtil sslUtil;

    @Autowired
    private ITablaService tablaService;

    private TablaPK tablaPK;
    private Tabla tabla;

    private ObjectMapper mapper = new ObjectMapper();


    public LoginVoResponse login() {
        String resultado = "";
        //log.info("::::::::::::::::Accediendo al login Onboarding::::::::::::::::::::");
        LoginVoResponse loginVoResponse = new LoginVoResponse();
        try {

            tablaPK = new TablaPK("voculares", "login_credentials");
            tabla = tablaService.buscarPorId(tablaPK);

            //log.info(":::::::::::::::::::::::Datos login:::::::::::::"+tabla.getDato1()+","+tabla.getDato2());
            sslUtil.disableSSLCertificateChecking();

            client = new OkHttpClient.Builder()
                    .connectTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // Tiempo de espera para establecer la conexión
                    .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)    // Tiempo de espera para la lectura de datos
                    .build();


            mediaType = MediaType.parse("application/x-www-form-urlencoded");

            body = RequestBody.create(mediaType, "email="+tabla.getDato2()+"&password="+tabla.getDato1());

           // body = RequestBody.create(mediaType, "email=ctello@csn.coop&password=cjAs6L09Jqlh");//+tabla.getDato1());
            //log.info(":::::::::::::::::::Body:"+body);

            tablaPK = new TablaPK("voculares", "ambiente");
            tabla = tablaService.buscarPorId(tablaPK);

            String ambiente = "";
            if(tabla.getDato1().equals("demo")){
                ambiente = basePathSandbox;
            } else if(tabla.getDato1().equals("prod")){
                ambiente = basePathProduccion;
            }

            String url = ambiente + pathLogin;

            log.warn(":::::::::Url a consumir:"+url);
            request = new Request.Builder()
                    .url(url)
                    .method("POST", body).addHeader("Content-Type", "application/x-www-form-urlencoded").build();
            response = client.newCall(request).execute();
            resultado = response.body().string();

            log.info(":::::::::::::::::Resultado login:::::::::::::::::::::"+resultado);

            JsonObject jsonObject = JsonParser.parseString(resultado).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");

            loginVoResponse.setClientId(data.get("client_id").getAsString());
            loginVoResponse.setClientSecret(data.get("client_secret").getAsString());
        } catch (Exception e) {
            log.error("::::Error al realizar login:" + e.getMessage() + "::::::::::::::");
        }
        return loginVoResponse;
    }


    public String token() {
        String resultado = "";

        try {

            sslUtil.disableSSLCertificateChecking();
            client = new OkHttpClient.Builder()
                    .connectTimeout(20, java.util.concurrent.TimeUnit.SECONDS) // Tiempo de espera para establecer la conexión
                    .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)    // Tiempo de espera para la lectura de datos
                    .build();

            //Se consume directo metodo para loguear
            LoginVoResponse loginVoResponse = login();

            mediaType = MediaType.parse("application/x-www-form-urlencoded");
            body = RequestBody.create(mediaType, "client_id=" + loginVoResponse.getClientId() +
                    "&client_secret=" + loginVoResponse.getClientSecret() + "&grant_type=client_credentials&scope=csn");//Se cambia scope el 06/11/2025

            tablaPK = new TablaPK("voculares", "ambiente");
            tabla = tablaService.buscarPorId(tablaPK);

            String ambiente = "";
            if(tabla.getDato1().equals("demo")){
                ambiente = basePathSandbox;
            } else if(tabla.getDato1().equals("prod")){
                ambiente = basePathProduccion;
            }


            String url = ambiente + pathToken;
            log.warn(":::::::::Url a consumir:"+url);
            request = new Request.Builder()
                    .url(url)
                    .method("POST", body).addHeader("Content-Type", "application/x-www-form-urlencoded").build();
            response = client.newCall(request).execute();
            resultado = response.body().string();
            //log.info("Resultado token: " + resultado);

            JsonObject jsonObject = JsonParser.parseString(resultado).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");


            resultado = data.get("access_token").getAsString();
        } catch (Exception e) {
            log.error("::::Error al obtener token http:" + e.getMessage() + "::::::::::::::");
        }
        return resultado;
    }

    public IdentidadVoResponse identidadCrear(SignersVoReq signersVoReq) {
        String resultado = "";
        IdentidadVoResponse identidadVoResponse = new IdentidadVoResponse();
        try {

            String token = token();
            sslUtil.disableSSLCertificateChecking();
            client = new OkHttpClient.Builder()
                    .connectTimeout(220, java.util.concurrent.TimeUnit.SECONDS) // Tiempo de espera para establecer la conexión
                    .readTimeout(220, java.util.concurrent.TimeUnit.SECONDS)    // Tiempo de espera para la lectura de datos
                    .build();
            String json = mapper.writeValueAsString(signersVoReq);
            log.info(":::::::::::Json identidades crear:"+json);
            mediaType = MediaType.parse("application/application/json");
            body = RequestBody.create(mediaType, json);

            tablaPK = new TablaPK("voculares", "ambiente");
            tabla = tablaService.buscarPorId(tablaPK);

            String ambiente = "";
            if(tabla.getDato1().equals("demo")){
                ambiente = basePathSandbox;
            } else if(tabla.getDato1().equals("prod")){
                ambiente = basePathProduccion;
            }

            String url = ambiente + pathIdentidad;
            log.warn(":::::::::Url a consumir:"+url);
            request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();

            response = client.newCall(request).execute();
            resultado = response.body().string();
            identidadVoResponse = mapper.readValue(resultado, IdentidadVoResponse.class);

            if(!identidadVoResponse.isSuccess()) {
                identidadVoResponse.setMessage(identidadVoResponse.getData().get(0).asText());
            }

            log.info(":::::::::::Resultado identidad crear:::::::::::::::::::" + resultado + "," + identidadVoResponse);
        } catch (Exception e) {
            log.error("::::Error al crear identidad:" + e.getMessage() + "::::::::::::::");
        }
        return identidadVoResponse;
    }


    public IdentidadAdicionalResVo identidadCrearAdditional(IdentidadAdicionalReqVo identidadAdicionalReqVo) {
        String resultado = "";
        IdentidadAdicionalResVo identidadAdicionalResVo = new IdentidadAdicionalResVo();
        try {

            String token = token();
            sslUtil.disableSSLCertificateChecking();
            client = new OkHttpClient.Builder()
                    .connectTimeout(220, java.util.concurrent.TimeUnit.SECONDS) // Tiempo de espera para establecer la conexión
                    .readTimeout(220, java.util.concurrent.TimeUnit.SECONDS)    // Tiempo de espera para la lectura de datos
                    .build();
            String json = mapper.writeValueAsString(identidadAdicionalReqVo);
            log.info(":::::::::::Json identidad adicional:"+json);
            mediaType = MediaType.parse("application/application/json");
            body = RequestBody.create(mediaType, json);

            tablaPK = new TablaPK("voculares", "ambiente");
            tabla = tablaService.buscarPorId(tablaPK);

            String ambiente = "";
            if(tabla.getDato1().equals("demo")){
                ambiente = basePathSandbox;
            } else if(tabla.getDato1().equals("prod")){
                ambiente = basePathProduccion;
            }

            String url = ambiente + pathIdentidadAdicional;
            log.warn(":::::::::Url a consumir:"+url);
            request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();

            response = client.newCall(request).execute();
            resultado = response.body().string();
            identidadAdicionalResVo = mapper.readValue(resultado, IdentidadAdicionalResVo.class);


            log.info(":::::::::::Resultado identidad crear adicional:::::::::::::::::::" + resultado);
        } catch (Exception e) {
            log.error("::::Error al crear identidad adicional:" + e.getMessage() + "::::::::::::::");
        }
        return identidadAdicionalResVo;
    }

}

