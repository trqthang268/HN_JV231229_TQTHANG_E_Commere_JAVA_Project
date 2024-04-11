package ra.business.entity;

import ra.config.InputMethods;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static ra.business.implement.CatalogImplement.catalogsList;

public class Catalogs implements Serializable {
    private static final long serialVersionUID = 1L;
    private String catalogId;
    private String catalogName;
    private String description;
    private boolean catalogStatus;
    private LocalDateTime createdAt;

    public Catalogs() {
    }

    public Catalogs(String catalogId, String catalogName, String description, boolean catalogStatus, LocalDateTime createdAt) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.description = description;
        this.catalogStatus = catalogStatus;
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCatalogStatus() {
        return catalogStatus;
    }

    public void setCatalogStatus(boolean catalogStatus) {
        this.catalogStatus = catalogStatus;
    }
    public void inputDataCatalog(boolean isAdd){
        if (isAdd) {
            this.catalogId = inputCatalogId(catalogsList);
            this.catalogStatus = true;
        }
        this.catalogName = inputCatalogName(catalogsList);
        System.out.println("Nhập mô tả danh mục");
        this.description = InputMethods.getString();
        if(!isAdd){
            System.out.println("Nhập trạng thái danh mục");
            this.catalogStatus = InputMethods.getBoolean();
        }
        this.createdAt = LocalDateTime.now();
    }

    private String inputCatalogId(List<Catalogs> catalogsList) {
        while (true){
            System.out.println("Nhập mã danh mục");
            String catalogId = InputMethods.getString();
            String idRegex = "^[C][A-Za-z0-9]{3}$";
            if (catalogId.matches(idRegex)){ // đúng định dạng
                boolean isExist = catalogsList.stream().anyMatch(catalogs -> catalogs.getCatalogId().equals(catalogId));
                if (isExist){ // trùng id
                    System.err.println("Mã danh mục đã được sử dụng. Vui lòng nhập mã khác");
                }else{ // không trùng id
                    return catalogId;
                }
            }else{ // sai định dạng
                System.err.println("Mã danh mục không đúng đinh dạng C___ . Vui lòng nhập lại!");
            }
        }
    }

    private String inputCatalogName(List<Catalogs> catalogsList) {
        while(true){
            System.out.println("Nhập tên danh mục");
            String catalogName = InputMethods.getString();
            boolean isExist = catalogsList.stream().anyMatch(catalogs -> catalogs.getCatalogName().equals(catalogName));
            if (isExist){ // trùng tên
                System.err.println("Tên danh mục đã được sử dụng. Vui lòng nhập tên danh mục khác.");
            }else{ //không trùng tên
                return catalogName;
            }
        }
    }

    public void displayData() {
        System.out.printf("|Mã danh mục : %-4s | Tên : %-30s | Mô tả : %s | Trạng thái : %s |\n",catalogId,catalogName,description,catalogStatus?"Hoạt động":"Ẩn hoạt động");
        System.out.println("---------------------------------------------");
    }

}
