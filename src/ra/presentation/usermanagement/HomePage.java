package ra.presentation.usermanagement;

import ra.config.Color;
import ra.config.IOFile;
import ra.config.InputMethods;

import static ra.config.Alert.WRONG_CHOICE;
import static ra.presentation.Main.authentication;
import static ra.presentation.Main.userActive;
import static ra.presentation.adminmanagement.ProductManagement.productImplement;

public class HomePage {

    public void displayHomePage() {
        while (true) {
            System.out.println(Color.CYAN+"╔══════════════════════════════════════════════════════╗");
            System.out.println("║                      TRANG CHỦ                       ║");
            System.out.println("╠══════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Tìm kiếm sản phẩm                                 ║");
            System.out.println("║ 2. Hiển thị sản phẩm nổi bật                         ║");
            System.out.println("║ 3. Hiển thị từng nhóm sản phẩm theo danh mục         ║");
            System.out.println("║ 4. Danh sách sản phẩm                                ║");
            System.out.println("║ 5. Hiển thị danh sách sắp xếp theo giá tăng/giảm dần ║");
            System.out.println("║ 6. Thêm vào giỏ hàng                                 ║");
            System.out.println("║ 7. Quay lại                                          ║");
            System.out.println("╚══════════════════════════════════════════════════════╝"+Color.RESET);
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    productImplement.findProductInHomePage();
                    break;
                case 2:
                    productImplement.displayTop10Products();
                    break;
                case 3:
                    productImplement.displayProductByCatalog();
                    break;
                case 4:
                    productImplement.displayProductInHomePage();
                    break;
                case 5:
                    productImplement.displayProductByPrice();
                    break;
                case 6:
                    authentication.addToCart(userActive);
                    userActive= null;
                    IOFile.writeUserLogin(userActive);
                    userActive = IOFile.readUserActive();
                    break;
                case 7:
                    return;
                default:
                    System.err.println(WRONG_CHOICE);
            }
        }
    }
}
