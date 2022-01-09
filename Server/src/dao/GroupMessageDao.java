package dao;

import model.Group;
import model.GroupMessage;
import tools.DataBase_Connection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupMessageDao {
    public List<GroupMessage> getGroupMessagesByGroupId(long groupId){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        List<GroupMessage> groupMessages = new ArrayList<>();
        String sql = "select sendUserAccount,content,sendTime FROM group_messages where groupId = ?";

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, groupId);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                GroupMessage groupMessage = new GroupMessage();
                groupMessage.setGroupId(groupId);
                groupMessage.setContent(rs.getString("content"));
                groupMessage.setSendAccount(rs.getLong("sendUserAccount"));
                groupMessage.setSendDate(rs.getTimestamp("sendTime"));

                groupMessages.add(groupMessage);
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
        return groupMessages;
    }

    public List<GroupMessage> getGroupHasNotReadMessages(long groupId, long last_read_messageId){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        List<GroupMessage> groupMessages = new ArrayList<>();
        String sql = "select sendUserAccount,content,sendTime FROM group_messages where groupId = ? and groupMessageId > ?";

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, last_read_messageId);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                GroupMessage groupMessage = new GroupMessage();
                groupMessage.setGroupId(groupId);
                groupMessage.setContent(rs.getString("content"));
                groupMessage.setSendAccount(rs.getLong("sendUserAccount"));
                groupMessage.setSendDate(rs.getTimestamp("sendTime"));

                groupMessages.add(groupMessage);
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
        return groupMessages;
    }

    public long getLastReadMessageId(long groupId, long account){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        String sql = "select last_read_messageId from groups_relationship where groupId = ? and userAccount = ?";
        long result = -1;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, account);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                result = rs.getLong("last_read_messageId");
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
        return result;
    }

    public void addGroupMessage(long groupId, long sendAccount, String content, Date sendDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        String sql = "insert into group_messages (groupId, sendUserAccount, content, sendTime) values(?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, sendAccount);
            preparedStatement.setString(3, content);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(sdf.format(sendDate)));

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

    public long getMaxGroupMessageId(long groupId){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        String sql = "select MAX(groupMessageId) from group_messages where groupId = ?";
        long result = -1;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, groupId);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                result = rs.getLong("MAX(groupMessageId)");
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
        return result;
    }

    public void updateUserHasReadMessageId(long groupId, long account, long lastMessageId){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        String sql = "update groups_relationship set last_read_messageId=? where groupId = ? and userAccount = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, lastMessageId);
            preparedStatement.setLong(2, groupId);
            preparedStatement.setLong(3, account);

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
