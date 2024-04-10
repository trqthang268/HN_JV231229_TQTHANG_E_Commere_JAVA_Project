package ra.business.entity;

import ra.config.InputMethods;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private List<CartItem> cartItemList = new ArrayList<>();
    private RoleNameUser role;
    private boolean userStatus;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String userName, String password, String email, String phoneNumber, String address, List<CartItem> cart, RoleNameUser role, boolean userStatus) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.cartItemList = cart;
        this.role = role;
        this.userStatus = userStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CartItem> getCart() {
        return cartItemList;
    }

    public void setCart(List<CartItem> cart) {
        this.cartItemList = cart;
    }

    public RoleNameUser getRole() {
        return role;
    }

    public void setRole(RoleNameUser role) {
        this.role = role;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    /**inputData*/
    public void inputFirstName(){
        this.firstName = InputMethods.getString();
    }
    public void inputLastName(){
        this.lastName = InputMethods.getString();
    }
    public void inputUserName(List<User> userList){
        while (true) {
            String userNameRegex = "^[A-Za-z][A-Za-z0-9]{5,29}$"; // bắt đầu bằng 1 chữ cái, từ 6 đến 30 kí tự, không có kí tự đặc biệt
            String userName = InputMethods.getString();
            if (userName.matches(userNameRegex)){ // đúng định dạng
                boolean isExist = userList.stream().anyMatch(user -> user.getUserName().equals(userName)); // check trùng lặp
                if (isExist){ // có trùng lặp
                    System.err.println("Tên này đã tồn tại. Vui lòng nhập tên khác !");
                }else{ //không trùng lặp
                    this.userName = userName;
                    break;
                }
            }else { //không đúng đinh dạng
                System.err.println("Tên không phù hợp với định dạng. Vui lòng nhâp tên khác!");
            }
        }
    }

    public void inputPassword(){
        while (true) {
            String passwordRegex = "^[A-Za-z0-9]{8,30}$"; // 8 đến 30 kí tự, kí tự chữ, số
            String password = InputMethods.getString();
            if (password.matches(passwordRegex)){ // đúng định dạng
                this.password = password;
                break;
            }else{ // không đúng định dạng
                System.err.println("Mật khẩu không phù hợp định dạng. Vui lòng nhập mật khác !");
            }
        }
    }

    public void inputEmail(List<User> userList){
        while (true){
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            String email = InputMethods.getString();
            if (email.matches(emailRegex)){ // đúng định dạng
                boolean isExist = userList.stream().anyMatch(user -> user.getEmail().equals(email));
                if (isExist){// có trùng lặp với user khác
                    System.err.println("Email đã được sử dụng. Vui lòng nhập email khác!");
                }else{ // không trùng lặp
                    this.email = email;
                    break;
                }
            }else{ // không đúng đinh dạng
                System.err.println("Email không đúng định dạng. Vui lòng nhập lại email khác !");
            }
        }
    }

    public void inputPhone(List<User> userList){
        while (true){
            String phoneRegex = "((^(\\+84|84|0|0084){1})([35789]))+([0-9]{8})$";
            String phone = InputMethods.getString();
            if (phone.matches(phoneRegex)){ // đúng định dạng
                boolean isExist = userList.stream().anyMatch(user -> user.getPhoneNumber().equals(phone));
                if (isExist){// có trùng lặp với user khác
                    System.err.println("Số điện thoại đã được sử dụng. Vui lòng nhập số khác!");
                }else{ // không trùng lặp
                    this.phoneNumber = phone;
                    break;
                }
            }else{ // không đúng đinh dạng
                System.err.println("Số điện thoại không đúng định dạng. Vui lòng nhập lại số khác !");
            }
        }
    }

    public void inputAddress(){
        this.address = InputMethods.getString();
    }

    public void displayData(){
        String roleUser = null;
        switch (role){
            case ROLE_USER:
                roleUser = "User";
                System.out.printf("| Mã : %d | Tên : %s %s | Tên đăng nhập : %s | Mật khẩu : %s | Email : %s | Sdt : %s |\n",userId,firstName,lastName,userName,password,email,phoneNumber);
                System.out.printf("| Địa chỉ : %s | Giỏ hàng : %s | Role : %s | Trạng thái : %s |",address,cartItemList.toString(),roleUser,userStatus?"Unblock":"Block");
                break;
            case ROLE_ADMIN:
                roleUser = "Admin";
                System.out.printf("| Mã : %d | Tên : %s %s | Tên đăng nhập : %s | Mật khẩu : %s | Email : %s | Sdt : %s |\n",userId,firstName,lastName,userName,password,email,phoneNumber);
                System.out.printf("| Địa chỉ : %s | Role : %s | Trạng thái : %s |",address,roleUser,userStatus?"Unblock":"Block");
                break;
        }
        }

}

