package clothify.sys.controller.user;

import clothify.sys.model.User;
import javafx.collections.ObservableList;

public interface UserService {
    User searchUser (String Id);
    ObservableList<User> getAllUsers();
    boolean addUser(User user);
}
