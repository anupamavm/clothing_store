package clothify.sys.controller.user;

import clothify.sys.db.DBConnection;
import clothify.sys.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController implements UserService{
    private static UserController instance;
    private UserController (){}

    public static UserController getInstance(){
        if (instance==null){
            return instance=new UserController();
        }
        return instance;
    }
    @Override
    public User searchUser(String Id) {
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM User");

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBoolean(4),
                        resultSet.getBoolean(5)
                );
                System.out.println(user);
                userList.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }


    @Override
    public boolean addUser(User user) {
        return false;
    }
}
