package com.heaerie.sparrow.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.common.USER002TT;
import com.heaerie.sparrow.service.AuthService;
import com.heaerie.sparrow.service.AuthToken;
import com.heaerie.sparrow.service.SparrowBadFormatException;
import com.heaerie.sqlite.Mapper;
import com.heaerie.sqlite.PrimaryKeyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class Controller<I, M extends Mapper<I>> {
    static final Logger logger = LogManager.getLogger();
    AuthToken authToken;
    @Context
    HttpServletRequest httpServletRequest;
    static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();


    void prepareToken() {
        if (httpServletRequest != null) {
            logger.info("AuthService.AUTH_TOKEN={}, userId={}, role={}", AuthService.AUTH_TOKEN, httpServletRequest.getHeader("userId"), httpServletRequest.getHeader("role"));

            authToken = new AuthToken();

            // authToken =  gson.fromJson(headers.getHeaderString(AuthService.AUTH_TOKEN), AuthToken.class);


            authToken.setUserId(httpServletRequest.getHeader("userId"));
            authToken.setRole(httpServletRequest.getHeader("role"));
        } else {
            authToken = new AuthToken();
        }

    }

    public abstract void validate() throws SparrowBadFormatException;

    public abstract I execute();

    public abstract M getMapper();

    public Response get() {
        try {
            prepareToken();
            validate();
        } catch (SparrowBadFormatException e) {
            return Response.status(e.getHttpCode()).type(MediaType.APPLICATION_JSON_TYPE).entity(e.getMessage()).build();
        }
        M mapper = getMapper();
        try {
            List<I> i = viewer(mapper.get(execute()));
            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).header(AuthService.AUTH_TOKEN, AuthService.getInstance().createToken(getUser(), getRole()))
                    .entity(gson.toJson(i)).build();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            logger.error("internal server error={}", e);
            return Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).entity("InternalServerError").build();
        }


    }

    protected abstract List<I> viewer(List<I> input);

    public Response getOne() {
        try {
            prepareToken();
            validate();
        } catch (SparrowBadFormatException e) {
            return Response.status(e.getHttpCode()).type(MediaType.APPLICATION_JSON_TYPE).entity(e.getMessage()).build();
        }
        M mapper = getMapper();
        try {
            List<I> i = mapper.get(execute());
            if (i.isEmpty()) {
                return Response.status(404).type(MediaType.APPLICATION_JSON_TYPE)
                        .entity( "{\"msg\": \"Not Found\"}").build();
            }
            if (this instanceof LoginController.V2Login)  {
                logger.debug("LoginController.V2Login from controller ");
                I a = i.get(0) ;
                if (a instanceof USER002TT) {
                    USER002TT user = (USER002TT) a;
                    logger.debug("user={}",user);
                    return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE)
                            .header(AuthService.AUTH_TOKEN,
                                    AuthService.getInstance().createToken(user.getUsrId(), user.getRole()))
                            .entity(gson.toJson(i.get(0))).build();
                }

            }
            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).header(AuthService.AUTH_TOKEN, AuthService.getInstance().createToken(getUser(), getRole()))
                    .entity(gson.toJson(i.get(0))).build();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).entity("InternalServerError").build();
        }


    }


    protected String getRole() {
        return authToken.getRole();
    }

    protected  String getUser() {
        return authToken.getUserId();
    }

    public Response post() {
        logger.error("in post");
        try {
            logger.error("in post.prepareToken");
            prepareToken();
            logger.error("in post.validate");
            validate();
        } catch (SparrowBadFormatException e) {
            return Response.status(e.getHttpCode()).type(MediaType.APPLICATION_JSON_TYPE).header(AuthService.AUTH_TOKEN, AuthService.getInstance().createToken(getUser(), getRole())).entity(e.getMessage()).build();
        }
        M mapper = getMapper();
        try {
            logger.error("in post.call execute");
            mapper.save(execute());
            logger.error("in post.call after execute 201");
            return Response.status(201).type(MediaType.APPLICATION_JSON_TYPE).entity("{}").build();
        } catch (PrimaryKeyException e) {
            return Response.status(400).type(MediaType.APPLICATION_JSON_TYPE).entity("{ \"msg\" : \"" + e.getMessage() +"\" }").build();

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            logger.error("in post.call after execute 500", e);
            return Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).entity("{ \"msg\" :   \"InternalServerError\"}").build();
        }
    }

    public Response put() {

        try {
            validate();
        } catch (SparrowBadFormatException e) {
            return Response.status(e.getHttpCode()).type(MediaType.APPLICATION_JSON_TYPE).entity(e.getMessage()).build();
        }
        M mapper = getMapper();
        try {
            mapper.save(execute());
            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).header(AuthService.AUTH_TOKEN, AuthService.getInstance().createToken(getUser(), getRole())).entity("{}").build();
        } catch (PrimaryKeyException e) {
            return Response.status(400).type(MediaType.APPLICATION_JSON_TYPE).entity(e.getMessage()).build();

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).entity("InternalServerError").build();
        }
    }

    public Response delete() {

        try {
            validate();
        } catch (SparrowBadFormatException e) {
            return Response.status(e.getHttpCode()).type(MediaType.APPLICATION_JSON_TYPE).entity(e.getMessage()).build();
        }
        M mapper = getMapper();
        try {
            mapper.delete(execute());
            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).header(AuthService.AUTH_TOKEN, AuthService.getInstance().createToken(getUser(), getRole())).entity("{}").build();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).entity("InternalServerError").build();
        }
    }
}
