package com.heaerie.sparrow.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.common.HARA004TT;
import com.heaerie.common.MAIL001TT;
import com.heaerie.common.MailReq;
import com.heaerie.sparrow.service.MailProcess;
import com.heaerie.sparrow.service.SqliteFactory;
import com.heaerie.sparrow.storage.MAIL001TTMapper;
import com.heaerie.sqlite.Mapper;
import com.heaerie.sqlite.PrimaryKeyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Path("/mail/")
public class MailController {

    static final Logger logger = LogManager.getLogger();
    private static final String PENDING = "PENDING";
    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("id") String id,
                        @QueryParam("send_time") String sendTime,
                        @QueryParam("request") String request,
                        @QueryParam("status") String status,
                        @Context HttpHeaders httpHeaders) {

        // "id": "003",
        //"send_time": "Apr 30, 2020 8:26:50 PM",
        //"request": "request",
        //"status": "PENDING"
        MAIL001TTMapper mail001TTMapper = SqliteFactory.getMAIL001TTMapper();
        List<MAIL001TT> a = new ArrayList<>();
        MAIL001TT s  = new MAIL001TT();
        s.setId(id);
        s.setStatus(status);
        try {
            s.setSendTime(Mapper.fromIsoDate(sendTime));
        } catch (ParseException e) {
            Response.status(400).type(MediaType.APPLICATION_JSON_TYPE).entity("invalid date formate for send_time").build();
        }
        try {
            if (s == null) {
                s = new MAIL001TT();
            }
            a =  mail001TTMapper.get(s);
        } catch (NoSuchMethodException e) {
            logger.error("NoSuchMethodException", e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException", e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException", e);
            e.printStackTrace();
        }

        return  Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson(a)).build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String requestBody, @Context HttpHeaders httpHeaders) {
        MAIL001TTMapper mail001TTMapper = SqliteFactory.getMAIL001TTMapper();

	System.out.println("requestBody" + requestBody);
	logger.error("requestBody={}",requestBody);

        HARA004TT input  = gson.fromJson(requestBody, HARA004TT.class);
        MailProcess m = new MailProcess();


        logger.error("input={}", input);
        try {
            MAIL001TT s = new MAIL001TT();
            s.setSendTime(input.getSendTime());
            s.setId(input.getId());
            MailReq mailReq= m.process(input.getRequest());
            s.setRequest(mailReq);
            s.setStatus(input.getStatus());
            s.setUserId(mailReq.getUserId());
            mail001TTMapper.save(s);


        } catch (NoSuchMethodException e) {
            logger.error("NoSuchMethodException", e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException", e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException", e);
            e.printStackTrace();
        } catch (PrimaryKeyException e) {
            logger.error("PrimaryKeyException", e);
            e.printStackTrace();
        }
        return  Response.status(201).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson("{}")).build();


    }


    @PUT
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(String requestBody, @Context HttpHeaders httpHeaders) {
	System.out.println("requestBody" + requestBody);
        MAIL001TTMapper mail001TTMapper = SqliteFactory.getMAIL001TTMapper();
	
	logger.error("requestBody={}", requestBody);	

        MAIL001TT s  = gson.fromJson(requestBody, MAIL001TT.class);
        try {

            mail001TTMapper.save(s);


        } catch (NoSuchMethodException e) {
            logger.error("NoSuchMethodException", e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException", e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException", e);
            e.printStackTrace();
        } catch (PrimaryKeyException e) {
            logger.error("PrimaryKeyException", e);
            e.printStackTrace();
        }
        return  Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson("{}")).build();


    }
    @DELETE
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(String requestBody, @Context HttpHeaders httpHeaders) {
        MAIL001TTMapper mail001TTMapper = SqliteFactory.getMAIL001TTMapper();
        MAIL001TT s  = gson.fromJson(requestBody, MAIL001TT.class);
        try {
            mail001TTMapper.delete(s);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson("{}")).build();

    }


}
