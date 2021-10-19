package com.heaerie.sparrow.service;


import com.heaerie.common.HARAKAMAIL;
import com.heaerie.common.MailReq;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.UUID;

public class MailProcess {
    static final Logger logger = LogManager.getLogger();
    public  MailReq process(HARAKAMAIL mail) {

        MailReq resp = new MailReq();
        logger.error("process.mail={}", mail);
        // logic
        resp.setMkrId(1);
        resp.setDtCreated(new Date());
        resp.setAthId(1);
        resp.setDtModified(new Date());
        resp.setUuid(UUID.randomUUID().toString());
        if (mail.getHeader() != null) {
            logger.error("process.mail.getHeader().getHeaders()={}", mail.getHeader().getHeaders());
            if (mail.getHeader().getHeaders() != null) {
                if (mail.getHeader().getHeaders().getMessageId() != null) {
                    resp.setMsgId(mail.getHeader().getHeaders().getMessageId().get(0));
                }
                if (mail.getHeader().getHeaders().getSubject() != null) {
                    resp.setSubject(mail.getHeader().getHeaders().getSubject().get(0));
                }
                if (mail.getHeader().getHeaders().getThreadTopic() != null) {
                    resp.setLinkId(mail.getHeader().getHeaders().getThreadTopic().get(0));
                }
                if (mail.getHeader().getHeaders().getThreadIndex() != null) {
                    resp.setReplyId(mail.getHeader().getHeaders().getThreadIndex().get(0));
                }

                resp.setToList(mail.getHeader().getHeaders().getTo());
                resp.setFromList(mail.getHeader().getHeaders().getFrom());

                if  (mail.getHeader().getHeaders().getImportance() != null) {
                    resp.setIsFlag(mail.getHeader().getHeaders().getImportance().get(0));
                }

                if (mail.getHeader().getHeaders().getDate() != null) {
                    //Thu, 21 May 2020 16:25:11 +0000 (UTC)
                    // resp.setSentDate(mail.getHeader().getHeaders().getDate().get(0));
                }


                //savedfromemail


            }


        }
        resp.setShortBody(getShortBody(mail));
        resp.setBody(getBody(mail));
        return  resp;
    }

    private String getBody(HARAKAMAIL mail) {
        String body = null;
/*
        for ( int i=0 ; i < mail.getChildern().size() ; i ++ ) {
            HARAKAMAIL child =mail.getChildern().get(i);
        }
  */
        for(HARAKAMAIL child:  mail.getChildren()) {
            if  (child.isHtml()) {
                body =child.getBodytext();
                break;
            }

        }

        return body;
    }

    private String getShortBody(HARAKAMAIL mail) {
        String shortBody = null;
/*
        for ( int i=0 ; i < mail.getChildern().size() ; i ++ ) {
            HARAKAMAIL child =mail.getChildern().get(i);
        }
  */
        if (mail.getChildren() != null) {
            for (HARAKAMAIL child : mail.getChildren()) {
                if (!child.isHtml()) {
                    shortBody = child.getBodytext();
                    break;
                }

            }
        }

        return shortBody;
    }
}
