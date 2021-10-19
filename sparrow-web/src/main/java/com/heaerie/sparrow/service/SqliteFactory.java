package com.heaerie.sparrow.service;

import com.heaerie.sparrow.storage.LIST001TTMapper;
import com.heaerie.sparrow.storage.MAIL001TTMapper;
import com.heaerie.sparrow.storage.ROLE005TTMapper;
import com.heaerie.sparrow.storage.USER002TTMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteFactory {

    private static Connection conn;
    private MAIL001TTMapper mail001TTMapper;


    static SqliteFactory singleton;
    static final Logger logger = LogManager.getLogger();

    public static void initilize(String url) throws SQLException {

        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/helloworld");
            conn = ds.getConnection();
        } catch (NamingException e) {
            logger.error("NamingException ={}" , e);
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:" + url);
            } catch (SQLException e1 ) {
                e1.printStackTrace();
                e.printStackTrace();
                throw new SQLException("NamingException");
            }
        }

       // conn =DriverManager.getConnection("jdbc:sqlite:" + url);
        logger.info("url=jdbc:sqlite:{}", url);
        if (conn != null) {
            DatabaseMetaData meta = conn.getMetaData();
            logger.info("The driver name is " + meta.getDriverName());
            logger.info("A new database has been created.");
        }
        MAIL001TTMapper.initilize(conn);
        USER002TTMapper.initilize(conn);
        LIST001TTMapper.initilize(conn);
        ROLE005TTMapper.initilize(conn);

    }

    SqliteFactory(String url) throws SQLException {
        initilize(url);
        mail001TTMapper = new MAIL001TTMapper();
    }

    public static SqliteFactory getInstance() throws SQLException {
        String url = "test.db";
        if (singleton == null ) {
            singleton = new SqliteFactory(url);
        }
        return  singleton;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public static MAIL001TTMapper getMAIL001TTMapper() {
        return new MAIL001TTMapper();
    }
    public static USER002TTMapper getUSER002TTMapper() {
        return new USER002TTMapper();
    }
    public static ROLE005TTMapper getROLE005TTMapper() { return new ROLE005TTMapper(); }

}
