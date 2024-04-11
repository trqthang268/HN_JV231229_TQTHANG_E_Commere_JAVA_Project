package ra.presentation.usermanagement;

import ra.business.implement.AuthenticationService;
import ra.business.implement.ProductImplement;
import ra.config.InputMethods;

import static ra.presentation.Main.authentication;
import static ra.presentation.Main.userActive;
import static ra.presentation.adminmanagement.ProductManagement.productImplement;

public class ProductInfo {
    public void displayProductInfoMenu() {
        while (true) {
            System.out.println("============PRODUCT INFO MENU===========");
            System.out.println("1. Đổ dữ liệu sản phẩm");
            System.out.println("2. Thêm vào giỏ hàng");
            System.out.println("3. Quay lại");
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    productImplement.displayProductById();
                    break;
                case 2:
                    authentication.addToCart(userActive);
                    break;
                case 3:
                    return;
                default:
                    System.err.println("Lựa chọn sai. Vui lòng nhập lại!");

            }
        }
    }
}
