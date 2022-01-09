package dao;

import model.Group;
import model.User;
import tools.DataBase_Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    public List<Group> getGroupsByAccount(long account){
        DataBase_Connection dataBase_connection = new DataBase_Connection();
        Connection conn = dataBase_connection.getConn();
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT groups_all.groupId, groupName, last_read_messageId FROM groups_all, groups_relationship where userAccount = ? and groups_all.groupId = groups_relationship.groupId";

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, account);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                Group group = new Group();
                group.setGroupId(rs.getLong("groupId"));
                group.setName(rs.getString("groupName"));
                group.setHasReadMessageId(rs.getLong("last_read_messageId"));
                groups.add(group);
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
        return groups;
    }
}
