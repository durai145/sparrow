package com.heaerie.sparrow.controller;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.common.HARA004TT;
import com.heaerie.common.HARAKAMAIL;
import com.heaerie.common.PillarPropService;
import com.heaerie.sparrow.service.SqliteFactory;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class V2MailControllerTest extends TestCase {
    static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();
    @Before
    public void init() throws IOException, SQLException {
        PillarPropService.init();
        SqliteFactory.initilize("/tmp/test.db");
    }

    @Test
    public void test001() throws IOException, SQLException {
        PillarPropService.init();
        SqliteFactory.initilize("/tmp/test.db");
        V2MailController c = new V2MailController();
        String harakaMail="{\n" +
                "  \"header\": {\n" +
                "    \"headers\": {\n" +
                "      \"content-transfer-encoding\": [\n" +
                "        \"quoted-printable\",\n" +
                "        \"quoted-printable\"\n" +
                "      ],\n" +
                "      \"received\": [\n" +
                "        \"from SG2APC01FT056.eop-APC01.prod.protection.outlook.com\\r\\n (2a01:111:e400:7ebd::4a) by\\r\\n SG2APC01HT060.eop-APC01.prod.protection.outlook.com (2a01:111:e400:7ebd::251)\\r\\n with Microsoft SMTP Server (version\\u003dTLS1_2,\\r\\n cipher\\u003dTLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384) id 15.20.3261.16; Sat, 8 Aug\\r\\n 2020 22:23:58 +0000\",\n" +
                "        \"from PS2PR06MB2566.apcprd06.prod.outlook.com\\r\\n (2a01:111:e400:7ebd::45) by SG2APC01FT056.mail.protection.outlook.com\\r\\n (2a01:111:e400:7ebd::269) with Microsoft SMTP Server (version\\u003dTLS1_2,\\r\\n cipher\\u003dTLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384) id 15.20.3261.16 via Frontend\\r\\n Transport; Sat, 8 Aug 2020 22:23:58 +0000\",\n" +
                "        \"from PS2PR06MB2566.apcprd06.prod.outlook.com\\r\\n ([fe80::c18b:b053:2720:ae11]) by PS2PR06MB2566.apcprd06.prod.outlook.com\\r\\n ([fe80::c18b:b053:2720:ae11%7]) with mapi id 15.20.3261.022; Sat, 8 Aug 2020\\r\\n 22:23:58 +0000\"\n" +
                "      ],\n" +
                "      \"mime-version\": [\n" +
                "        \"1.0\"\n" +
                "      ],\n" +
                "      \"arc-seal\": [\n" +
                "        \"i\\u003d1; a\\u003drsa-sha256; s\\u003darcselector9901; d\\u003dmicrosoft.com; cv\\u003dnone;\\r\\n b\\u003dGo0mAbhuPOs5+Ocdso7WiUn8V9upvBLr59YxVYAoWBoXq2TV+9NQCIxD0LJ81nasrOyqMbtFycfVig6nexMtfkTDAOAE0ptDKjVcNP2ZvR2HwxiNjfuCBkUwjR3vg5/iorSb616y0+VZHl+DGologAzVXU4NjV6mR9UHQWiY43UlqvH7k8cD7RWLT1pQGKgjifrQ/O2X2+tmTbAr2okLnBAnN6EYDG8KIH5wiJ/0HVaqn29NXonPkrB4LkIwXHUeaQJNneWpjSE5QfS2sIM8tznCiPpJCw4iAKuIdqTXt1VezvwspVii9dU+uSJD6Rc8RViFtcR74nFaVkEkYSWpAg\\u003d\\u003d\"\n" +
                "      ],\n" +
                "      \"arc-message-signature\": [\n" +
                "        \"i\\u003d1; a\\u003drsa-sha256; c\\u003drelaxed/relaxed; d\\u003dmicrosoft.com;\\r\\n s\\u003darcselector9901;\\r\\n h\\u003dFrom:Date:Subject:Message-ID:Content-Type:MIME-Version:X-MS-Exchange-SenderADCheck;\\r\\n bh\\u003dJbfIY6q+a9BVC0oWO4eBrAB8zDuWekYDWmsjD3RvJAk\\u003d;\\r\\n b\\u003djuHSlZNZX9aKU+Vs3XJenMNF2Z4EnvREHAkRpqavxcvhOv4RfoufWv2AJad7RFwRcdGlb0pI84HIieIxlSypREn4eh34fMwDAi4mApQmebnrt0bMMdPZ66YauvNnVLccTEEhdbK6wahnu/bXOTNQk7TFIPr+w0H9Ha9GtqI7IIlsTcsPd9ApjeGC+FSGIu8u03KBomtsC4jOdVWn/0CQe7C/aeDT4WWR2Nw4ppPEdPgqzTZBSdnchdQbXz1Z9lJS3eFwrxpvbEASf/zDHmR8H2rShTHknj6VGKMKfXkHOgd4nud6/lTrvy+WyWL2u6EB4eIbc/qRnMUHCbfldRexUA\\u003d\\u003d\"\n" +
                "      ],\n" +
                "      \"arc-authentication-results\": [\n" +
                "        \"i\\u003d1; mx.microsoft.com 1; spf\\u003dnone; dmarc\\u003dnone;\\r\\n dkim\\u003dnone; arc\\u003dnone\"\n" +
                "      ],\n" +
                "      \"from\": [\n" +
                "        \"duraimurugan Govindaraj \\u003cdurai145@live.in\\u003e\"\n" +
                "      ],\n" +
                "      \"to\": [\n" +
                "        \"\\\"durai@columbarian.com\\\" \\u003cdurai@columbarian.com\\u003e\"\n" +
                "      ],\n" +
                "      \"subject\": [\n" +
                "        \"STAPI001\"\n" +
                "      ],\n" +
                "      \"thread-topic\": [\n" +
                "        \"STAPI001\"\n" +
                "      ],\n" +
                "      \"thread-index\": [\n" +
                "        \"AQHWbdKbQg5R9n1niUO/ziG0AZ4t9Q\\u003d\\u003d\"\n" +
                "      ],\n" +
                "      \"date\": [\n" +
                "        \"Sat, 8 Aug 2020 22:23:57 +0000\"\n" +
                "      ],\n" +
                "      \"accept-language\": [\n" +
                "        \"en-US\"\n" +
                "      ],\n" +
                "      \"content-language\": [\n" +
                "        \"en-US\"\n" +
                "      ],\n" +
                "      \"x-ms-exchange-messagesentrepresentingtype\": [\n" +
                "        \"1\"\n" +
                "      ],\n" +
                "      \"x-tmn\": [\n" +
                "        \"[Uz7zk07ofBU+dB/qYOjIL0BuIZLpy/L4]\"\n" +
                "      ],\n" +
                "      \"x-ms-publictraffictype\": [\n" +
                "        \"Email\"\n" +
                "      ],\n" +
                "      \"x-incomingheadercount\": [\n" +
                "        \"42\"\n" +
                "      ],\n" +
                "      \"x-eopattributedmessage\": [\n" +
                "        \"0\"\n" +
                "      ],\n" +
                "      \"x-ms-office365-filtering-correlation-id\": [\n" +
                "        \"ae6ccaa5-b074-42ab-0fec-08d83be9be72\"\n" +
                "      ],\n" +
                "      \"x-ms-traffictypediagnostic\": [\n" +
                "        \"SG2APC01HT060:\"\n" +
                "      ],\n" +
                "      \"x-microsoft-antispam\": [\n" +
                "        \"BCL:0;\"\n" +
                "      ],\n" +
                "      \"x-ms-exchange-transport-forked\": [\n" +
                "        \"True\"\n" +
                "      ],\n" +
                "      \"x-originatororg\": [\n" +
                "        \"outlook.com\"\n" +
                "      ],\n" +
                "      \"x-ms-exchange-crosstenant-rms-persistedconsumerorg\": [\n" +
                "        \"00000000-0000-0000-0000-000000000000\"\n" +
                "      ],\n" +
                "      \"x-ms-exchange-crosstenant-network-message-id\": [\n" +
                "        \"ae6ccaa5-b074-42ab-0fec-08d83be9be72\"\n" +
                "      ],\n" +
                "      \"x-ms-exchange-crosstenant-originalarrivaltime\": [\n" +
                "        \"08 Aug 2020 22:23:58.0237\\r\\n (UTC)\"\n" +
                "      ],\n" +
                "      \"x-ms-exchange-crosstenant-fromentityheader\": [\n" +
                "        \"Internet\"\n" +
                "      ],\n" +
                "      \"x-ms-exchange-crosstenant-id\": [\n" +
                "        \"84df9e7f-e9f6-40af-b435-aaaaaaaaaaaa\"\n" +
                "      ],\n" +
                "      \"x-ms-exchange-transport-crosstenantheadersstamped\": [\n" +
                "        \"SG2APC01HT060\"\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"is_html\": false,\n" +
                "  \"children\": []\n" +
                "}\n";
        HttpHeaders headrs=null;
        HARA004TT hara004TT = new HARA004TT();
        hara004TT.setId(UUID.nameUUIDFromBytes(new Date().toString().getBytes()).toString());
        hara004TT.setRequest(gson.fromJson(harakaMail, HARAKAMAIL.class));
        hara004TT.setStatus("PENDING");
        String requestBody = gson.toJson(hara004TT);
        System.out.println(requestBody);
        c.create(requestBody, headrs);
    }

    @Test
    public void test002() throws IOException, SQLException {
        PillarPropService.init();
        SqliteFactory.initilize("/tmp/test.db");
        V2MailController c = new V2MailController();
        String harakaMail= "{\n" +
                "  \"id\": \"df13b6c1-0110-3b4a-b284-c7a9f9dd182a\",\n" +
                "  \"request\": {\n" +
                "    \"header\": {\n" +
                "      \"headers\": {\n" +
                "        \"received\": [\n" +
                "          \"from columbarian.com/104.207.133.150\"\n" +
                "        ],\n" +
                "        \"from\": [\n" +
                "          \"durai@columbarian.com\"\n" +
                "        ],\n" +
                "        \"subject\": [\n" +
                "          \"This is subject\"\n" +
                "        ],\n" +
                "        \"thread-topic\": [\n" +
                "          \"This is subject\"\n" +
                "        ],\n" +
                "        \"date\": [\n" +
                "          \"Tue Dec 22 20:01:13 EST 2020\"\n" +
                "        ],\n" +
                "        \"message-id\": [\n" +
                "          \"dd44a03f-c902-4096-b7b7-2ee98493f4a5.columbarian.com\"\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"is_html\": false,\n" +
                "    \"children\": [\n" +
                "      {\n" +
                "        \"header\": {\n" +
                "          \"headers\": {\n" +
                "            \"content-type\": [\n" +
                "              \"text/plain; charset\\u003d\\\"iso-8859-1\\\"\"\n" +
                "            ]\n" +
                "          }\n" +
                "        },\n" +
                "        \"is_html\": false,\n" +
                "        \"bodytext\": \"This is test\\r\\n\",\n" +
                "        \"boundary\": \"--ffae591d-20d1-4fdb-929a-fcf5d513467c--\"\n" +
                "      },\n" +
                "\t  {\n" +
                "        \"header\": {\n" +
                "          \"headers\": {\n" +
                "            \"content-type\": [\n" +
                "              \"text/html; charset\\u003d\\\"iso-8859-1\\\"\"\n" +
                "            ]\n" +
                "          }\n" +
                "        },\n" +
                "        \"is_html\": true,\n" +
                "        \"bodytext\": \"This is test\\r\\n\",\n" +
                "        \"boundary\": \"--f8a2c8d1-965a-401d-933d-32622d025733--\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"user_id\": \"durai\"\n" +
                "  },\n" +
                "  \"status\": \"PENDING\"\n" +
                "}\n";
        HttpHeaders headrs=null;
        HARA004TT hara004TT = new HARA004TT();
        hara004TT.setId(UUID.nameUUIDFromBytes(new Date().toString().getBytes()).toString());
        hara004TT.setRequest(gson.fromJson(harakaMail, HARAKAMAIL.class));
        hara004TT.setStatus("PENDING");
        String requestBody = gson.toJson(hara004TT);
        System.out.println(requestBody);
        c.create(requestBody, headrs);
    }
}