package com.heaerie.sparrow.storage;

import com.heaerie.common.MAIL001TT;
import com.heaerie.sparrow.service.SqliteFactory;
import com.heaerie.sqlite.Mapper;
import com.heaerie.sqlite.PrimaryKeyException;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class MAIL001TTMapper extends Mapper<MAIL001TT> {

    public static void main(String... argv) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SqliteFactory.initilize("/tmp/test.db");
        Connection conn = SqliteFactory.getInstance().getConn();
        MAIL001TTMapper.initilize(conn);
        MAIL001TTMapper mail001TTMapper = new MAIL001TTMapper();
        MAIL001TT mail001tt = new MAIL001TT();

        try {
            mail001TTMapper.save(mail001tt);


           /* Date newT = new Date();
            newT.setTime(mail001tt.getTime());
            System.out.println("old" + old + "|newT=" + newT);*/
            System.out.println( mail001TTMapper.get(mail001tt));
        } catch (PrimaryKeyException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
