package ru.mail.park.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.json.*;
import ru.mail.park.model.UserProfile;
import ru.mail.park.model.UserSession;
import ru.mail.park.service.AccountService;
import ru.mail.park.service.SessionService;


import javax.servlet.http.HttpSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;


@RestController
public class RegistrationController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;


    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok()
                .body(new Response<>("info",accountService.getAllUsers()));
    }


    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        final UserProfile user = accountService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>("error",new ErrorMessage("User not exist")));
        }
        return ResponseEntity
                .ok(new Response<>("info",new SignUpMessage(user.getLogin())));
    }


    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeUserById(@PathVariable("id") Long id) {
        if (!isNull(httpSession.getAttribute("userId")) && !id.equals((Long)httpSession.getAttribute("userId"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response<>("error",new ErrorMessage("Cannot remove other user")));
        }
        return ResponseEntity.ok(new Response<>("info", "User has removed"));
    }


    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody RegistrationRequest body) {

        final String login = body.getLogin();
        final String name = body.getName();
        final String password = body.getPassword();
        final String email = body.getEmail();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email) || StringUtils.isEmpty(name)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new Response<>("error",new ErrorMessage("Invalid data")));
        }
        final UserProfile existingUserByLogin = accountService.existingUserByLogin(login);
        if (existingUserByLogin != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>("error",new ErrorMessage("User with this login already exist")));
        }
        final UserProfile existingUserByEmail = accountService.existingUserByEmail(email);
        if (existingUserByEmail != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>("error",new ErrorMessage("User with this email already exist")));
        }
        final Long id = accountService.addUser(name, login, password, email);
        return ResponseEntity.ok(new Response<>("info",new AuthMessage(id)));
    }

    @RequestMapping(value = "/api/sessions", method = RequestMethod.GET)
    public ResponseEntity<?> getSession() {
        if (httpSession.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>("error",new ErrorMessage("You aren't authenticated. Session is null!")));
        }
        return ResponseEntity.ok()
                .body(new Response<>("info",new GetSessionMessage(httpSession.getId())));
    }

    @RequestMapping(value = "/api/sessions", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSession() {
        if (httpSession.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>("error",new ErrorMessage("You aren't authenticated. Session is null!")));
        }
        DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
//            System.out.println(httpSession.getAttribute("userId"));
//            System.out.println(httpSession.getId());
            sessionService.updateSession((Long) httpSession.getAttribute("userId"), null, dateTimeFormat.parse(dateTimeFormat.format(new Date())), (byte) 0);
        } catch (ParseException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("error", new ErrorMessage("Exception in Parse(Date)")));
        }

        httpSession.invalidate();
        return ResponseEntity.ok()
                .body(new Response<>("info","You are log out!"));
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }


    @RequestMapping(value = "/api/sessions", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody RegistrationRequest body) {
        final String login = body.getLogin();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>("error",new ErrorMessage("Invalid data")));
        }

        final UserProfile user = accountService.checkingUserByLoginPassword(login,password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>("error",new ErrorMessage("Incorrect login/password")));
        }
        httpSession.setAttribute("userId",user.getId());
        httpSession.setAttribute("login",login);

        Date date = new Date();
        DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateTimeFormat.format(date);
        try {
            date = dateTimeFormat.parse(dateString);
        }
        catch (ParseException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("error",new ErrorMessage("Exception in Parse(Date)")));
        }
        final UserSession session = sessionService.findSessionByUserId(user.getId());
        if (session != null && (session.getIs_auth() & 1) == 1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>("error",new ErrorMessage("This user is registered! You cannot logged in more 1 time..")));
        if (session != null && session.getSession_id() != null) {
            Long diff_days;
            try {
                diff_days = getDateDiff(session.getDate_session(),dateTimeFormat.parse(dateTimeFormat.format(new Date())),TimeUnit.DAYS);
            }
            catch (ParseException ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("error",new ErrorMessage("Exception in Parse(Date)")));
            }
            if (diff_days < 30)
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response<>("error",new ErrorMessage("Error with cookie. Please remove cookie from browser and try again")));
            else {
                try {
                    sessionService.updateSession(user.getId(), httpSession.getId(), dateTimeFormat.parse(dateTimeFormat.format(new Date())), (byte) 1);
                } catch (ParseException ex) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("error", new ErrorMessage("Exception in Parse(Date)")));
                }
            }
        }

        final Long userSession = sessionService.addSession(user.getId(),httpSession.getId(),date,(byte)1);
        return ResponseEntity.ok()
                .body(new Response<>("info",new SessionMessage(httpSession.getId(),user.getId())));

    }

    @RequestMapping(value = "/api/auth", method = RequestMethod.GET)
    public ResponseEntity<?> isAuth() {
        if (httpSession.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>("error",new ErrorMessage("Access allowed only for registered users")));
        }
        final UserSession session = sessionService.findSessionByUserId((Long) httpSession.getAttribute("userId"));
        if (session.getSession_id().equals(httpSession.getId()) && (session.getIs_auth() & 1) == 1)
            return ResponseEntity.ok()
                    .body(new Response<>("info",("User logged in")));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("error", new ErrorMessage("Unknown result in isAuth")));
    }

}


class RegistrationRequest {
    private String name;
    private String login;
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