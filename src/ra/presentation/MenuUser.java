package ra.presentation;

import ra.config.InputMethods;
import ra.presentation.usermanagement.*;

public class MenuUser {
    Cart cart = new Cart();
    HomePage homePage = new HomePage();
    MyAccount myAccount = new MyAccount();
    OrderHistory orderHistory = new OrderHistory();
    ProductInfo productInfo = new ProductInfo();
    public void displayMenuUser() {
        while (true){
            System.out.println("===========MENU USER===============");
            System.out.println("1. Trang chủ");
            System.out.println("2. Chi tiết sản phẩm");
            System.out.println("3. Giỏ hàng");
            System.out.println("4. Trang thông tin cá nhân");
            System.out.println("5. Lịch sử đơn hàng");
            System.out.println("6. Đăng xuất");
            System.out.println("Lựa chọn của bạn");
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    homePage.displayHomePage();
                    break;
                case 2:
                    productInfo.displayProductInfoMenu();
                    break;
                case 3:
                    cart.displayCartMenu();
                    break;
                case 4:
                    myAccount.displayAccountMenu();
                    break;
                case 5:
                    orderHistory.displayOrderHistoryMenu();
                    break;
                case 6:
                    return;
                default:
                    System.err.println("Lựa chọn sai. Vui lòng nhập lại!");
            }
        }
    }
}
