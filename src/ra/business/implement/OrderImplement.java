package ra.business.implement;

import ra.business.design.IOderDesign;
import ra.business.entity.*;
import ra.config.IOFile;
import ra.config.InputMethods;

import java.util.ArrayList;
import java.util.List;

import static ra.business.implement.AuthenticationService.userList;
import static ra.business.implement.ProductImplement.productsList;
import static ra.config.Alert.ORDER_ID_NOTFOUND;

public class OrderImplement implements IOderDesign {
    public static List<Order> orderList;

    static {
        orderList = IOFile.readFromFile(IOFile.ODER_PATH);
    }

    @Override
    public void displayWaitingOrder() { //Hiển thị danh sách hóa đơn chưa xác nhận
        int total = (int) orderList.stream().filter(order -> order.getOrderStatus().equals(OrderStatus.STATUS_WAITING)).count();
        System.out.printf("Danh sách hóa đơn chưa xác nhận (%d) : \n", total);
        for (Order order : orderList) {
            if (order.getOrderStatus().equals(OrderStatus.STATUS_WAITING)) {
                order.displayOrderData();
            }
        }
    }

    @Override
    public void displaySuccessOrder() { //Hiển thị danh sách hóa đơn đã xác nhận
        int total = (int) orderList.stream().filter(order -> order.getOrderStatus().equals(OrderStatus.STATUS_SUCCESS)).count();
        System.out.printf("Danh sách hóa đơn đã xác nhận (%d) : \n", total);
        for (Order order : orderList) {
            if (order.getOrderStatus().equals(OrderStatus.STATUS_SUCCESS)) {
                order.displayOrderData();
            }
        }
    }

    @Override
    public void changeStatusOrder() { //Xác nhận hóa đơn đang chờ
        System.out.println("Nhập mã hóa đơn");
        long inputOrderId = InputMethods.getLong();
        int indexOrder = findIndexOrderById(inputOrderId);
        if (indexOrder == -1) {
            System.out.println(ORDER_ID_NOTFOUND);
        } else {
            if (orderList.get(indexOrder).getOrderStatus().equals(OrderStatus.STATUS_WAITING)) {
                orderList.get(indexOrder).setOrderStatus(OrderStatus.STATUS_SUCCESS);
                System.out.println("Đã xác nhận hóa đơn đang chờ");
                IOFile.writeToFile(IOFile.ODER_PATH, orderList);
            } else {
                System.out.println("Trạng thái của hóa đơn hiện không ở trại thái chờ");
            }
        }
    }

    private int findIndexOrderById(long id) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void cancelOrder() { // hủy đơn hàng nếu trạng thái của nó không phải là đã giao hàng hoặc hoàn tiền
        System.out.println("Nhập mã hóa đơn");
        long inputOrderId = InputMethods.getLong();
        int indexOrder = findIndexOrderById(inputOrderId);
        if (indexOrder == -1) {
            System.out.println(ORDER_ID_NOTFOUND);
        } else {
            if (orderList.get(indexOrder).getOrderStatus().equals(OrderStatus.STATUS_WAITING)) {
                orderList.remove(orderList.get(indexOrder));
                IOFile.writeToFile(IOFile.ODER_PATH, orderList);
            } else {
                System.out.println("Đơn hàng hiện tại không thể hủy do đã xác nhận hoặc đang được giao");
            }
        }
    }

    @Override
    public void oldOrderUser(User user) {
        System.out.println("Lịch sử đơn hàng");
        boolean isExist = orderList.stream().anyMatch(order -> order.getUserId() == user.getUserId());
        if (isExist) {
            for (Order order : orderList) {
                if (order.getUserId() == user.getUserId()) {
                    order.displayOrderData();
                }
            }
        } else {
            System.out.println("Lịch sử đơn hàng trống");
        }
    }

    @Override
    public void getOrder(User user) throws NullPointerException { // lấy dữ liệu từ cart của user để thiết lập order
        List<CartItem> cartItems = user.getCart(); // lấy cart của user hiện tại
        if (cartItems == null || cartItems.isEmpty()) {
            System.err.println("Giỏ hàng trống");
        } else {
            List<OrderDetail> orderDetails = new ArrayList<>(); // tạo list orderDetail mới
            Order newOrder = new Order(); // tạo đổi tượng order mới
            for (CartItem cartItem : cartItems) { // chuyển dữ liệu từ cartItem sang orderDetail tương ứng
                OrderDetail newOrderDetail = new OrderDetail();
                newOrderDetail.setProductId(cartItem.getProductId());
                newOrderDetail.setQuantity(cartItem.getQuantity());
                newOrderDetail.setProductName(getNameProductById(cartItem.getProductId()));
                newOrderDetail.setUnitPrice(getUnitPriceById(cartItem.getProductId()));
                orderDetails.add(newOrderDetail);
            }
            user.setCart(null); //xóa dữ liệu cart của user
            double newTotal = 0; // tính tổng tiền thông qua đơn gía và số lượng trong orderDetail
            for (OrderDetail orderDetail : orderDetails) {
                newTotal += (orderDetail.getUnitPrice() * orderDetail.getQuantity());
            }
            newOrder.setOderDetails(orderDetails); // gán list oderDetail vào đối tượng order mới
            newOrder.setUserId(user.getUserId()); // gán mã ng dùng trong order từ ng dùng đăng nhập
            newOrder.setTotal(newTotal); //gán tổng tiền
            newOrder.inputDataOrder(); // nhập thông tin trong đơn hàng
            orderList.add(newOrder);
            cartItems = new ArrayList<>();
            user.setCart(cartItems);
            for (int i = 0; i < userList.size(); i++) { // cập nhật lưu người dùng hiện tại vào danh sách người dùng
                if (userList.get(i).getUserId() == user.getUserId()) {
                    userList.set(i, user);
                }
            }
            IOFile.writeToFile(IOFile.ODER_PATH, orderList);
            IOFile.writeToFile(IOFile.USER_PATH, userList);
            System.out.println("Đặt hàng thành công");
        }
    }

    public String getNameProductById(long id) { // lấy tên sản phẩm từ mã sp
        for (Products products : productsList) {
            if (products.getProductId() == id) {
                return products.getProductName();
            }
        }
        return null;
    }

    public double getUnitPriceById(long id) { // lấy đơn giá sp từ mã sp
        for (Products products : productsList) {
            if (products.getProductId() == id) {
                return products.getUnitPrice();
            }
        }
        return -1;
    }

}
