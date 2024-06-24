package clothify.sys.controller.user;

import clothify.sys.model.User;
import javafx.collections.ObservableList;

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
    public ObservableList<User> getAllUsers() {
        return null;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }
}
