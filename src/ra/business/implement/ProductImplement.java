package ra.business.implement;

import ra.business.design.IProductDesign;
import ra.business.entity.Catalogs;
import ra.business.entity.Products;
import ra.config.Alert;
import ra.config.IOFile;
import ra.config.InputMethods;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static ra.business.implement.CatalogImplement.catalogsList;
import static ra.config.Alert.*;
import static ra.presentation.adminmanagement.CatalogManagement.catalogImplement;

public class ProductImplement implements IProductDesign {
    public static List<Products> productsList;
    static {
        productsList = IOFile.readFromFile(IOFile.PRODUCTS_PATH);
    }

    @Override
    public void displayAllProducts() {
        if (productsList.isEmpty()){
            System.out.println("Danh sách sản phẩm trống");
        }else{
            System.out.println("Danh sách sản phẩm :");
            productsList.sort(((o1, o2) -> o2.getStock()-o1.getStock())); // sắp xếp theo thời gian thêm mới nhất
            productsList.forEach(Products::displayDataProduct);
        }
    }

    @Override
    public void addNewProducts() {
        if (catalogsList.isEmpty()){
            System.err.println("Vui lòng tạo danh mục trước");
        }else {
            System.out.println("Nhập số lượng sản phẩm muốn thêm");
            int countNewProduct = InputMethods.getInteger();
            for (int i = 0; i < countNewProduct; i++) {
                System.out.println("Nhập thông tin cho sản phẩm thứ " + (i + 1));
                Products products = new Products();
                products.inputDataProduct(true);
                productsList.add(products);
            }
            IOFile.writeToFile(IOFile.PRODUCTS_PATH, productsList);
            System.out.println("Đã thêm mới thành công");
        }
    }

    @Override
    public void editInfoProduct() {
        System.out.println("Nhập mã sản phẩm muốn chỉnh sửa :");
        int editId = InputMethods.getInteger();
        int editIdIndex = findIndexById(editId);
        if (editIdIndex == -1){
            System.err.println(PRODUCT_ID_NOTFOUND);
        }else{
            System.out.println("Thông tin cũ của sản phẩm");
            productsList.get(editIdIndex).displayDataProduct();
            byte choice;
            do {
                System.out.println("Thông tin có thể chỉnh sửa:");
                System.out.println("1. Tên sản phẩm");
                System.out.println("2. Danh mục sản phẩm");
                System.out.println("3. Mô tả/ Thông số");
                System.out.println("4. Đơn giá");
                System.out.println("5. Số lượng trong kho");
                System.out.println("6. Trạng thái");
                System.out.println("7. Chỉnh sửa toàn bộ");
                System.out.println("8. Lưu chỉnh sửa");
                System.out.println("Lựa chọn của bạn :");
                choice = InputMethods.getByte();
                switch (choice){
                    case 1:
                        productsList.get(editIdIndex).setProductName(productsList.get(editIdIndex).inputProductName(productsList));
                        break;
                    case 2:
                        productsList.get(editIdIndex).setCategoryId(productsList.get(editIdIndex).inputCatalogId(catalogsList));
                        break;
                    case 3:
                        productsList.get(editIdIndex).setDescription(productsList.get(editIdIndex).inputDescription());
                        break;
                    case 4:
                        productsList.get(editIdIndex).setUnitPrice(productsList.get(editIdIndex).inputUnitPrice());
                        break;
                    case 5:
                        productsList.get(editIdIndex).setStock(productsList.get(editIdIndex).inputStock());
                        break;
                    case 6:
                        productsList.get(editIdIndex).setProductStatus(InputMethods.getBoolean());
                        break;
                    case 7:
                        System.out.println("Nhập thông tin mới cho sản phẩm");
                        productsList.get(editIdIndex).inputDataProduct(false);
                    case 8:
                        productsList.get(editIdIndex).setUpdateAt(LocalDate.now());
                        System.out.println("Cập nhật thông tin thành công");
                        IOFile.writeToFile(IOFile.PRODUCTS_PATH,productsList);
                        break;
                    default:
                        System.err.println(WRONG_CHOICE);
                        break;
                }
            }while (choice!=8);
        }

    }

    @Override
    public void changeStatusById() {
        System.out.println("Nhập mã sản phẩm muốn sửa trạng thái");
        int changeStatusId = InputMethods.getInteger();
        int statusIdIndex = findIndexById(changeStatusId);
        if (statusIdIndex == -1){
            System.err.println(PRODUCT_ID_NOTFOUND);
        }else {
            System.out.println("Trạng thái cũ của sản phẩm là " + (productsList.get(statusIdIndex).isProductStatus() ? "Đang bán" : "Ngưng bán"));
            productsList.get(statusIdIndex).setProductStatus(!productsList.get(statusIdIndex).isProductStatus());
            System.out.println("Trạng thái mới của sản phẩm là " + (productsList.get(statusIdIndex).isProductStatus() ? "Đang bán" : "Ngưng bán"));
            IOFile.writeToFile(IOFile.PRODUCTS_PATH, productsList);
        }
    }

    @Override
    public void searchProductByName() {
        System.out.println("Nhập tên sản phẩm cần tìm");
        String searchName  = InputMethods.getString();
        boolean isExist = productsList.stream().anyMatch(products -> products.getProductName().contains(searchName));
        if (isExist){
            System.out.println("Sản phẩm cần tìm :");
            productsList.stream().filter(products -> products.getProductName().contains(searchName)).forEach(Products::displayDataProduct);
        }else{
            System.out.println(PRODUCT_NOTFOUND);
        }
    }

    public int findIndexById(int id){
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getProductId()==id){
                return i;
            }
        }
        return -1;

    }

    public void findProductInHomePage(){
        System.out.println("Nhập tên sản phẩm cần tìm");
        String searchName = InputMethods.getString();
        boolean isExist = productsList.stream().anyMatch(products -> products.getProductName().contains(searchName));
        if (isExist){
            for (Products products : productsList) {
                if (products.isProductStatus() && checkStatusCatalogById(products.getCategoryId()) && products.getProductName().contains(searchName)){
                    products.displayProductForCustomer();
                }
            }
        }else {
            System.out.println(PRODUCT_NOTFOUND);
        }
    }

    public boolean checkStatusCatalogById(String id){
        for (Catalogs catalogs : catalogsList) {
            if (catalogs.getCatalogId().equals(id)){
                if (catalogs.isCatalogStatus()){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    public void displayTop10Products() {
        productsList.sort(Comparator.comparing(Products::getStock));
        System.out.println("10 sản phẩm nổi bật :");
        productsList.stream().limit(10).forEach(Products::displayProductForCustomer); // hiển thị những sản phẩm còn ít số lượng nhất
    }

    public void displayProductByCatalog() {
        System.out.println("Nhập mã danh mục :");
        String catalogId = InputMethods.getString();
        if(catalogImplement.findIndexById(catalogId) == -1){
            System.err.println(PRODUCT_ID_NOTFOUND);
        }else {
            System.out.println("Danh sách sản phẩm của danh mục trên :");
            for (Products products : productsList) {
                if (products.getCategoryId().equals(catalogId)) {
                    products.displayProductForCustomer();
                }
            }
        }
    }

    public void displayProductInHomePage() {
        System.out.println("Danh sách toàn bộ sản phẩm :");
        for (Products products : productsList) {
            if (products.isProductStatus() && checkStatusCatalogById(products.getCategoryId())){
                products.displayProductForCustomer();
            }
        }
    }

    public void displayProductByPrice() {
        while (true) {
            System.out.println("Sắp xếp sản phẩm theo :");
            System.out.println("1. Giá tăng dần");
            System.out.println("2. Giá giảm dần");
            System.out.println("3. Quay lại");
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    productsList.sort((o1, o2) -> (int) (o1.getUnitPrice()-o2.getUnitPrice()));
                    displayProductInHomePage();
                    break;
                case 2:
                    productsList.sort((o1, o2) -> (int) (o2.getUnitPrice()-o1.getUnitPrice()));
                    displayProductInHomePage();
                    break;
                case 3:
                    return;
                default:
                    System.err.println(WRONG_CHOICE);
            }
        }
    }


    public void displayProductById() {
        System.out.println("Nhập mã sản phẩm ");
        int productId = InputMethods.getInteger();
        int productIndex = findIndexById(productId);
        if (productIndex == -1){
            System.err.println(PRODUCT_ID_NOTFOUND);
        }else{
            System.out.println("Thông tin sản phẩm");
            productsList.get(productIndex).displayProductForCustomer();
        }
    }
}
