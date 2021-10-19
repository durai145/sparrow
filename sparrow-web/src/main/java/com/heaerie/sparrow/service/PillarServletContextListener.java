package com.heaerie.sparrow.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.common.PillarPropService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.sql.SQLException;

public class PillarServletContextListener implements ServletContextListener {

    static final Logger logger = LogManager.getLogger();
    static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();


    @Override
    public void contextInitialized(ServletContextEvent sce) {


        try {
            Class.forName("org.sqlite.JDBC");
            PillarPropService.init();
        } catch (ClassNotFoundException | IOException e) {
            logger.info("ClassNotFoundException: ", e);
            e.printStackTrace();
            throw new InternalServerErrorException(e.getMessage());
        }

        logger.info("contextInitialized");
        try {
            logger.info("calling SqliteFactory with url");
            SqliteFactory.initilize("/tmp/test.db");
        } catch (SQLException exception) {
            exception.printStackTrace();
            logger.info("Exception={}",exception);
            throw new InternalServerErrorException(exception.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("contextDestroyed");
    }
}
