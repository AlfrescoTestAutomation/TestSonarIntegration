package org.alfresco.test.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTest
{
    String userName = System.currentTimeMillis() + "@test.com";
    String password = "password";
    String email = userName;
    String shareUrl = "http://127.0.0.1:8080/share";
    String admin = "admin";

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createUserInvalidUserName() throws Exception
    {
        UserService.create(shareUrl, admin, admin, null, password, email);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createUserInvalidPassword() throws Exception
    {
        UserService.create(shareUrl, admin, admin, userName, null, email);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createUserInvalidShareUrl() throws Exception
    {
        UserService.create(null, admin, admin, userName, password, email);
    }

    @Test
    public void creatEnterpriseUser() throws Exception
    {
        boolean result = UserService.create(shareUrl, admin, admin, userName, password, email);
        Assert.assertTrue(result);
        Assert.assertTrue(UserService.userExists(shareUrl, admin, admin, userName));
    }

    @Test
    public void checkUserExistsWhenHeDoesnt() throws Exception
    {
        Assert.assertFalse(UserService.userExists(shareUrl, admin, admin, "booo"));
    }

    @Test
    public void createSameEnterpriseUser() throws Exception
    {
        String userName = "sameUserR1";
        String password = "password";
        UserService.create(shareUrl, admin, admin, userName, password, email);
        boolean result = UserService.create(shareUrl, admin, admin, userName, password, email);
        Assert.assertFalse(result);
    }

    @Test
    public void deleteUser() throws Exception
    {
        String userName = "deleteUser";
        UserService.create(shareUrl, admin, admin, userName, password, email);
        Assert.assertTrue(UserService.delete(shareUrl, admin, admin, userName));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void deleteNonExistent() throws Exception
    {
        String userName = "booo";
        UserService.delete(shareUrl, admin, admin, userName);
    }

}
