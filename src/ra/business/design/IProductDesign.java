package ra.business.design;

public interface IProductDesign {
//    hiển thị danh sách sản phẩm
    void displayAllProducts();
//    thêm mới 1 hoặc nhiều sản phẩm
    void addNewProducts();
//    chỉnh sửa thông tin sản phẩm
    void editInfoProduct();
//    ẩn sản phẩm theo mã sản phẩm
    void changeStatusById();
//    tìm kiếm sản phẩm theo tên
    void searchProductByName();

    void displayProductById();

    void displayProductByPrice();

    void displayProductInHomePage();

    void displayProductByCatalog();

    void displayTop10Products();

    void findProductInHomePage();

    int findIndexById(int id);
}
