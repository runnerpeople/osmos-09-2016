package ru.mail.park.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.park.servicies.AccountService;
import sun.plugin.util.UserProfile;

import java.util.List;

import static javafx.beans.binding.Bindings.when;
import static junit.framework.TestCase.assertFalse;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by SergeyCheremisin on 04/10/16.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ControllerTest {
    @MockBean
    private AccountService accountService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLogin(){
        login();
    }


    private List<String> login() {
        when(accountService.existingUserByLogin(anyString())).thenReturn(new UserProfile("Sergey", "cherem@mail.ru", "password11"));

        ResponseEntity<UserProfile> loginResp = restTemplate.getForEntity("/api/users", UserProfile.class);
        assertEquals(HttpStatus.OK, loginResp.getStatusCode());
        List<String> coockies = loginResp.getHeaders().get("Set-Cookie");
        assertNotNull(coockies);
        assertFalse(coockies.isEmpty());

        UserProfile user = loginResp.getBody();
        assertNotNull(user);

        assertEquals("Sergey", user.getLogin());

        return coockies;
    }

    private void assertEquals(HttpStatus ok, HttpStatus statusCode) {
    }
}
