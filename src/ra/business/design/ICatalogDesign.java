package ra.business.design;

public interface ICatalogDesign {
    void displayAllCatalog();
    void addNewCatalog();
    void searchCatalogByName();
    void editCatalogInfo();
    void changeCatalogStatus();
    int findIndexById(String id);
}
