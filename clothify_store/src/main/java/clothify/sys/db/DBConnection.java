package clothify.sys.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;

    private Connection connection;

    private DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothify","root","12345");
    }

    public java.sql.Connection getConnection(){
        return connection;
    }

    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        if (dbConnection==null){
            return dbConnection=new DBConnection();
        }
        return dbConnection;
    }
}
