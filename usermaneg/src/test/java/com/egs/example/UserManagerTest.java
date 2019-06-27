//package com.egs.example;
//
//import com.egs.example.data.dao.user.UserDao;
//import com.egs.example.data.dao.user.impl.UserDaoImpl;
//import com.egs.example.data.model.User;
//import com.egs.example.data.model.UserProfile;
//import com.egs.example.data.model.UserStatus;
//import com.egs.example.service.user.exception.EmailAlreadyExistException;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.List;
//import java.util.UUID;
//
//public class UserManagerTest {
//
//    UserDao userDao;
//
//    public UserManagerTest() {
//        userDao = new UserDaoImpl();
//    }
//    @Before
//    public void beforeClazz() {
//        userDao.deleteAll();
//    }
//
//    @After
//    public void afterClazz() {
//        userDao.deleteAll();
//    }
//
//    @Test
//    public void testCreateWithExistingEmail() {
//        final User user1 = createUser();
//        try {
//            final User user2 = new User(randomUUID(),UserProfile.USER, UserStatus.ACTIVE,user1.getEmail(),randomUUID(),randomUUID(),randomUUID());
//            userDao.create(user2);
//        } catch (final EmailAlreadyExistException ex) {
//            Assert.assertEquals(user1.getEmail(), ex.getEmail());
//        }
//    }
//
//
//   @Test
//    public void testCreate() {
//
//
//        final User user = createUser();
//        final User findedUser = userDao.findById(user.getId());
//
//        Assert.assertNotNull(user);
//        Assert.assertNotNull(user.getId());
//        Assert.assertEquals(findedUser.getEmail(), user.getEmail());
//        Assert.assertEquals(findedUser.getPassword(), user.getPassword());
//        Assert.assertEquals(findedUser.getName(), user.getName());
//        Assert.assertEquals(findedUser.getSurname(), user.getSurname());
//    }
//
//
//    @Test
//    public void testUpdate() {
//        final User user = createUser();
//
//        final String name = randomUUID();
//        final String surname = randomUUID();
//
//        final User result = updateUser(user.getId(), user.getProfile(), user.getStatus(), user.getEmail(), user.getPassword(), name, surname);
//
//        Assert.assertNotNull(result);
//
//        Assert.assertEquals(user.getId(), result.getId());
//
//
//    }
//
//    @Test
//    public void testDeleteNotExistingUser() {
//        final int count = userDao.delete(UUID.randomUUID().toString());
//        Assert.assertEquals(0, count);
//    }
//
//    @Test
//    public void testDelete() {
//        final User user = createUser();
//        userDao.delete(user.getId());
//        final User result = userDao.findById(user.getId());
//        Assert.assertNull(result);
//    }
//
//    @Test
//    public void testGetAll() {
//        createUser();
//        final List<User> users = userDao.findAll();
//        Assert.assertNotNull(users);
//    }
//
//    @Test
//    public void testFindByIdNotExistingUser() {
//        final User user = userDao.findById(randomUUID());
//        Assert.assertNull(user);
//    }
//
//    @Test
//    public void testFindById() {
//        final User user = createUser();
//        final User result = userDao.findById(user.getId());
//        Assert.assertEquals(user.getSurname(), result.getSurname());
//    }
//
//
//    @Test
//    public void testExistByEmail() {
//        final User user = createUser();
//        Assert.assertTrue(userDao.existsByEmail(user.getEmail()));
//    }
//
//    private User updateUser(final String id, final UserProfile profile, final UserStatus userStatus, final String email, final String password, final String name, final String surname) {
//        return userDao.update(new User(id, profile, userStatus, email, password, name, surname));
//    }
//
//    private User createUser() {
//        final User user = new User();
//        user.setId(randomUUID());
//        user.setProfile(UserProfile.USER);
//        user.setStatus(UserStatus.ACTIVE);
//        user.setEmail(randomUUID());
//        user.setPassword(randomUUID());
//        user.setName(randomUUID());
//        user.setSurname(randomUUID());
//        return userDao.create(user);
//    }
//
//    private static String randomUUID() {
//        return UUID.randomUUID().toString();
//    }
//}
