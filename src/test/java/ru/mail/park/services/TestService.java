package ru.mail.park.services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.model.UserProfile;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestService {

    @Autowired
    private AccountService accountService;

    @Test
    @SuppressWarnings("unchecked")
    public void testServiceGetAllUsers() {
        UserProfile user = new UserProfile("name3","login3","email3","password3");
        accountService.addUser(user.getName(),user.getLogin(),user.getEmail(),user.getPassword());
        // Now in database 4 elements //
        List<UserProfile> users = accountService.getAllUsers();
        assertEquals(users.size(),4);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testServiceGetLimitUsers() {
        accountService.addUser("name3","login3","password3","email3");
        accountService.addUser("name4","login4","password4","email4");
        accountService.addUser("name5","login5","password5","email5");
        accountService.addUser("name6","login6","password6","email6");
        accountService.addUser("name7","login7","password7","email7");
        List<UserProfile> users = accountService.getAllUsers(5);
        assertEquals(users.size(),5);
    }


    @Test
    public void testServiceCreateUser() {
        UserProfile user = new UserProfile("name3","login3","email3","password3");
        assertNotNull(user);
        accountService.addUser(user.getName(),user.getLogin(),user.getPassword(),user.getEmail());
        UserProfile test = accountService.existingUserByLogin("login3");
        assertNotNull(test);
        assertEquals(user.getPassword(),test.getPassword());
        assertEquals(user.getName(),test.getName());
        assertEquals(user.getEmail(),test.getEmail());
        assertEquals(user.getLogin(),test.getLogin());
    }

    @Test
    public void testServiceExistsByLogin() {
        UserProfile user = accountService.existingUserByLogin("login");
        assertNotNull(user);
        assertEquals(user.getPassword(),"password");
        assertEquals(user.getName(),"name");
        assertEquals(user.getEmail(),"test@mail.ru");
    }

    @Test
    public void testServiceExistsByEmail() {
        UserProfile user = accountService.existingUserByEmail("test2@mail.ru");
        assertNotNull(user);
        assertEquals(user.getPassword(),"password2");
        assertEquals(user.getName(),"name2");
        assertEquals(user.getLogin(),"login2");
    }

    @Test
    public void testServiceCheckLoginAndPassword() {
        UserProfile user = accountService.checkingUserByLoginPassword("login2","password2");
        assertNotNull(user);
    }

    @Test
    public void testServiceDeleteUserById() {
        assertNotNull(accountService.getUserById(1L));
        assertTrue(accountService.removeUserById(1L));
        assertNull(accountService.getUserById(1L));
        assertFalse(accountService.removeUserById(1L));
    }


}
