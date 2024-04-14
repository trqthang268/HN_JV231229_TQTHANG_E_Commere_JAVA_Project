package ra.business.design;

import ra.business.entity.User;

public interface IAuthication {
    User login(String username , String password);
    void register(User user);
    void displayUserList();
    void searchUserByName();
    void changeUserStatus();

    void addToCart(User user);

    void editUserInformation(User user);

    void displayUserInformation(User user);

    void changePassword(User user);

    void deleteCartItem(User user);

    void changeQuantityItem(User user);

    void showListCart(User user);
}
