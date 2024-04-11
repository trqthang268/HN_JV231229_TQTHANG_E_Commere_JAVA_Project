package ra.presentation;

import ra.business.design.IAuthication;
import ra.business.entity.RoleNameUser;
import ra.business.entity.User;
import ra.business.implement.AuthenticationService;
import ra.config.IOFile;
import ra.config.InputMethods;

import static ra.business.implement.AuthenticationService.userList;

public class Main {
    private static MenuAdmin menuAdmin = new MenuAdmin();
    private static MenuUser menuUser = new MenuUser();
    private static IAuthication authication = new AuthenticationService();
    public static User userActive = null;

    public static void main(String[] args) {
        while (true){
            System.out.println("==============E-COMMERE MENU=============");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng kí");
            System.out.println("3. Thoát");
            System.out.println("Lựa chọn cuả bạn :");
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Thoát");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại!");
            }
        }
    }
    // chức năng login
    public static void login(){
        System.out.println("===========Đăng nhập==============");
        System.out.println("Nhập username :");
        String username = InputMethods.getString();
        System.out.println("Nhập password :");
        String password = InputMethods.getString();

        User userLogin = authication.login(username,password);
        if (userLogin == null){
            System.err.println("Tên đăng nhập hoặc mật khẩu không chính xác");
            System.out.println("1. Đăng nhập lại");
            System.out.println("2. Bạn chưa có tài khoản ? Đăng ký tại đây.");
            System.out.println("3. Thoát");
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Quay lại");
                    return;
                default:
                    System.err.println("Lựa chọn không chính xác");

            }
        }else{
            if (userLogin.getRole().equals(RoleNameUser.ROLE_ADMIN)){
                userActive = userLogin;
                menuAdmin.displayMenuAdmin();
            } else if (userLogin.getRole().equals(RoleNameUser.ROLE_USER)) {
                if (!userLogin.isUserStatus()){
                    System.err.println("Tài khoản đã bị khóa! Vui lòng liên hệ với admin (0987654321) để giải quyết");
                }else {
                    userActive = userLogin;
                    menuUser.displayMenuUser();
                }
            }else {
                System.err.println("Tài khoản của bạn không có quyền truy cập");
            }
        }
    }

    public static void register(){
        System.out.println("==============Đăng kí=============");
        User user = new User();
        System.out.println("Nhập tên đăng nhập :");
        user.inputUserName(userList);
        System.out.println("Nhập họ :");
        user.inputFirstName();
        System.out.println("Nhập tên :");
        user.inputLastName();
        System.out.println("Nhập password :");
        user.inputPassword();
        System.out.println("Nhập email :");
        user.inputEmail(userList);
        System.out.println("Nhập số điện thoại :");
        user.inputPhone(userList);
        System.out.println("Nhập địa chỉ :");
        user.inputAddress();
        System.out.println("========Đăng kí thành công=========");
        authication.register(user); // set giá trị mặc định cho user bình thường
        login();
    }

}
