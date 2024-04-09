package ra.business.implement;

import ra.business.design.IOderDesign;
import ra.business.entity.Order;
import ra.config.IOFile;

import java.util.List;

public class OrderImplement implements IOderDesign {
    public static List<Order> orderList;
    static {
        orderList = IOFile.readFromFile(IOFile.ODER_PATH);
    }
}
