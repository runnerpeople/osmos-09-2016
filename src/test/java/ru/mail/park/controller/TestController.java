package ru.mail.park.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
public class TestController {

    @Autowired
    protected MockMvc mockMvc;

    private String getUriString(String path, ArrayList<String> params) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(path).build();
        for (String param : params)
            uriComponents = uriComponents.expand(param);
        return uriComponents.toUriString();
    }

    // Default value //
    private String name = "name";
    private String login = "login";
    private String email = "test@mail.ru";
    private String password = "password";

    @Test
    public void testGet404() throws Exception {
        mockMvc.perform(
                get("/hello"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(
                get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
    }

    @Test
    public void testGetUserById() throws Exception {
        String id = "1";
        ArrayList<String> params = new ArrayList<>();
        params.add(id);
        String uriString = getUriString("/api/users/{id}", params);
        mockMvc.perform(
                get(uriString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")))
                .andExpect(jsonPath("$.message.login", is("login")));
    }

    @Test
    public void testGetUserByInvalidId() throws Exception {
        String id = "100";
        ArrayList<String> params = new ArrayList<>();
        params.add(id);
        String uriString = getUriString("/api/users/{id}", params);
        mockMvc.perform(
                get(uriString))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("User not exist")));
    }

    @Test
    public void testDeleteUserById() throws Exception {
        String id = "2";
        ArrayList<String> params = new ArrayList<>();
        params.add(id);
        String uriString = getUriString("/api/users/{id}", params);
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockMvc.perform(
                post("/api/sessions")
                .session(mockHttpSession)
                .content("{\"login\":\"" + login + "1" + "\",\"password\":\"" + password + "1" + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
        assertEquals(login + "1", mockHttpSession.getAttribute("login"));
        assertEquals(Long.valueOf(id), mockHttpSession.getAttribute("userId"));
        mockMvc.perform(
                delete(uriString)
                .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")))
                .andExpect(jsonPath("$.message", is("User has removed")));
    }

    @Test
    public void testDeleteUserByInvalidId() throws Exception {
        String id = "100";
        ArrayList<String> params = new ArrayList<>();
        params.add(id);
        String uriString = getUriString("/api/users/{id}", params);
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockMvc.perform(
                post("/api/sessions")
                .session(mockHttpSession)
                .content("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
        assertEquals(login, mockHttpSession.getAttribute("login"));
        assertNotEquals(Long.valueOf(id), mockHttpSession.getAttribute("userId"));
        mockMvc.perform(
                delete(uriString)
                .session(mockHttpSession))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("Cannot remove other user")));
    }

    @Test
    public void testLoginByLoginPassword() throws Exception {
        mockMvc.perform(
                post("/api/sessions")
                .content("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
    }

    @Test
    public void testLoginByErrorJson() throws Exception {
        mockMvc.perform(
                post("/api/sessions")
                .content("{\"loGin\":\"" + login + "\",\"pasword\":\"" + password + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("Invalid data")));
    }

    @Test
    public void testLoginByIncorrectLoginPassword() throws Exception {
        mockMvc.perform(
                post("/api/sessions")
                .content("{\"login\":\"" + login + "\",\"password\":\"" + "password2" + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("Incorrect login/password")));
    }

    @Test
    public void testGetSessionByCorrectLogin() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockMvc.perform(
                post("/api/sessions")
                .session(mockHttpSession)
                .content("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
        assertEquals(login, mockHttpSession.getAttribute("login"));
        mockMvc.perform(
                get("/api/sessions")
                .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
    }

    @Test
    public void testGetSessionByIncorrectLogin() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockMvc.perform(
                post("/api/sessions")
                .session(mockHttpSession)
                .content("{\"login\":\"" + login + "\",\"password\":\"" + "password2" + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("Incorrect login/password")));
        assertNull(mockHttpSession.getAttribute("login"));
        mockMvc.perform(
                get("/api/sessions")
                .session(mockHttpSession))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("You aren't authenticated. Session is null!")));
    }

    @Test
    public void testDeleteSessionByCorrectLogin() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockMvc.perform(
                post("/api/sessions")
                .session(mockHttpSession)
                .content("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
        assertEquals(login, mockHttpSession.getAttribute("login"));
        mockMvc.perform(
                delete("/api/sessions")
                .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")))
                .andExpect(jsonPath("$.message", is("You are log out!")));
    }

    @Test
    public void testDeleteSessionByIncorrectLogin() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockMvc.perform(
                post("/api/sessions")
                .session(mockHttpSession)
                .content("{\"login\":\"" + login + "\",\"password\":\"" + "password2" + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("Incorrect login/password")));
        assertNull(mockHttpSession.getAttribute("login"));
        mockMvc.perform(
                delete("/api/sessions")
                .session(mockHttpSession))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("You aren't authenticated. Session is null!")));
    }

    @Test
    public void testRegisterByCorrectData() throws Exception {
        mockMvc.perform(
                post("/api/users")
                .content("{\"login\":\"" + login + "3" +
                        "\",\"password\":\"" + password + "3" +
                        "\",\"name\":\"" + name + "3" +
                        "\",\"email\":\"" + email.replace("test", "test3") +
                        "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
    }

    @Test
    public void testRegisterByIncorrectData() throws Exception {
        mockMvc.perform(
                post("/api/users")
                        .content("{\"loGin\":\"" + login + "3" +
                                "\",\"pasword\":\"" + password + "3" +
                                "\",\"name\":\"" + name + "3" +
                                "\",\"emial\":\"" + email.replace("test", "test3") +
                                "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("Invalid data")));
    }

    @Test
    public void testRegisterByDuplicateLogin() throws Exception {
        mockMvc.perform(
                post("/api/users")
                        .content("{\"login\":\"" + login +
                                "\",\"password\":\"" + password + "3" +
                                "\",\"name\":\"" + name + "3" +
                                "\",\"email\":\"" + email.replace("test", "test3") +
                                "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("User with this login already exist")));
    }

    @Test
    public void testRegisterByDuplicateEmail() throws Exception {
        mockMvc.perform(
                post("/api/users")
                        .content("{\"login\":\"" + login + "3" +
                                "\",\"password\":\"" + password + "3" +
                                "\",\"name\":\"" + name + "3" +
                                "\",\"email\":\"" + email +
                                "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("error")))
                .andExpect(jsonPath("$.message.cause", is("User with this email already exist")));
    }


    @Test
    public void testReal() throws Exception {
        mockMvc.perform(
                post("/api/users")
                .content("{\"login\":\"" + login + "3" +
                        "\",\"password\":\"" + password + "3" +
                        "\",\"name\":\"" + name + "3" +
                        "\",\"email\":\"" + email.replace("test", "test3") +
                        "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
        MockHttpSession mockHttpSession = new MockHttpSession();

        mockMvc.perform(
                post("/api/sessions")
                .session(mockHttpSession)
                .content("{\"login\":\"" + login + "3" + "\",\"password\":\"" + password + "3" + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
        assertEquals(login + "3", mockHttpSession.getAttribute("login"));
        mockMvc.perform(
                delete("/api/sessions")
                .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")))
                .andExpect(jsonPath("$.message", is("You are log out!")));
        assertTrue(mockHttpSession.isInvalid());
        mockMvc.perform(
                post("/api/sessions")
                .session(mockHttpSession)
                .content("{\"login\":\"" + login + "3"  + "\",\"password\":\"" + password + "3" + "\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.level", is("info")));
    }


}
