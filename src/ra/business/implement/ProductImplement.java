package ra.business.implement;

import ra.business.design.IProductDesign;
import ra.business.entity.Catalogs;
import ra.business.entity.Products;
import ra.config.IOFile;
import ra.config.InputMethods;

import java.util.Comparator;
import java.util.List;

import static ra.business.implement.CatalogImplement.catalogsList;
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
            productsList.sort(Comparator.comparing(Products::getCreatedAt)); // sắp xếp theo thời gian thêm mới nhất
            productsList.forEach(Products::displayDataProduct);
            System.out.println("---------------------");
        }
    }

    @Override
    public void addNewProducts() {
        System.out.println("Nhập số lượng sản phẩm muốn thêm");
        int countNewProduct = InputMethods.getInteger();
        for (int i = 0; i < countNewProduct; i++) {
            System.out.println("Nhập thông tin cho sản phẩm thứ "+(i+1));
            Products products = new Products();
            products.inputDataProduct(true);
            productsList.add(products);
        }
        IOFile.writeToFile(IOFile.PRODUCTS_PATH,productsList);
        System.out.println("Đã thêm mới thành công");
    }

    @Override
    public void editInfoProduct() {
        System.out.println("Nhập mã sản phẩm muốn chỉnh sửa :");
        int editId = InputMethods.getInteger();
        int editIdIndex = findIndexById(editId);
        if (editIdIndex == -1){
            System.err.println("Không tìm thấy sản phẩm chứa mã này");
        }else{
            System.out.println("Thông tin cũ của sản phẩm");
            productsList.get(editIdIndex).displayDataProduct();
            System.out.println("Nhập thông tin mới cho sản phẩm");
            productsList.get(editIdIndex).inputDataProduct(false);
            System.out.println("Cập nhật thông tin thành công");
            IOFile.writeToFile(IOFile.PRODUCTS_PATH,productsList);
        }

    }

    @Override
    public void changeStatusById() {
        System.out.println("Nhập mã sản phẩm muốn sửa trạng thái");
        int changeStatusId = InputMethods.getInteger();
        int statusIdIndex = findIndexById(changeStatusId);
        System.out.println("Trạng thái cũ của sản phẩm là "+(productsList.get(statusIdIndex).isProductStatus()?"Đang bán":"Ngưng bán"));
        productsList.get(statusIdIndex).setProductStatus(!productsList.get(statusIdIndex).isProductStatus());
        System.out.println("Trạng thái mới của sản phẩm là "+(productsList.get(statusIdIndex).isProductStatus()?"Đang bán":"Ngưng bán"));
        IOFile.writeToFile(IOFile.PRODUCTS_PATH,productsList);
    }

    @Override
    public void searchProductByName() {
        System.out.println("Nhập tên sản phẩm cần tìm");
        String searchName  = InputMethods.getString();
        boolean isExist = productsList.stream().anyMatch(products -> products.getProductName().equalsIgnoreCase(searchName));
        if (isExist){
            System.out.println("Sản phẩm cần tìm :");
            productsList.stream().filter(products -> products.getProductName().equalsIgnoreCase(searchName)).forEach(Products::displayDataProduct);
        }else{
            System.out.println("Không có sản phẩm cần tìm");
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
                    System.out.println("-----------------------------");
                }
            }
        }else {
            System.out.println("Không có sản phẩm cần tìm");
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
        if(catalogImplement.findIndexbyId(catalogId) == -1){
            System.err.println("Mã danh mục không tồn tại");
        }else {
            System.out.println("Danh sách sản phẩm của danh mục trên :");
            for (Products products : productsList) {
                if (products.getCategoryId().equals(catalogId)) {
                    products.displayProductForCustomer();
                    System.out.println("-----------------------------------------");
                }
            }
        }
    }

    public void displayProductInHomePage() {
        System.out.println("Danh sách toàn bộ sản phẩm :");
        for (Products products : productsList) {
            if (products.isProductStatus() && checkStatusCatalogById(products.getCategoryId())){
                products.displayProductForCustomer();
                System.out.println("-----------------------------");
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
                    System.err.println("Vui lòng nhập 1 hoặc 2.");
            }
        }
    }

    public void displayProductById() {
        System.out.println("Nhập mã sản phẩm ");
        int productId = InputMethods.getInteger();
        int productIndex = findIndexById(productId);
        if (productIndex == -1){
            System.err.println("Mã sản phẩm không tồn tại");
        }else{
            System.out.println("Thông tin sản phẩm");
            productsList.get(productIndex).displayDataProduct();
        }
    }
}
