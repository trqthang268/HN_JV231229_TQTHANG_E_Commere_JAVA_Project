package ra.presentation.usermanagement;

import ra.config.Color;
import ra.config.IOFile;
import ra.config.InputMethods;

import static ra.config.Alert.WRONG_CHOICE;
import static ra.presentation.Main.authentication;
import static ra.presentation.Main.userActive;

public class MyAccount {

    public void displayAccountMenu(){
        while (true) {
            System.out.println(Color.CYAN+"╔═══════════════════════════════╗");
            System.out.println("║         ACCOUNT MENU          ║");
            System.out.println("╠═══════════════════════════════╣");
            System.out.println("║ 1. Đổi mật khẩu               ║");
            System.out.println("║ 2. Hiển thị thông tin cá nhân ║");
            System.out.println("║ 3. Chỉnh sửa thông tin cá nhân║");
            System.out.println("║ 4. Quay lại                   ║");
            System.out.println("╚═══════════════════════════════╝"+Color.RESET);
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    authentication.changePassword(userActive);
                    userActive= null;
                    IOFile.writeUserLogin(userActive);
                    userActive= IOFile.readUserActive();
                    break;
                case 2:
                    authentication.displayUserInformation(userActive);
                    break;
                case 3:
                    authentication.editUserInformation(userActive);
                    userActive= null;
                    IOFile.writeUserLogin(userActive);
                    userActive = IOFile.readUserActive();
                    break;
                case 4:
                    return;
                default:
                    System.err.println(WRONG_CHOICE);

            }
        }
    }
}
