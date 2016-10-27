package ru.mail.park.service;

import org.junit.Before;
import org.junit.Test;
import ru.mail.park.implementationDAO.UserDao;
import ru.mail.park.servicies.AccountService;

/**
 * Created by SergeyCheremisin on 21/10/2016.
 */

public class AccountServiceTest {

    private AccountService accountService;
    private UserDao userDao;

    @Before
    public void setup(){
        userDao = new UserDao();
    }

//    @Before
//    public void setup2(){
//        accountService = new AccountService();
//    }

    @Test
    public void testExistingUserByLogin(){
//        UserProfile user = userDao.existingUserByLogin("Sergey");
//        assertNotNull(user);
//
//        assertSame(user, userDao.existingUserByLogin("Sergey"));
    }


}
