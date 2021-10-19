package  com.heaerie.sparrow.service;

import com.heaerie.common.PillarPropService;
import com.heaerie.sparrow.exceptions.InvalidTokenException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AuthServiceTest {

    AuthService authService;

    @Before
    public void init() throws IOException {
        PillarPropService.init();
        authService =  new AuthService();
    }

    @Test
    public void testAuthService() {
        String token= authService.createToken("Durai", "Admin");
        System.out.println(token);
        try {
            authService.verifyToken(token);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }
}
