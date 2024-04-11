package ra.business.entity;

import java.io.Serializable;

public class CartItem implements Serializable {
    private long productId;
    private int quantity;

    public CartItem() {
    }

    public CartItem(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void displayCartItemData(){
        System.out.printf("| Mã sản phẩm : %d | Số lượng : %d |\n",productId,quantity);
        System.out.println("---------------------------------------------");
    }
}
