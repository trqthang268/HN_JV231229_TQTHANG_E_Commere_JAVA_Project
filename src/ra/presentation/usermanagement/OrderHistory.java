package ra.presentation.usermanagement;

import ra.config.Color;
import ra.config.InputMethods;

import static ra.config.Alert.WRONG_CHOICE;
import static ra.presentation.Main.userActive;
import static ra.presentation.adminmanagement.OrderManagement.orderImplement;

public class OrderHistory {
    public void displayOrderHistoryMenu(){
        while (true) {
            System.out.println(Color.CYAN+"╔══════════════════════════════════════╗");
            System.out.println("║         ORDER HISTORY MENU           ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Đổ dữ liệu lịch sử đơn hàng       ║");
            System.out.println("║ 2. Quay lại                          ║");
            System.out.println("╚══════════════════════════════════════╝"+Color.RESET);
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    orderImplement.oldOrderUser(userActive);
                    break;
                case 2:
                    return;
                default:
                    System.err.println(WRONG_CHOICE);
            }
        }
    }
}
