package ra.presentation.adminmanagement;

import ra.business.design.IProductDesign;
import ra.business.implement.ProductImplement;
import ra.config.Color;
import ra.config.InputMethods;

import static ra.config.Alert.WRONG_CHOICE;

public class ProductManagement {
    public static IProductDesign productImplement = new ProductImplement();
    public void displayProductMenu(){
        while (true){
            System.out.println(Color.PURPLE+"╔══════════════════════════════════════╗");
            System.out.println("║              MENU PRODUCT            ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Hiển thị danh sách sản phẩm       ║");
            System.out.println("║ 2. Thêm mới 1 hoặc nhiều sản phẩm    ║");
            System.out.println("║ 3. Chỉnh sửa thông tin sản phẩm      ║");
            System.out.println("║ 4. Ẩn sản phẩm theo mã sản phẩm      ║");
            System.out.println("║ 5. Tìm kiếm sản phẩm theo tên        ║");
            System.out.println("║ 6. Quay lại                          ║");
            System.out.println("╚══════════════════════════════════════╝"+Color.RESET);
            System.out.println("Lựa chọn của bạn :");
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    productImplement.displayAllProducts();
                    break;
                case 2:
                    productImplement.addNewProducts();
                    break;
                case 3:
                    productImplement.editInfoProduct();
                    break;
                case 4:
                    productImplement.changeStatusById();
                    break;
                case 5:
                    productImplement.searchProductByName();
                    break;
                case 6:
                    return;
                default:
                    System.err.println(WRONG_CHOICE);
            }
        }
    }
}
