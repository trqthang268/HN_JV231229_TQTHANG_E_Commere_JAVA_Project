package ra.business.implement;

import ra.business.design.IProductDesign;
import ra.business.entity.Products;
import ra.config.IOFile;
import ra.config.InputMethods;

import java.util.List;

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
}
