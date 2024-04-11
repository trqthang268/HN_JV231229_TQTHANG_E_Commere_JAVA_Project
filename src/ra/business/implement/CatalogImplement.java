package ra.business.implement;

import com.sun.security.jgss.GSSUtil;
import ra.business.design.ICatalogDesign;
import ra.business.entity.Catalogs;
import ra.config.IOFile;
import ra.config.InputMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CatalogImplement implements ICatalogDesign {
    public static List<Catalogs> catalogsList;
    static {
        catalogsList = IOFile.readFromFile(IOFile.CATALOG_PATH);
    }

    @Override
    public void displayAllCatalog() {
        if (catalogsList.isEmpty()){
            System.err.println("Danh sách danh mục trống");
        }else {
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
        for (int i = 1; i <= countNewCatalog ; i++) {
            System.out.println("Nhập thông tin cho danh mục thứ "+i);
            Catalogs newCatalogs = new Catalogs();
            newCatalogs.inputDataCatalog(true);
            catalogsList.add(newCatalogs);
        }
        IOFile.writeToFile(IOFile.CATALOG_PATH,catalogsList);
        System.out.println("Đã thêm mới thành công");
    }

    @Override
    public void searchCatalogByName() {
        System.out.println("Nhập tên danh mục muốn tìm");
        String searchName = InputMethods.getString();
        catalogsList.stream().filter(catalogs -> catalogs.getCatalogName().equalsIgnoreCase(searchName)).forEach(Catalogs::displayData);
    }

    @Override
    public void editCatalogInfo() {
        System.out.println("Nhập mã danh mục muốn sửa đổi :");
        String updateId = InputMethods.getString();
        int updateIndex = findIndexbyId(updateId);
        if(updateIndex == -1){
            System.err.println("Không tìm thấy mã danh mục");
            return;
        }
        System.out.println("Thông tin cũ của danh mục:");
        catalogsList.get(updateIndex).displayData();
        System.out.println("Nhập thông tin mới cho danh mục :");
        //sửa thông tin giữ nguyên mã danh mục
        catalogsList.get(updateIndex).inputDataCatalog(false);
        IOFile.writeToFile(IOFile.CATALOG_PATH,catalogsList);
        System.out.println("Cập nhật thành công");
    }

    @Override
    public void changeCatalogStatus() {
        System.out.println("Nhập mã danh mục muốn thay đổi trạng thái");
        String changeId = InputMethods.getString();
        int changeIdIndex = findIndexbyId(changeId);
        if(changeIdIndex == -1){
            System.err.println("Không tìm thấy mã danh mục");
        }
        System.out.println("Trạng thái cũ của danh mục là "+(catalogsList.get(changeIdIndex).isCatalogStatus()?"Hoạt động":"Ẩn hoạt động"));
        catalogsList.get(changeIdIndex).setCatalogStatus(!catalogsList.get(changeIdIndex).isCatalogStatus());
        System.out.println("Trạng thái mới của danh mục là "+(catalogsList.get(changeIdIndex).isCatalogStatus()?"Hoạt động":"Ẩn hoạt động"));
        IOFile.writeToFile(IOFile.CATALOG_PATH,catalogsList);
    }

    @Override
    public int findIndexbyId(String id) {
        for (int i = 0; i < catalogsList.size(); i++) {
            if (catalogsList.get(i).getCatalogId().equals(id)){
                return i;
            }
        }
        return -1;
    }
}
