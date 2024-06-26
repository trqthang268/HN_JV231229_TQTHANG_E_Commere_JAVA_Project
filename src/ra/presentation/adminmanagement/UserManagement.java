package ra.presentation.adminmanagement;

import ra.config.Color;
import ra.config.InputMethods;

import static ra.config.Alert.WRONG_CHOICE;
import static ra.presentation.Main.authentication;

public class UserManagement {

    public void displayUserMenu(){
        while (true) {
            System.out.println(Color.PURPLE+"╔═══════════════════════════════════════╗");
            System.out.println("║               USER MENU               ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║ 1. Hiển thị danh sách người dùng      ║");
            System.out.println("║ 2. Tìm kiếm người dùng theo tên       ║");
            System.out.println("║ 3. Block/Unblock tài khoản người dùng ║");
            System.out.println("║ 4. Quay lại                           ║");
            System.out.println("╚═══════════════════════════════════════╝"+Color.RESET);
            System.out.println("Lựa chọn của bạn :");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    authentication.displayUserList();
                    break;
                case 2:
                    authentication.searchUserByName();
                    break;
                case 3:
                    authentication.changeUserStatus();
                    break;
                case 4:
                    return;
                default:
                    System.err.println(WRONG_CHOICE);
            }
        }
    }
}
