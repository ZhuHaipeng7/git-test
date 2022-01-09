package dao;

import model.User;
import tools.DataBase_Connection;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    public void addUser(User user){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        String sql = "insert into users (account, name, password) values(?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, user.getAccount());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
                conn.close();
                dataBase_connection.closeConn();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void deleteUser(long account){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        String sql = "delete from users where account = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, account);

            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
                conn.close();
                dataBase_connection.closeConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUser(User user){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        String sql = "update users set name=?, password=? where account=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getAccount());

            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
                conn.close();
                dataBase_connection.closeConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User getUserByAccount(long account){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        User user = new User();
        String sql = "select * from users where account = ?";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, account);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                user.setAccount(rs.getLong("account"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                preparedStatement.close();
                conn.close();
                dataBase_connection.closeConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public ArrayList<User> getFriendsByAccount(long account){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        ArrayList<User> friends = new ArrayList<>();
        String sql = "SELECT account, name FROM users,friend_relationship where account1 = account and account2 =  ?";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, account);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                User user = new User();
                user.setAccount(rs.getLong("account"));
                user.setName(rs.getString("name"));
                //user.setPassword(rs.getString("password"));
                friends.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                preparedStatement.close();
                conn.close();
                dataBase_connection.closeConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return friends;
    }

    public ArrayList<User> getGroupMatesByGroupId(long groupId){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        ArrayList<User> groupMates = new ArrayList<>();
        String sql = "SELECT account, name FROM groups_relationship, users WHERE userAccount = account and groupId = ?";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, groupId);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                User user = new User();
                user.setAccount(rs.getLong("account"));
                user.setName(rs.getString("name"));
                //user.setPassword(rs.getString("password"));
                groupMates.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                preparedStatement.close();
                conn.close();
                dataBase_connection.closeConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groupMates;
    }
}
