package clothify.sys.controller.user;

import clothify.sys.model.User;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface UserService {
    User searchUser (String Id);
    ArrayList<User> getAllUsers();
    boolean addUser(User user);
}
