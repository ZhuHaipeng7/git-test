package controller;

import dao.UserDao;
import model.User;
import service.UserService;
import tools.ManageClientThread;
import tools.Parser;
import tools.Protocol;
import tools.ServerThread;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class UserController {
    public void register(User user){
        UserService service = new UserService();
        service.register(user);
    }

    public String login(String msg, Socket client){
        Protocol protocol = new Protocol();
        Parser parser = new Parser();
        String msgType = parser.getMessageType(msg);
        if(msgType.equals("LOGIN")){
            String[] strs = msg.split(":")[1].split(",");
            long account = Long.parseLong(strs[0]);
            String password = strs[1];
            UserService service = new UserService();
            User user = service.getUserByAccount(account);
            if(user != null && user.getPassword().equals(password) && ManageClientThread.getClientThread(account) == null){
                ServerThread thread = new ServerThread(client);
                thread.start();
                ManageClientThread.addClientThread(account, thread);
                ArrayList<User> friends = service.getFriendsByAccount(user.getAccount());
                thread.notifyOthers(account, friends, true);
                return "LOGIN_SUCCESS:" + user.getAccount() + "," + user.getName() + "|" + protocol.usersToString(friends);
            }
            return "LOGIN_FAIL";
        }else if(msgType.equals("REGISTER")){
            System.out.println("注册");
            Vector<Object> values = parser.getRegisterValues(msg);
            long account = (long)values.get(0);
            String name = (String)values.get(1);
            String password = (String)values.get(2);
            UserService service = new UserService();
            System.out.println("账号："+account);
            if(service.getUserByAccount(account).getName() == null){
                User user = new User();
                user.setAccount(account);
                user.setName(name);
                user.setPassword(password);
                service.register(user);
                return "REGISTER_SUCCESS";
            }
        }
        return "FAIL";
    }

    public void deleteUser(long account){
        UserService service = new UserService();
        service.deleteUserByAccount(account);
    }

    public void update(User user){
        UserService service = new UserService();
        service.updateUser(user);
    }

    public User getUserByAccount(long account){
        UserService service = new UserService();
        return service.getUserByAccount(account);
    }

    public ArrayList<User> getFriends(long account){
        UserService service = new UserService();
        return service.getFriendsByAccount(account);
    }
}
