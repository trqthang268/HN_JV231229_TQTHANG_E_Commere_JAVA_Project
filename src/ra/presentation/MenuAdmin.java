package ra.presentation;

import ra.config.InputMethods;
import ra.presentation.adminmanagement.CatalogManagement;
import ra.presentation.adminmanagement.OrderManagement;
import ra.presentation.adminmanagement.ProductManagement;
import ra.presentation.adminmanagement.UserManagement;

import static ra.presentation.Main.userActive;

public class MenuAdmin {
    CatalogManagement catalogManagement = new CatalogManagement();
    UserManagement userManagement = new UserManagement();
    ProductManagement productManagement = new ProductManagement();
    OrderManagement orderManagement = new OrderManagement();
    public void displayMenuAdmin() {
        while (true) {
            System.out.println("==========MENU ADMIN============");
            System.out.println("1. Quản lí người dùng");
            System.out.println("2. Quản lí danh mục");
            System.out.println("3. Quản lí sản phẩm");
            System.out.println("4. Quản lí hóa đơn");
            System.out.println("5. Đăng xuất");
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1 :
                    userManagement.displayUserMenu();
                    break;
                case 2 :
                    catalogManagement.displayCatalogMenu();
                    break;
                case 3 :
                    productManagement.displayProductMenu();
                    break;
                case 4 :
                    orderManagement.displayOrderMenu();
                    break;
                case 5 :
                    userActive = null;
                    return;
                default:
                    System.err.println("Nhập lựa chọn sai. Vui lòng nhập lại!");
            }
        }
    }
}
