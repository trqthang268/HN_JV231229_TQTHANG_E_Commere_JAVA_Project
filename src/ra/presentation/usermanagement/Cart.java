package ra.presentation.usermanagement;

import ra.business.design.IAuthication;
import ra.business.implement.AuthenticationService;
import ra.business.implement.OrderImplement;
import ra.config.InputMethods;

import static ra.presentation.Main.authentication;
import static ra.presentation.Main.userActive;
import static ra.presentation.adminmanagement.OrderManagement.orderImplement;

public class Cart {
    public void displayCartMenu(){
        while (true) {
            System.out.println("============CART MENU===========");
            System.out.println("1. Hiển thị danh sách sản phẩm trong giỏ hàng");
            System.out.println("2. Thay đổi số lượng đặt hàng");
            System.out.println("3. Xóa sản phẩm trong giỏ hàng");
            System.out.println("4. Đặt hàng");
            System.out.println("5. Quay lại");
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
                    break;
                case 5:
                    return;
                default:
                    System.err.println("Lựa chọn sai. Vui lòng chọn lại!");
                    break;
            }
        }
    }
}
