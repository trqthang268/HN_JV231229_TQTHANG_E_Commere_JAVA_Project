package ra.business.design;

import ra.business.entity.User;

public interface IAuthication {
    User login(String username , String password);
    void register(User user);
    void displayUserList();
    void searchUserByName();
    void changeUserStatus();
}
