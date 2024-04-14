package ra.presentation;

import ra.business.design.IAuthication;
import ra.business.entity.RoleNameUser;
import ra.business.entity.User;
import ra.business.implement.AuthenticationService;
import ra.config.IOFile;
import ra.config.InputMethods;

import java.io.File;

import static ra.business.implement.AuthenticationService.userList;
import static ra.config.Alert.WRONG_CHOICE;
import static ra.config.Color.BLUE;
import static ra.config.Color.RESET;

public class Main {
    public static MenuAdmin menuAdmin = new MenuAdmin();
    public static MenuUser menuUser = new MenuUser();
    public static IAuthication authentication = new AuthenticationService();
    public static User userActive = null;
    static {
        File loginUser = new File(IOFile.USER_LOGIN);
        if (loginUser.length() != 0){
            userActive = IOFile.readUserActive();
        }
        if (userActive != null){
            if (userActive.getRole().equals(RoleNameUser.ROLE_ADMIN)){
                menuAdmin.displayMenuAdmin();
            }else {
                menuUser.displayMenuUser();
            }
        }
    }

    public static void main(String[] args) {
        while (true){
            System.out.println(BLUE+"╔══════════════════E-COMMERE MENU══════════════════╗");
            System.out.println("║   1. Đăng nhập                                   ║");
            System.out.println("║   2. Đăng kí                                     ║");
            System.out.println("║   3. Thoát                                       ║");
            System.out.println("╚══════════════════════════════════════════════════╝"+RESET);
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
                    System.out.println(WRONG_CHOICE);
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

        User userLogin = authentication.login(username,password);
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
                    System.err.println(WRONG_CHOICE);

            }
        }else{
            IOFile.writeUserLogin(userLogin);
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
        User newUser = new User();
        System.out.println("Nhập tên đăng nhập :");
        newUser.inputUserName(userList);
        System.out.println("Nhập họ :");
        newUser.inputFirstName();
        System.out.println("Nhập tên :");
        newUser.inputLastName();
        System.out.println("Nhập password :");
        newUser.inputPassword();
        System.out.println("Nhập email :");
        newUser.inputEmail(userList);
        System.out.println("Nhập số điện thoại :");
        newUser.inputPhone(userList);
        System.out.println("Nhập địa chỉ :");
        newUser.inputAddress();
        System.out.println("========Đăng kí thành công=========");
        authentication.register(newUser); // set giá trị mặc định cho user bình thường
        userList.add(newUser);
        IOFile.writeToFile(IOFile.USER_PATH, userList);
        login();
    }
}
