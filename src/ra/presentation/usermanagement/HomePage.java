package ra.presentation.usermanagement;

import ra.business.implement.AuthenticationService;
import ra.business.implement.ProductImplement;
import ra.config.InputMethods;

import java.sql.SQLOutput;

import static ra.presentation.Main.authentication;
import static ra.presentation.Main.userActive;
import static ra.presentation.adminmanagement.ProductManagement.productImplement;

public class HomePage {

    public void displayHomePage() {
        while (true) {
            System.out.println("============TRANG CHỦ===========");
            System.out.println("1. Tìm kiếm sản phẩm");
            System.out.println("2. Hiển thị sản phẩm nổi bật");
            System.out.println("3. Hiển thị từng nhóm sản phẩm theo danh mục");
            System.out.println("4. Danh sách sản phẩm");
            System.out.println("5. Hiển thị danh sách sắp xếp theo giá tăng dần/giảm dần");
            System.out.println("6. Thêm vào giỏ hàng");
            System.out.println("7. Quay lại");
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
                    break;
                case 7:
                    return;
                default:
                    System.err.println("Lựa chọn sai. Vui lòng nhập lại!");
            }
        }
    }
}
