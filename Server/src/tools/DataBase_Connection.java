package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase_Connection {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "JDBC:mysql://localhost:3306/database_QQ_h61p?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "root";
    Connection conn = null;

//    public DataBase_Connection(){
//        getConn();
//    }

    public Connection getConn(){
        try {
            Class.forName(JDBC_DRIVER); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConn() throws SQLException {
        conn.close();
    }

//    public static void main(String[] args){
//        new DataBase_Connection();
//    }
}
