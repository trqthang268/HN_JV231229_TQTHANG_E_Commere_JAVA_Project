package ra.presentation.adminmanagement;

import ra.business.design.ICatalogDesign;
import ra.business.implement.CatalogImplement;
import ra.config.InputMethods;

public class CatalogManagement {
    ICatalogDesign catalogImplement = new CatalogImplement();
    public void displayCatalogMenu(){
        while(true) {
            System.out.println("=======CATALOG MENU=======");
            System.out.println("1. Hiển thị danh sách danh mục");
            System.out.println("2. Tạo mới danh mục");
            System.out.println("3. Tìm kiếm danh mục theo tên");
            System.out.println("4. Chỉnh sửa thông tin danh mục");
            System.out.println("5. Ẩn danh mục theo mã danh mục");
            System.out.println("Lựa chọn của bạn :");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    catalogImplement.displayAllCatalog();
                    break;
                case 2:
                    catalogImplement.addNewCatalog();
                    break;
                case 3:
                    catalogImplement.searchCatalogByName();
                    break;
                case 4:
                    catalogImplement.editCatalogInfo();
                    break;
                case 5:
                    catalogImplement.changeCatalogStatus();
                    break;
                default:
                    System.err.println("Nhập lựa chọn sai. Vui lòng nhập lại");
            }
        }
    }
}
