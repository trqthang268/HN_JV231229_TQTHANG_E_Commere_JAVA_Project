package ra.business.implement;

import ra.business.design.ICatalogDesign;
import ra.business.entity.Catalogs;
import ra.config.Alert;
import ra.config.IOFile;
import ra.config.InputMethods;

import java.util.Comparator;
import java.util.List;

import static ra.config.Alert.CATALOG_ID_NOTFOUND;

public class CatalogImplement implements ICatalogDesign {
    public static List<Catalogs> catalogsList;

    static {
        catalogsList = IOFile.readFromFile(IOFile.CATALOG_PATH);
    }

    @Override
    public void displayAllCatalog() {
        if (catalogsList.isEmpty()) {
            System.err.println("Danh sách danh mục trống");
        } else {
            System.out.println("Danh sách danh mục :");
            catalogsList.sort(Comparator.comparing(Catalogs::getCreatedAt)); // sắp xếp theo thời gian thêm mới nhất
            catalogsList.forEach(Catalogs::displayData);
            System.out.println("==================================");
        }
    }

    @Override
    public void addNewCatalog() {
        System.out.println("Nhập số lượng danh mục bạn muốn thêm");
        byte countNewCatalog = InputMethods.getByte();
        for (int i = 1; i <= countNewCatalog; i++) {
            System.out.println("Nhập thông tin cho danh mục thứ " + i);
            Catalogs newCatalogs = new Catalogs();
            newCatalogs.inputDataCatalog(true);
            catalogsList.add(newCatalogs);
        }
        IOFile.writeToFile(IOFile.CATALOG_PATH, catalogsList);
        System.out.println("Đã thêm mới thành công");
    }

    @Override
    public void searchCatalogByName() {
        System.out.println("Nhập tên danh mục muốn tìm");
        String searchName = InputMethods.getString();
        catalogsList.stream().filter(catalogs -> catalogs.getCatalogName().contains(searchName)).forEach(Catalogs::displayData);
    }

    @Override
    public void editCatalogInfo() {
        System.out.println("Nhập mã danh mục muốn sửa đổi :");
        String updateId = InputMethods.getString();
        int updateIndex = findIndexById(updateId);
        if (updateIndex == -1) {
            System.err.println(CATALOG_ID_NOTFOUND);
            return;
        }
        System.out.println("Thông tin cũ của danh mục:");
        catalogsList.get(updateIndex).displayData();
        System.out.println("Nhập thông tin mới cho danh mục :");
        //sửa thông tin giữ nguyên mã danh mục
        byte choice;
        do {
            System.out.println("Thông tin có thể chỉnh sửa");
            System.out.println("1. Tên danh mục");
            System.out.println("2. Mô tả danh mục");
            System.out.println("3. Trạng thái danh mục");
            System.out.println("4. Chỉnh sửa toàn bộ");
            System.out.println("5. Lưu thông tin chỉnh sửa");
            System.out.println("Lựa chọn của bạn");
            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    catalogsList.get(updateIndex).setCatalogName(catalogsList.get(updateIndex).inputCatalogName(catalogsList));
                    break;
                case 2:
                    System.out.println("Nhập mô tả mới :");
                    catalogsList.get(updateIndex).setDescription(InputMethods.getString());
                    break;
                case 3:
                    System.out.println("Nhập trạng thái mới :");
                    catalogsList.get(updateIndex).setCatalogStatus(InputMethods.getBoolean());
                    break;
                case 4:
                    catalogsList.get(updateIndex).inputDataCatalog(false);
                    IOFile.writeToFile(IOFile.CATALOG_PATH, catalogsList);
                case 5:
                    System.out.println("Cập nhật thông tin thành công");
                    IOFile.writeToFile(IOFile.CATALOG_PATH, catalogsList);
                    break;
                default:
                    System.err.println(Alert.WRONG_CHOICE);
            }
        } while (choice != 5);
    }

    @Override
    public void changeCatalogStatus() {
        System.out.println("Nhập mã danh mục muốn thay đổi trạng thái");
        String changeId = InputMethods.getString();
        int changeIdIndex = findIndexById(changeId);
        if (changeIdIndex == -1) {
            System.err.println(CATALOG_ID_NOTFOUND);
        }
        System.out.println("Trạng thái cũ của danh mục là " + (catalogsList.get(changeIdIndex).isCatalogStatus() ? "Hoạt động" : "Ẩn hoạt động"));
        catalogsList.get(changeIdIndex).setCatalogStatus(!catalogsList.get(changeIdIndex).isCatalogStatus());
        System.out.println("Trạng thái mới của danh mục là " + (catalogsList.get(changeIdIndex).isCatalogStatus() ? "Hoạt động" : "Ẩn hoạt động"));
        IOFile.writeToFile(IOFile.CATALOG_PATH, catalogsList);
    }

    @Override
    public int findIndexById(String id) {
        for (int i = 0; i < catalogsList.size(); i++) {
            if (catalogsList.get(i).getCatalogId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
