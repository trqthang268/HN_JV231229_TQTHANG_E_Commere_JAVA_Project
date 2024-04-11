package ra.presentation.usermanagement;

import ra.business.implement.OrderImplement;
import ra.config.InputMethods;

import static ra.presentation.Main.userActive;

public class OrderHistory {
    OrderImplement orderImplement = new OrderImplement();
    public void displayOrderHistoryMenu(){
        while (true) {
            System.out.println("=============ORDER HISTORY MENU=============");
            System.out.println("1. Đổ dữ liệu lịch sử đơn hàng");
            System.out.println("2. Quay lại");
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    orderImplement.oldOrderUser(userActive);
                    break;
                case 2:
                    return;
                default:
                    System.err.println("Lựa chọn sai. Vui lòng nhập lại!");
            }
        }
    }
}
