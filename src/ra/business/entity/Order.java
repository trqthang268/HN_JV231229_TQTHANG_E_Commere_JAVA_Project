package ra.business.entity;

import ra.config.InputMethods;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static ra.business.implement.OrderImplement.orderList;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long orderId;
    private long userId;
    private String nameReceive;
    private String receivePhoneNumber;
    private String receiveAddress;
    private double total;
    private OrderStatus orderStatus;
    private List<OrderDetail> oderDetails;
    private LocalDateTime orderAt;
    private LocalDateTime deliverAt;

    public Order() {
    }

    public Order(long orderId, long userId, String nameReceive, String receivePhoneNumber, String receiveAddress, double total, OrderStatus orderStatus, List<OrderDetail> oderDetails, LocalDateTime orderAt, LocalDateTime deliverAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.nameReceive = nameReceive;
        this.receivePhoneNumber = receivePhoneNumber;
        this.receiveAddress = receiveAddress;
        this.total = total;
        this.orderStatus = orderStatus;
        this.oderDetails = oderDetails;
        this.orderAt = orderAt;
        this.deliverAt = deliverAt;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNameReceive() {
        return nameReceive;
    }

    public void setNameReceive(String nameReceive) {
        this.nameReceive = nameReceive;
    }

    public String getReceivePhoneNumber() {
        return receivePhoneNumber;
    }

    public void setReceivePhoneNumber(String receivePhoneNumber) {
        this.receivePhoneNumber = receivePhoneNumber;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderDetail> getOderDetails() {
        return oderDetails;
    }

    public void setOderDetails(List<OrderDetail> oderDetails) {
        this.oderDetails = oderDetails;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

    public LocalDateTime getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(LocalDateTime deliverAt) {
        this.deliverAt = deliverAt;
    }

    public void inputDataOrder(){
        this.orderId = inputOrderId(orderList);
        //this.userId = inputUserId();
        this.nameReceive = inputReceiveName();
        this.receivePhoneNumber = inputReceivePhone();
        this.receiveAddress = inputReceiveAddress();
        //this.total += orderDetail.get(n).getUnitPrice * orderDetail.get(n).getQuantity;
        this.orderStatus = OrderStatus.STATUS_WAITING;
        //orderDetails = List<OrderDetail> thông tin orderdetail được cung cấp bới cartItem của người dùng đang đăng nhập
        this.orderAt = LocalDateTime.now();
        this.deliverAt = this.getOrderAt().plusDays(4);//tối thiểu 4 ngày sau đặt hàng
    }

    public long inputOrderId(List<Order> orderList) {
        long maxOrderId = orderList.stream().map(order -> order.getOrderId()).max(Comparator.naturalOrder()).orElse(0L);
        return maxOrderId+1;
    }
     public String inputReceiveName(){
         System.out.println("Nhập tên người nhận");
        return InputMethods.getString();
     }

     public String inputReceivePhone(){
        while (true) {
            System.out.println("Nhập số điện thoại người nhận hàng");
            String receivePhone = InputMethods.getString();
            String phoneRegex = "((^(\\+84|84|0|0084){1})([35789]))+([0-9]{8})$";
            if (receivePhone.matches(phoneRegex)){
                return receivePhone;
            }else{
                System.err.println("Số điện thoại không đúng định dạng vui lòng nhập lại");
            }
        }
     }

     public String inputReceiveAddress(){
         System.out.println("Nhập địa chỉ giao hàng :");
         return InputMethods.getString();
     }

     public void displayOrderData(){
        String orderstatus = null;
        switch (orderStatus){
            case STATUS_SUCCESS -> orderstatus = "Success";
            case STATUS_WAITING -> orderstatus = "Waitting";
            case STATUS_DELIVERING -> orderstatus = "Delivering";
            default -> System.out.println("Trạng thái không hợp lệ");
        }
         System.out.printf("| Mã hóa đơn : %d | Mã người mua : %d | Tên người nhận : %s | SDT người nhận : %s | Địa chỉ người nhận : %s |\n",orderId,userId,nameReceive,receivePhoneNumber,receiveAddress);
         System.out.printf("| Tổng tiền : %f | Trạng thái đơn hàng : %s | Danh sách chi tiết : %s |\n",total,orderstatus,oderDetails.toString());
         System.out.printf("| Ngày đặt hàng : %s | Ngày dự kiến giao hàng : %s",orderAt.toString(),deliverAt.toString());
         System.out.println("---------------------------------------------");
     }

}
