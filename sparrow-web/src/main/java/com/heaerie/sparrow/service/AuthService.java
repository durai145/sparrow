package com.heaerie.sparrow.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.heaerie.common.PillarPropService;
import com.heaerie.sparrow.exceptions.InvalidTokenException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class AuthService {
    Algorithm algorithm;
    Integer tokenExpireTime;
    static final Logger logger = LogManager.getLogger();
    static AuthService singleton;
    public  static final String AUTH_TOKEN = PillarPropService.get("AUTH_TOKEN", "X-AUTH-JWT");
    AuthService() {
        algorithm = Algorithm.HMAC512(PillarPropService.get("SECRECT", "SECRECT"));
        tokenExpireTime = PillarPropService.getNumber("TOKEN_EXPIRE", 900000);
        logger.info("TOKEN_EXPIRE={}",tokenExpireTime);
    }

    public static AuthService getInstance() {
        if (singleton == null ) {
            singleton = new AuthService();
        }
        return  singleton;
    }


    public String createToken(String userId, String role){
        Date expire = new Date();
        expire.setTime(expire.getTime() + tokenExpireTime );
       return JWT.create().withIssuer("heaerieglobalsolutions.com")
               .withIssuedAt(new Date())
               .withClaim("userId", userId)
               .withClaim("role", role)
               .withExpiresAt(expire)
               .sign(algorithm);
    }

    public AuthToken verifyToken(String token) throws InvalidTokenException {
        if (token == null) {
            throw new InvalidTokenException();
        }
        DecodedJWT jwt =JWT.require(algorithm).build().verify(token);
        if (jwt == null) {
            throw new InvalidTokenException();
        }
        logger.info("issuer={}, issueAt={}, ExpiresAt={},",jwt.getIssuer(), jwt.getIssuedAt(),
                jwt.getExpiresAt(), jwt.getPayload());
        System.out.println(jwt.getIssuer());
        System.out.println(jwt.getIssuedAt());
        System.out.println(jwt.getExpiresAt());
        System.out.println(jwt.getPayload());

        AuthToken authToken = new AuthToken();
        authToken.setUserId(jwt.getClaim("userId").asString());
        authToken.setRole(jwt.getClaim("role").asString());
        logger.info("AuthToken={}",authToken);
        return  authToken;
    }


}
