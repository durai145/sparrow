package com.heaerie.sparrow.controller;

import com.google.common.base.Strings;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.common.USER002TT;
import com.heaerie.sparrow.service.SparrowBadFormatException;
import com.heaerie.sparrow.service.SqliteFactory;
import com.heaerie.sparrow.storage.MAIL001TTMapper;
import com.heaerie.sparrow.storage.USER002TTMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/user/v2")
public class LoginController {
    static final Logger logger = LogManager.getLogger();
    private static final String PENDING = "PENDING";
    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .create();


    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String requestBody, @Context HttpHeaders httpHeaders) {
        MAIL001TTMapper mail001TTMapper = SqliteFactory.getMAIL001TTMapper();
        logger.error("requestBody={}", requestBody);
        System.out.println("requestBody" + requestBody);
        V2Login v2Login = new V2Login(requestBody);
        return  v2Login.getOne();
    }

    @POST
    @Path("/valid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isValid(String requestBody, @Context HttpHeaders httpHeaders) {
        MAIL001TTMapper mail001TTMapper = SqliteFactory.getMAIL001TTMapper();
        logger.error("requestBody={}", requestBody);
        System.out.println("requestBody" + requestBody);
        V2IsValidUser v2Login = new V2IsValidUser(requestBody);
        return  v2Login.getOne();
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(String requestBody, @Context HttpHeaders httpHeaders) {
        MAIL001TTMapper mail001TTMapper = SqliteFactory.getMAIL001TTMapper();
        logger.error("requestBody={}", requestBody);
        System.out.println("requestBody" + requestBody);
        Controller v2Login = new V2Register(requestBody);
        return  v2Login.post();
    }

    public static class V2Login extends Controller<USER002TT, USER002TTMapper>{
        String requestBody;
        USER002TT user;
        public V2Login(String requestBody) {
            super();
            this.requestBody= requestBody;

        }

        @Override
        public void validate() throws SparrowBadFormatException {
            user = execute();
            if (Strings.isNullOrEmpty(user.getUsrId())) {
                throw new SparrowBadFormatException("usr id is mandatory");
            }

            if (Strings.isNullOrEmpty(user.getPassword())) {
                throw new SparrowBadFormatException("password is mandatory");
            }
        }

        @Override
        public USER002TT execute() {

            USER002TT user =gson.fromJson(requestBody, USER002TT.class);
            return user;
        }

        @Override
        public USER002TTMapper getMapper() {
            return SqliteFactory.getUSER002TTMapper();
        }

        @Override
        protected List<USER002TT> viewer(List<USER002TT> input) {
            List<USER002TT> output = new ArrayList<>();
            for(USER002TT user:  input) {
                user.setPassword("");
                user.setVersion("1");
                output.add(user);
            }
            return output;
        }

        @Override
        protected String getRole() {
            return "role";
        }

        @Override
        protected String getUser() {
            return "user";
        }

    }

    private class V2Register extends Controller<USER002TT, USER002TTMapper> {
        String requestBody;
        USER002TT user;
        public V2Register(String requestBody) {
            super();
            this.requestBody= requestBody;

        }

        @Override
        public void validate() throws SparrowBadFormatException {
            user = execute();
            if (Strings.isNullOrEmpty(user.getUsrId())) {
                throw new SparrowBadFormatException("username is mandatory");
            }

            if (Strings.isNullOrEmpty(user.getDomain())) {
                throw new SparrowBadFormatException("domain is mandatory");
            }

            if (Strings.isNullOrEmpty(user.getPassword())) {
                throw new SparrowBadFormatException("password is mandatory");
            }
        }

        @Override
        public USER002TT execute() {


            USER002TT user = gson.fromJson(requestBody, USER002TT.class);
            user.setId(UUID.randomUUID().toString());
            return user;
        }

        @Override
        public USER002TTMapper getMapper() {
            return SqliteFactory.getUSER002TTMapper();
        }

        @Override
        protected List<USER002TT> viewer(List<USER002TT> input) {
            List<USER002TT> output = new ArrayList<>();
            for(USER002TT user:  input) {
                user.setPassword("");
                user.setVersion("1");
                output.add(user);
           }
            return output;
        }
    }

    private class V2IsValidUser extends Controller<USER002TT, USER002TTMapper>{

        String requestBody;
        USER002TT user;
        public V2IsValidUser(String requestBody) {
            super();
            this.requestBody= requestBody;

        }

        @Override
        public void validate() throws SparrowBadFormatException {
            user = execute();
            if (Strings.isNullOrEmpty(user.getUsrId())) {
                throw new SparrowBadFormatException("usr id is mandatory");
            }
        }

        @Override
        public USER002TT execute() {
            return gson.fromJson(requestBody, USER002TT.class);
        }

        @Override
        public USER002TTMapper getMapper() {
            return SqliteFactory.getUSER002TTMapper();
        }

        @Override
        protected List<USER002TT> viewer(List<USER002TT> input) {
            List<USER002TT> output = new ArrayList<>();
            for(USER002TT user:  input) {
                user.setPassword("");
                user.setVersion("1");
                output.add(user);
            }
            return output;
        }

        @Override
        protected String getRole() {
            return "role";
        }

        @Override
        protected String getUser() {
            return "user";
        }

    }
}
