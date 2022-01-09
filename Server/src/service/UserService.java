package service;

import dao.MessageDao;
import dao.UserDao;
import model.Message;
import model.User;

import java.util.ArrayList;

public class UserService {
    public void register(User user){
        UserDao dao = new UserDao();
        dao.addUser(user);
    }

    public void deleteUserByAccount(long account){
        UserDao dao = new UserDao();
        dao.deleteUser(account);
    }

    public void updateUser(User user){
        UserDao dao = new UserDao();
        dao.updateUser(user);
    }

    public User getUserByAccount(long account){
        UserDao dao = new UserDao();
        return dao.getUserByAccount(account);
    }

    public ArrayList<User> getFriendsByAccount(long account){
        UserDao userDao = new UserDao();
        MessageDao messageDao = new MessageDao();
        ArrayList<User> users = userDao.getFriendsByAccount(account);
        for(User user : users){
            user.setMessages(messageDao.getUnReadMessages(user.getAccount(), account));
            messageDao.updateMessagesRead(user.getAccount(), account);
        }
        return users;
    }

    public String loginIn(long account, String password){
        UserDao dao = new UserDao();
        return "";
    }

    public ArrayList<User> getGroupMatesByGroupId(long groupId){
        UserDao dao = new UserDao();
        return dao.getGroupMatesByGroupId(groupId);
    }
}
