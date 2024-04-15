package ra.business.implement;

import org.mindrot.jbcrypt.BCrypt;
import ra.business.design.IAuthication;
import ra.business.entity.*;
import ra.config.IOFile;
import ra.config.InputMethods;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static ra.config.Alert.PRODUCT_ID_NOTFOUND;
import static ra.config.Alert.WRONG_CHOICE;
import static ra.presentation.adminmanagement.ProductManagement.productImplement;

public class AuthenticationService implements IAuthication {
    public static List<User> userList;

    static {
        File user = new File(IOFile.USER_PATH);
        if (user.length() == 0) {
            userList = IOFile.readFromFile(IOFile.USER_PATH);
            User admin = new User(1, "Truong", "Thang", "admin123", BCrypt.hashpw("admin123", BCrypt.gensalt(5)), "admin123@gmail.com", "0987654321", "Số 1, khu A11, Vencam City, Ha Noi", null, RoleNameUser.ROLE_ADMIN, true);
            userList.add(admin);
            IOFile.writeToFile(IOFile.USER_PATH, userList);
        }else{
            userList = IOFile.readFromFile(IOFile.USER_PATH);
            if (userList.stream().noneMatch(user1 -> user1.getRole().equals(RoleNameUser.ROLE_ADMIN))){
                User admin = new User(1, "Truong", "Thang", "admin123", BCrypt.hashpw("admin123", BCrypt.gensalt(5)), "admin123@gmail.com", "0987654321", "Số 1, khu A11, Vencam City, Ha Noi", null, RoleNameUser.ROLE_ADMIN, true);
                userList.add(admin);
                IOFile.writeToFile(IOFile.USER_PATH, userList);
            }
        }
    }

    @Override
    public User login(String username, String password) {
        User userLogin = getUserFromUserName(username);
        if (userLogin == null) {
            return null;
        }
        boolean checkLogin = BCrypt.checkpw(password, userLogin.getPassword()); // kiểm tra mật khẩu có khớp hay không(so sánh 2 mật khẩu đã được mã hóa)
        if (checkLogin) { //mật khẩu đúng thì trả về thông tin user
            return userLogin;
        }
        return null; // mật khẩu không đúng thì trả về giá trị null
    }

    private User getUserFromUserName(String username) { //dùng stream duyệt qua userlist tìm username trùng với username nhập vào lúc đăng nhập
//        trả về phần tử đầu tiên tìm đc . không thì trả về già trị null
        return userList.stream().filter(user -> user.getUserName().equals(username)).findFirst().orElse(null);
    }

    @Override
    public void register(User user) { //set những giá trị mặc định cho user khi đăng kí
        user.setUserId(getNewUserId());
        user.setRole(RoleNameUser.ROLE_USER);
        user.setUserStatus(true);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));

    }

    @Override
    public void displayUserList() {
        if (userList.isEmpty()) {
            System.out.println("Danh sách người dùng trống");
        } else if (userList.size() == 1) {
            System.out.println("Danh sách người dùng chỉ có admin");
            userList.get(0).displayData();
        } else {
            System.out.println("Danh sách người dùng");
            userList.sort(Comparator.comparing(User::getLastName)); // sắp xếp theo tên người dùng
            for (User user : userList) {
                if (user.getRole() != RoleNameUser.ROLE_ADMIN) {
                    user.displayData();
                    System.out.println("---------------------------");
                }
            }
        }
    }

    @Override
    public void searchUserByName() {
        System.out.println("Nhập tên người dùng muốn tìm");
        String searchName = InputMethods.getString();
        boolean isExist = userList.stream().anyMatch(user -> user.getLastName().contains(searchName));
        if (isExist) {
            System.out.println("Danh sách người có tên " + searchName + " là:");
            userList.stream().filter(user -> user.getLastName().contains(searchName)).forEach(User::displayData);
        } else {
            System.out.println("Không có ai trong danh sách có tên như vậy");
        }
    }

    @Override
    public void changeUserStatus() {
        System.out.println("Nhập mã người dùng muốn sửa đổi trạng thái");
        int changeId = InputMethods.getInteger();
        int changeIdIndex = findIndexById(changeId);
        System.out.println("Trạng thái cũ của người dùng là " + (userList.get(changeIdIndex).isUserStatus() ? "Unblock" : "Block"));
        userList.get(changeIdIndex).setUserStatus(!userList.get(changeIdIndex).isUserStatus());
        System.out.println("Trạng thái mới của người dùng là " + (userList.get(changeIdIndex).isUserStatus() ? "Unblock" : "Block"));
        IOFile.writeToFile(IOFile.USER_PATH, userList);
    }

    private int getNewUserId() { // tìm int id lớn nhất trong userList. tìm thấy thì cộng thêm 1. k thấy thì trả về giá trị 0
        int maxId = userList.stream().map(User::getUserId).max(Comparator.naturalOrder()).orElse(0);
        return maxId + 1;
    }

    public int findIndexById(int id) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void showListCart(User user) {
        try {
            List<CartItem> cartItems = user.getCart();
            if (cartItems == null || cartItems.isEmpty()) {
                System.out.println("Giỏ hàng trống");
            } else {
                System.out.println("Danh sách sản phẩm trong giỏ hàng");
                for (CartItem cartItem : user.getCart()) {
                    cartItem.displayCartItemData();
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void addToCart(User user) {
        List<CartItem> cart = user.getCart();
        System.out.println("Nhập mã sản phẩm");
        int id = InputMethods.getInteger();
        if (productImplement.findIndexById(id) == -1) {
            System.err.println(PRODUCT_ID_NOTFOUND);
        } else {
            System.out.println("Nhập số lượng sản phẩm muốn thêm vào giỏ :");
            int quantity = InputMethods.getInteger();
            CartItem newCartItem = new CartItem(id, quantity);
            if (cart.isEmpty()) {
                cart.add(newCartItem);
            } else {
                if (checkCartItemExist(cart, id)) {
                    //sản phẩm trùng
                    for (CartItem item : cart) {
                        if (item.getProductId() == id) {
                            item.setQuantity(item.getQuantity() + quantity);
                            break;
                        }
                    }
                } else {
                    //sản phẩm không trùng
                    cart.add(newCartItem);
                }
            }
            user.setCart(cart); // thêm giỏ vào ng dùng hiện tại
            for (int i = 0; i < userList.size(); i++) { // cập nhật lưu người dùng hiện tại vào danh sách người dùng
                if (userList.get(i).getUserId() == user.getUserId()) {
                    userList.set(i, user);
                }
            }
            System.out.println("Thêm vào giỏ hàng thành công");
            IOFile.writeToFile(IOFile.USER_PATH, userList);
        }
    }

    private boolean checkCartItemExist(List<CartItem> cart, int id) { //check sản phẩm trùng thông quan productId
        for (CartItem cartItem : cart) {
            if (cartItem.getProductId() == id) {
                return true;
            }
        }
        return false;
    }


    public void changeQuantityItem(User user) {
        List<CartItem> cartItemList = user.getCart();
        System.out.println("Nhập mã sản phẩm ");
        int productIdChangeItem = InputMethods.getInteger();
        if (checkCartItemExist(cartItemList, productIdChangeItem)) {
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getProductId() == productIdChangeItem) {
                    System.out.println("Số lượng cũ là " + cartItem.getQuantity());
                    System.out.println("Nhập số lượng mới");
                    cartItem.setQuantity(InputMethods.getInteger());
                    user.setCart(cartItemList);
                    for (int i = 0; i < userList.size(); i++) { // cập nhật lưu người dùng hiện tại vào danh sách người dùng
                        if (userList.get(i).getUserId() == user.getUserId()) {
                            userList.set(i, user);
                        }
                    }
                    System.out.println("Hoàn tất thay đổi");
                    IOFile.writeToFile(IOFile.USER_PATH, userList);
                    break;
                }
            }
        } else {
            System.err.println(PRODUCT_ID_NOTFOUND);
        }

    }

    public void deleteCartItem(User user) {
        List<CartItem> cartItemList = user.getCart();
        System.out.println("Nhập mã sản phẩm");
        int productIdDelete = InputMethods.getInteger();
        if (checkCartItemExist(cartItemList, productIdDelete)) {
            for (CartItem item : cartItemList) {
                if (item.getProductId() == productIdDelete) {
                    cartItemList.remove(item);
                    user.setCart(cartItemList);
                    for (int i = 0; i < userList.size(); i++) { // cập nhật lưu người dùng hiện tại vào danh sách người dùng
                        if (userList.get(i).getUserId() == user.getUserId()) {
                            userList.set(i, user);
                        }
                    }
                    IOFile.writeToFile(IOFile.USER_PATH, userList);
                    break;
                }
            }
        } else {
            System.err.println(PRODUCT_ID_NOTFOUND);
        }
    }

    public void editUserInformation(User user) {
        byte choise = 0;
        do {
            System.out.println("Thông tin bạn muốn chỉnh sửa :");
            System.out.println("1. Họ và tên");
            System.out.println("2. Tên đăng nhập");
            System.out.println("3. Mật khẩu");
            System.out.println("4. Email");
            System.out.println("5. Số điện thoại");
            System.out.println("6. Địa chỉ");
            System.out.println("7. Thoát");
            System.out.println("Lựa chọn của bạn :");
            choise = InputMethods.getByte();
            switch (choise) {
                case 1:
                    user.inputFirstName();
                    user.inputLastName();
                    IOFile.writeToFile(IOFile.USER_PATH, userList);
                    break;
                case 2:
                    user.inputUserName(userList);
                    IOFile.writeToFile(IOFile.USER_PATH, userList);
                    break;
                case 3:
                    changePassword(user);
                    IOFile.writeToFile(IOFile.USER_PATH, userList);
                    break;
                case 4:
                    user.inputEmail(userList);
                    IOFile.writeToFile(IOFile.USER_PATH, userList);
                    break;
                case 5:
                    user.inputPhone(userList);
                    IOFile.writeToFile(IOFile.USER_PATH, userList);
                    break;
                case 6:
                    user.inputAddress();
                    IOFile.writeToFile(IOFile.USER_PATH, userList);
                    break;
                case 7:
                    break;
                default:
                    System.out.println(WRONG_CHOICE);
            }
        } while (choise != 7);
    }

    public void displayUserInformation(User user) {
        user.displayData();
    }

    public void changePassword(User user) {
        System.out.println("Nhập mật khẩu cũ :");
        String oldPassword = InputMethods.getString();
        boolean checkPassword = BCrypt.checkpw(oldPassword, user.getPassword()); // kiểm tra mật khẩu có khớp hay không(so sánh 2 mật khẩu đã được mã hóa)
        if (checkPassword) {
            System.out.println("Nhập mật khẩu mới :");
            user.inputPassword();
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));
            IOFile.writeToFile(IOFile.USER_PATH, userList);
            System.out.println("Đã lưu mật khẩu mới.");
        } else {
            System.err.println("Mật khẩu sai!! Không thể thay đổi mật khẩu.");
        }
    }
}
