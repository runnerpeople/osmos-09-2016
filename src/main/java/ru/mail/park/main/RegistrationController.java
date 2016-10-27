package ru.mail.park.main;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.exception.UserNotFoundException;
import ru.mail.park.model.*;
import ru.mail.park.servicies.AccountService;
import ru.mail.park.FakeDB.View;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * Created by SergeyCheremisin on 13/09/16.
 */

@EnableSwagger2
@RestController
@Scope("request")
public class RegistrationController {


    @Autowired
    private AccountService accountService;


    //-----------------------------------------------------------------------//
    //Controller that processes a request for displaying all users.
    //-----------------------------------------------------------------------//
    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ResponseEntity getAllUsers() {

        return ResponseEntity.ok(accountService.getAllUsers());
    }


    //-----------------------------------------------------------------------//
    //Controller that processes a request for getting user's information by id.
    //-----------------------------------------------------------------------//
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserById(@PathVariable("id") Integer id) throws UserNotFoundException {

        final UserProfile user = accountService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"User not exist\"}");
        }
        return ResponseEntity.ok(new SuccessResponse(user.getLogin()));
    }


    //-----------------------------------------------------------------------//
    //Controller that deletes a user by id.
    //-----------------------------------------------------------------------//
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeUserById(@PathVariable("id") Integer id) {
        if (!accountService.removeUserById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok("User removed");
    }


    //-----------------------------------------------------------------------//
    //Controller, that processes a registration request.
    //-----------------------------------------------------------------------//
    @JsonView(View.Summary.class)
    @RequestMapping(value = "/api/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody RegistrationRequest body) {

        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"empty data\"}");
        }
        final UserProfile existingUser = accountService.existingUserByLogin(login);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"User with this login already exist\"}");
        }

        final Integer id = accountService.addUser(login, password, email);

        return ResponseEntity.ok(new IdResponse(id));
    }


    //-----------------------------------------------------------------------//
    //Controller (servlet?), that processes an authorization request.
    //-----------------------------------------------------------------------//

    @RequestMapping(value = "/api/sessions", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody RegistrationRequest body, HttpSession session_p) {
        final String login = body.getLogin();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"invalid data\"}");
        }
        final SessionClass session = accountService.addSession(login);
        session_p.setAttribute("User", session);
        if (session != null) {
            return ResponseEntity.ok(new SesstionResponse(session.getSession_id(), session.getUser_id()));
        }


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"invalid data\"}");
    }

    //-----------------------------------------------------------------------//
    //Controller that processes a request to display sessions.
    //-----------------------------------------------------------------------//
    @RequestMapping(value = "/api/sessions", method = RequestMethod.GET)
    public ResponseEntity getSessions() {
        return ResponseEntity.ok(new GetSesstion(accountService.getSessions()));
    }

    //-----------------------------------------------------------------------//
    //Controller that processes a request for deleting a session.
    //-----------------------------------------------------------------------//
    @RequestMapping(value = "/api/sessions/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeSessions(@PathVariable("id") Integer id) {

        if (accountService.removeSessions(id)) {
            return ResponseEntity.ok("Session finished");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Session not found");
    }


    private static final class RegistrationRequest {
        private String login;
        private String name;
        private String password;
        private String email;

        public String getLogin() {
            return login;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }
    }


    private static final class GetSesstion {
        @JsonView(View.SummaryWithRecipients.class)
        private Collection session;

        public void setSession(Collection session) {
            this.session = session;
        }

        public Collection getSession() {
            return session;
        }

        private GetSesstion(Collection session) {

            this.session = session;
        }
    }

    private static final class SuccessResponse {
        private String login;

        private SuccessResponse(String login) {
            this.login = login;
        }

        //Функция необходима для преобразования см  https://en.wikipedia.org/wiki/Plain_Old_Java_Object
        @SuppressWarnings("unused")
        public String getLogin() {
            return login;
        }

    }


}
