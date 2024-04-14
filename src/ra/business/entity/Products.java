package ra.business.entity;

import ra.config.IOFile;
import ra.config.InputMethods;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static ra.business.implement.CatalogImplement.catalogsList;
import static ra.business.implement.ProductImplement.productsList;
import static ra.config.Alert.WRONG_CHOICE;
import static ra.presentation.adminmanagement.CatalogManagement.catalogImplement;

public class Products implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int productId;
    private String productName;
    private String categoryId;
    private String description;
    private double unitPrice;
    private int stock;
    private LocalDate createdAt;
    private LocalDate updateAt;
    private boolean productStatus;

    public Products() {
    }

    public Products(int productId, String productName, String categoryId, String description, double unitPrice, int stock, LocalDate createdAt, LocalDate updateAt, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.productStatus = productStatus;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }
    public void inputDataProduct(boolean isAdd){
        if (isAdd) {
            this.productId = inputProductId();
            this.productStatus = true;
            this.createdAt = LocalDate.now();
        }
        this.productName = inputProductName(productsList);
        this.categoryId = inputCatalogId(catalogsList);
        this.description = inputDescription();
        this.unitPrice = inputUnitPrice();
        this.stock = inputStock();
        if (!isAdd){
            System.out.println("Nhập trạng thái sản phẩm");
            this.productStatus = InputMethods.getBoolean();
            this.updateAt = LocalDate.now();
        }
    }

    public String inputProductName(List<Products> productsList) {
        while (true){
            System.out.println("Nhập tên sản phẩm");
            String productNameInput = InputMethods.getString();
            boolean isExist = productsList.stream().anyMatch(products -> products.getProductName().equals(productNameInput));
            if (isExist){
                System.err.println("Tên sản phẩm đã tồn tại. Vui lòng nhập lại!");
            }else{
                return productNameInput;
            }
        }
    }

    public int inputProductId() {
        int maxId = productsList.stream().map(Products::getProductId).max(Comparator.naturalOrder()).orElse(0);
        return maxId+1;
    }
    public String inputCatalogId(List<Catalogs> catalogsList){
        if (catalogsList.isEmpty()){
            System.err.println("Danh sách danh mục trống.");
            return null;
        }
        System.out.println("=====Danh sách danh mục=====");
        for (int i = 0; i < catalogsList.size(); i++) {
            System.out.printf("| STT : %-3d | Tên : %-30s |\n",(i+1),catalogsList.get(i).getCatalogName());
        }
        while(true) {
            System.out.println("=====Lựa chọn của bạn : =====");
            int choice = InputMethods.getInteger();
            if (choice > 0 && choice <= catalogsList.size()) {
                return catalogsList.get(choice - 1).getCatalogId();
            } else {
                System.err.println(WRONG_CHOICE);
                System.out.println("Thêm danh mục mới không?");
                System.out.println("1. Có");
                System.out.println("2. Không");
                byte choice1 = InputMethods.getByte();
                switch (choice1){
                    case 1 :
                        catalogImplement.addNewCatalog();
                        inputCatalogId(catalogsList);
                        break;
                    default:
                        return null;
                }

            }
        }
    }
    public String inputDescription(){
        System.out.println("Nhập mô tả sản phẩm");
        return InputMethods.getString();
    }

    public double inputUnitPrice(){
        while(true) {
            System.out.println("Nhập đơn giá");
            double unitPriceInput = InputMethods.getDouble();
            if (unitPriceInput > 0) {
                return unitPriceInput;
            } else {
                System.err.println("Đơn giá phải có giá trị lớn hơn 0. Vui lòng nhập lại! ");
            }
        }
    }

    public int inputStock(){
        while (true){
            System.out.println("Nhập số lượng trong kho");
            int stockInput = InputMethods.getInteger();
            if (stockInput >0){
                return stockInput;
            }else{
                System.err.println("Số lượng trong kho phải có giá trị lớn hơn 0. Vui lòng nhập lại!");
            }
        }
    }

    public void displayDataProduct(){ // thông tin admin xem
        System.out.printf("| Mã sp : %d | Tên sp : %s | Danh mục : %s | Mô tả : %s | Đơn giá : %f | Số lượng : %d | Thời gian tạo : %s | Thời gian cập nhật gần nhất : %s | Trạng thái : %s |\n",productId,productName,categoryId,description,unitPrice,stock,createdAt,updateAt,productStatus?"Đang bán":"Ngưng bán");
        System.out.println("---------------------------------------------");
    }

    public void displayProductForCustomer(){ // thông tin khách hàng xem
        System.out.printf("| Mã sản phẩm : %d | Tên sản phẩm : %s | Danh mục : %s | Mô tả : %s | Đơn giá : %f |\n",productId,productName,categoryId,description,unitPrice);
        System.out.println("---------------------------------------------");
    }
}
