package com.heaerie.sparrow.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/appstatus")
public class AppController {

    static final Logger logger = LogManager.getLogger();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus() {
        logger.info("app status is called");
        return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity("{\"name\": \"running\"}").build();
    }
}
