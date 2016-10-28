package ru.mail.park.main;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.DAO2.SessionClassImpl;
import ru.mail.park.DAO2.UserProfileImpl;
import ru.mail.park.exception.UserNotFoundException;
import ru.mail.park.json.*;
import ru.mail.park.model.*;
import ru.mail.park.servicies.AccountService;
import ru.mail.park.implementationDAO.View;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import javax.servlet.http.HttpSession;


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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(UserProfileImpl.error_string));
        }
        return ResponseEntity.ok(new SuccessResponse(user.getLogin()));
    }


    //-----------------------------------------------------------------------//
    //Controller that deletes a user by id.
    //-----------------------------------------------------------------------//
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeUserById(@PathVariable("id") Integer id) {
        if (!accountService.removeUserById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(UserProfileImpl.error_string));
        }
        return ResponseEntity.ok("User removed");
    }


    //-----------------------------------------------------------------------//
    //Controller, that processes a registration request.
    //-----------------------------------------------------------------------//
    @JsonView(View.Summary.class)
    @RequestMapping(value = "/api/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces="application/json")
    public ResponseEntity login(@RequestBody RegistrationRequest body) {

        final String name = body.getName();
        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email) || StringUtils.isEmpty(name)) {
            return ResponseEntity.ok(new ErrorResponse("Invalid data"));
        }
        final UserProfile existingUser = accountService.existingUserByLogin(login);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User with this login exists"));
        }
        final Integer id = accountService.addUser(name, login, password, email);
        return ResponseEntity.ok(new IdResponse(id));
    }


    //-----------------------------------------------------------------------//
    //Controller, that processes an authorization request.
    //-----------------------------------------------------------------------//

    @RequestMapping(value = "/api/sessions", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody RegistrationRequest body, HttpSession session_p) {
        final String login = body.getLogin();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Invalid data"));
        }
        final SessionClass session = accountService.addSession(login,password);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Incorrect login/password"));
        }
        session_p.setAttribute("User", session);
        return ResponseEntity.ok(new SessionResponse(session.getSession_id(), session.getUser_id()));
    }


    //-----------------------------------------------------------------------//
    //Controller that processes a request for deleting a session.
    //-----------------------------------------------------------------------//
    @RequestMapping(value = "/api/sessions/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeSessions(@PathVariable("id") Integer id) {

        if (accountService.removeSessions(id)) {
            return ResponseEntity.ok("Session finished");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(SessionClassImpl.error_string));
    }
}