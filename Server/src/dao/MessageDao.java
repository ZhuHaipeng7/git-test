package dao;

import model.Message;
import tools.DataBase_Connection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// hahahaha Git
public class MessageDao {
    public void addMessage(Message message){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        String sql = "insert into messages (sendAccount, acceptAccount, content, state, date) values(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, message.getSendAccount());
            preparedStatement.setLong(2, message.getAcceptAccount());
            preparedStatement.setString(3, message.getContent());
            if(message.getState()){
                preparedStatement.setLong(4, 1);
            }else {
                preparedStatement.setLong(4, 0);
            }
            preparedStatement.setTimestamp(5, Timestamp.valueOf(sdf.format(message.getSendTime())));

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

    public ArrayList<Message> getMessages(long account1, long account2){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        ArrayList<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages where (sendAccount = ? and acceptAccount =  ?) or (acceptAccount = ? and sendAccount = ?)";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, account1);
            preparedStatement.setLong(2, account2);
            preparedStatement.setLong(3, account1);
            preparedStatement.setLong(4, account2);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                Message message = new Message();
                message.setSendAccount(rs.getLong("sendAccount"));
                message.setAcceptAccount(rs.getLong("acceptAccount"));
                message.setContent(rs.getString("content"));
                message.setState(rs.getBoolean("state"));
                message.setSendTime(rs.getTimestamp("date"));
                //System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message.getSendTime()));
                messages.add(message);
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
        return messages;
    }

    public ArrayList<Message> getUnReadMessages(long fromAccount, long toAccount){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        ArrayList<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages where state = 0 and sendAccount = ? and acceptAccount = ?";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, fromAccount);
            preparedStatement.setLong(2, toAccount);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                Message message = new Message();
                message.setSendAccount(rs.getLong("sendAccount"));
                message.setAcceptAccount(rs.getLong("acceptAccount"));
                message.setContent(rs.getString("content"));
                message.setState(rs.getBoolean("state"));
                message.setSendTime(rs.getTimestamp("date"));
                messages.add(message);
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
        return messages;
    }

    public void updateMessagesRead(long fromAccount, long toAccount){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        String sql = "update messages set state=1 where state = 0 and sendAccount = ? and acceptAccount = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, fromAccount);
            preparedStatement.setLong(2, toAccount);

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
}
