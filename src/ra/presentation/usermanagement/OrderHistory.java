package ra.presentation.usermanagement;

import ra.config.InputMethods;

public class OrderHistory {
    public void displayOrderHistoryMenu(){
        while (true) {
            System.out.println("=============ORDER HISTORY MENU=============");
            System.out.println("1. Đổ dữ liệu lịch sử đơn hàng");
            System.out.println("2. Quay lại");
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    break;
                case 2:
                    return;
                default:
                    System.err.println("Lựa chọn sai. Vui lòng nhập lại!");
            }
        }
    }
}
