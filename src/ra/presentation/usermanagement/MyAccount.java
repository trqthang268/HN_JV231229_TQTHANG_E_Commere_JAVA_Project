package ra.presentation.usermanagement;

import ra.business.implement.AuthenticationService;
import ra.config.InputMethods;

import static ra.presentation.Main.authentication;
import static ra.presentation.Main.userActive;

public class MyAccount {

    public void displayAccountMenu(){
        while (true) {
            System.out.println("==========ACCOUNT MENU===========");
            System.out.println("1. Đổi mật khẩu");
            System.out.println("2. Hiển thị thông tin các nhân");
            System.out.println("3. Chỉnh sửa thông tin cá nhân");
            System.out.println("4, Quay lại");
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    authentication.changePassword(userActive);
                    break;
                case 2:
                    authentication.displayUserInformation(userActive);
                    break;
                case 3:
                    authentication.editUserInformation(userActive);
                    break;
                case 4:
                    return;
                default:
                    System.err.println("Lựa chọn sai. Vui lòng nhập lại!");

            }
        }
    }
}
