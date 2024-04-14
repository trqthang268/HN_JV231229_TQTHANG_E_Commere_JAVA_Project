package ra.presentation.usermanagement;

import ra.config.Color;
import ra.config.IOFile;
import ra.config.InputMethods;

import static ra.config.Alert.WRONG_CHOICE;
import static ra.presentation.Main.authentication;
import static ra.presentation.Main.userActive;
import static ra.presentation.adminmanagement.ProductManagement.productImplement;

public class ProductInfo {
    public void displayProductInfoMenu() {
        while (true) {
            System.out.println(Color.CYAN+"╔══════════════════════════════════╗");
            System.out.println("║        PRODUCT INFO MENU         ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║ 1. Đổ dữ liệu sản phẩm           ║");
            System.out.println("║ 2. Thêm vào giỏ hàng             ║");
            System.out.println("║ 3. Quay lại                      ║");
            System.out.println("╚══════════════════════════════════╝"+Color.RESET);
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
                    System.err.println(WRONG_CHOICE);

            }
        }
    }
}
