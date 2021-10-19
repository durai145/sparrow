package com.heaerie.sparrow.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.common.Constants;
import com.heaerie.common.USER002TT;
import com.heaerie.sparrow.service.SqliteFactory;
import com.heaerie.sparrow.storage.USER002TTMapper;
import com.heaerie.sqlite.PrimaryKeyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Path("/user/")
public class UserController {

    static final Logger logger = LogManager.getLogger();
    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("usrId") String usrId,
                        @QueryParam("domain") String  domain,
                        @QueryParam("portal") String portal,
                        @Context HttpHeaders httpHeaders) {

        // "id": "003",
        //"send_time": "Apr 30, 2020 8:26:50 PM",
        //"request": "request",
        //"status": "PENDING"
        USER002TTMapper user002TTMapper = SqliteFactory.getUSER002TTMapper();
        List<USER002TT> a = new ArrayList<>();
        USER002TT s  = new USER002TT();
        s.setUsrId(usrId);
        s.setDomain(domain);
        s.setPortal(portal);
        s.setVersion(Constants.VERSION_001);


        try {

            a =  user002TTMapper.get(s);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return  Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson(a)).build();
    }

    @PUT
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String requestBody, @Context HttpHeaders httpHeaders) {
        USER002TTMapper user002TTMapper = SqliteFactory.getUSER002TTMapper();

        USER002TT s  = gson.fromJson(requestBody, USER002TT.class);

        try {
            user002TTMapper.save(s);


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        	return  Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson("{ \"message\":" + e.getMessage() +" }")).build();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        	return  Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson("{ \"message\":" + e.getMessage() +" }")).build();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        	return  Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson("{ \"message\":" + e.getMessage() +" }")).build();
        } catch (PrimaryKeyException e) {
            e.printStackTrace();
        	return  Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson("{ \"message\":" + e.getMessage() +" }")).build();
        }
        return  Response.status(201).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson("{}")).build();


    }



    @DELETE
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(String requestBody, @Context HttpHeaders httpHeaders) {
        USER002TTMapper user002TTMapper = SqliteFactory.getUSER002TTMapper();
        USER002TT s  = gson.fromJson(requestBody, USER002TT.class);
        try {
            user002TTMapper.delete(s);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  Response.status(201).type(MediaType.APPLICATION_JSON_TYPE).entity(gson.toJson("{}")).build();

    }


}
