package com.heaerie.sparrow.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.common.HARA004TT;
import com.heaerie.common.MAIL001TT;
import com.heaerie.sparrow.service.MailProcess;
import com.heaerie.sparrow.service.SparrowBadFormatException;
import com.heaerie.sparrow.service.SqliteFactory;
import com.heaerie.sparrow.storage.MAIL001TTMapper;
import com.heaerie.sqlite.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.List;

@Path("/v2/mail/")
public class V2MailController {

    static final Logger logger = LogManager.getLogger();
    private static final String PENDING = "PENDING";
    static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get1(@QueryParam("id") String id,
                        @QueryParam("send_time") String sendTime,
                        @QueryParam("request") String request,
                        @QueryParam("status") String status,
                        @Context HttpHeaders httpHeaders) {
        V2GetEmail g = new V2GetEmail(id,sendTime,request,status);
        MAIL001TT i= new MAIL001TT();
        return g.get();
    }

    public static class V2GetEmail extends Controller<MAIL001TT,MAIL001TTMapper>{
        private String id;
        private String sendTime;
        private String request;
        private String status;


        public V2GetEmail(String id, String sendTime, String request, String status)  {
            this.id = id;
            this.sendTime = sendTime;
            this.request = request;
            this.status = status;
            logger.info("V2GetEmail id={}, sendTime={}, request={}, status={}" , this.id, this.sendTime, this.request, this.status);
        }

        @Override
        public void validate() throws SparrowBadFormatException {
            try {
                Mapper.fromIsoDate(sendTime);
            } catch (ParseException e) {
               throw new SparrowBadFormatException("invalid date format for send_time");
            }
        }

        @Override
        public MAIL001TT execute() {
            MAIL001TT s  = new MAIL001TT();
            s.setId(id);
            s.setStatus(status);
            return s;
        }

        @Override
        public MAIL001TTMapper getMapper() {
            return SqliteFactory.getMAIL001TTMapper();
        }

        @Override
        protected List<MAIL001TT> viewer(List<MAIL001TT> input) {
            return input;
        }


    }


    public static class V2CreateEmail extends Controller<MAIL001TT,MAIL001TTMapper>{

        String requestBody;

        public V2CreateEmail(String requestBody) {
            logger.error("V2CreateEmail().requestBody={}", requestBody);
            this.requestBody = requestBody;
        }


        @Override
        public void validate() throws SparrowBadFormatException {
            logger.error("V2CreateEmail.validate");
        }

        @Override
        public MAIL001TT execute() {
            logger.error("V2CreateEmail.execute");
            HARA004TT input  = gson.fromJson(requestBody, HARA004TT.class);
            logger.error("V2CreateEmail.execute input={}",input);
            MailProcess m = new MailProcess();
            MAIL001TT s = new MAIL001TT();
            s.setSendTime(input.getSendTime());
            s.setId(input.getId());
            s.setRequest( m.process(input.getRequest()));
            s.setStatus(input.getStatus());
            return s;
        }

        @Override
        public MAIL001TTMapper getMapper() {
            return SqliteFactory.getMAIL001TTMapper();
        }

        @Override
        protected List<MAIL001TT> viewer(List<MAIL001TT> input) {
            return input;
        }
    }



    public static class V2PutEmail extends Controller<MAIL001TT,MAIL001TTMapper>{

        String requestBody;

        public V2PutEmail(String requestBody) {
            this.requestBody = requestBody;
        }


        @Override
        public void validate() throws SparrowBadFormatException {

        }

        @Override
        public MAIL001TT execute() {
            MAIL001TT s  = gson.fromJson(requestBody, MAIL001TT.class);
            return s;
        }

        @Override
        public MAIL001TTMapper getMapper() {
            return SqliteFactory.getMAIL001TTMapper();
        }

        @Override
        protected List<MAIL001TT> viewer(List<MAIL001TT> input) {
            return input;
        }
    }
    public static class V2DeleteEmail extends Controller<MAIL001TT,MAIL001TTMapper>{

        String requestBody;

        public V2DeleteEmail(String requestBody) {
            this.requestBody = requestBody;
        }


        @Override
        public void validate() throws SparrowBadFormatException {

        }

        @Override
        public MAIL001TT execute() {
            MAIL001TT s  = gson.fromJson(requestBody, MAIL001TT.class);
            return s;
        }

        @Override
        public MAIL001TTMapper getMapper() {
            return SqliteFactory.getMAIL001TTMapper();
        }

        @Override
        protected List<MAIL001TT> viewer(List<MAIL001TT> input) {
            return input;
        }
    }


    @POST
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String requestBody, @Context HttpHeaders httpHeaders) {
        MAIL001TTMapper mail001TTMapper = SqliteFactory.getMAIL001TTMapper();
        logger.error("requestBody={}", requestBody);
        System.out.println("requestBody" + requestBody);
        V2CreateEmail v2CreateEmail = new V2CreateEmail(requestBody);
        return  v2CreateEmail.post();
    }


    @PUT
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(String requestBody, @Context HttpHeaders httpHeaders) {
        V2PutEmail v2PutEmail = new V2PutEmail(requestBody);
        return v2PutEmail.put();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(String requestBody, @Context HttpHeaders httpHeaders) {
       V2DeleteEmail v2DeleteEmail = new V2DeleteEmail(requestBody);
       return  v2DeleteEmail.delete();

    }


}
