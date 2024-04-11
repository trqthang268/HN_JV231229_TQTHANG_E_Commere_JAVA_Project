package ra.business.design;

import ra.business.entity.User;

public interface IOderDesign {
void displayWaitingOrder();
void displaySuccessOrder();
void changeStatusOrder();
void cancelOrder();
void oldOrderUser(User user);
void getOrder(User user);

}
