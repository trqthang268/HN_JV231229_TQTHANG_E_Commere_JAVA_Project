package ra.presentation.usermanagement;

import ra.config.Color;
import ra.config.IOFile;
import ra.config.InputMethods;

import static ra.config.Alert.WRONG_CHOICE;
import static ra.presentation.Main.authentication;
import static ra.presentation.Main.userActive;
import static ra.presentation.adminmanagement.OrderManagement.orderImplement;

public class Cart {
    public void displayCartMenu(){
        while (true) {
            System.out.println(Color.CYAN+"╔══════════════════════════════════════════════╗");
            System.out.println("║                    CART MENU                 ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║ 1. Xem danh sách sản phẩm trong giỏ hàng     ║");
            System.out.println("║ 2. Thay đổi số lượng đặt mua                 ║");
            System.out.println("║ 3. Xóa sản phẩm khỏi giỏ hàng                ║");
            System.out.println("║ 4. Đặt hàng                                  ║");
            System.out.println("║ 5. Quay lại                                  ║");
            System.out.println("╚══════════════════════════════════════════════╝"+Color.RESET);
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    authentication.showListCart(userActive);
                    break;
                case 2:
                    authentication.changeQuantityItem(userActive);
                    break;
                case 3:
                    authentication.deleteCartItem(userActive);
                    break;
                case 4:
                    orderImplement.getOrder(userActive);
                    userActive= null;
                    IOFile.writeUserLogin(userActive);
                    userActive = IOFile.readUserActive();
                    break;
                case 5:
                    return;
                default:
                    System.err.println(WRONG_CHOICE);
            }
        }
    }
}
