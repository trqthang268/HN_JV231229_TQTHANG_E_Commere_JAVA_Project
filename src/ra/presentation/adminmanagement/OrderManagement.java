package ra.presentation.adminmanagement;

import ra.business.design.IOderDesign;
import ra.business.implement.OrderImplement;
import ra.config.Color;
import ra.config.InputMethods;

import static ra.config.Alert.WRONG_CHOICE;

public class OrderManagement {
    public static IOderDesign orderImplement = new OrderImplement();
    public void displayOrderMenu(){
        while (true){
            System.out.println(Color.PURPLE+"╔══════════════════════════════════════════════╗");
            System.out.println("║                    ORDER MENU                ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║ 1. Hiển thị danh sách hóa đơn chưa xác nhận  ║");
            System.out.println("║ 2. Hiển thị danh sách hóa đơn đã xác nhận    ║");
            System.out.println("║ 3. Xác nhận hóa đơn đang chờ                 ║");
            System.out.println("║ 4. Hủy 1 đơn hàng                            ║");
            System.out.println("║ 5. Quay lại                                  ║");
            System.out.println("╚══════════════════════════════════════════════╝"+Color.RESET);
            System.out.println("Lưạ chọn của bạn :");
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    orderImplement.displayWaitingOrder();
                    break;
                case 2:
                    orderImplement.displaySuccessOrder();
                    break;
                case 3:
                    orderImplement.changeStatusOrder();
                    break;
                case 4:
                    orderImplement.cancelOrder();
                    break;
                case 5:
                    return;
                default:
                    System.err.println(WRONG_CHOICE);
            }
        }
    }
}
