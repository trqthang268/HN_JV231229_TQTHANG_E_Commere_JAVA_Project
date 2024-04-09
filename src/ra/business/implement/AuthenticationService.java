package ra.business.implement;

import org.mindrot.jbcrypt.BCrypt;
import ra.business.design.IAuthication;
import ra.business.entity.RoleNameUser;
import ra.business.entity.User;
import ra.config.IOFile;
import ra.config.InputMethods;

import java.util.Comparator;
import java.util.List;

public class AuthenticationService implements IAuthication {
    public static List<User> userList;

    static {
        userList = IOFile.readFromFile(IOFile.USER_PATH);
        User admin = new User(1, "Truong", "Thang", "admin123", BCrypt.hashpw("admin123", BCrypt.gensalt(5)), "admin123@gmail.com", "0987654321", "Số 1, khu A11, Vencam City, Ha Noi", null, RoleNameUser.ROLE_ADMIN, true);
        userList.add(admin);
        IOFile.writeToFile(IOFile.USER_PATH, userList);
    }

    @Override
    public User login(String username, String password) {
        User userLogin = getUserFromUserName(username);
        if (userLogin == null) {
            return null;
        }
        boolean checkLogin = BCrypt.checkpw(password, userLogin.getPassword()); // kiểm tra mật khẩu có khớp hay không(so sánh 2 mật khẩu đã được mã hóa)
        if (checkLogin) { //mật khẩu đúng thì trả về thông tin user
            return userLogin;
        }
        return null; // mật khẩu không đúng thì trả về giá trị null
    }

    private User getUserFromUserName(String username) { //dùng stream duyệt qua userlist tìm username trùng với username nhập vào lúc đăng nhập
//        trả về phần tử đầu tiên tìm đc . không thì trả về già trị null
        return userList.stream().filter(user -> user.getUserName().equals(username)).findFirst().orElse(null);
    }

    @Override
    public void register(User user) { //set những giá trị mặc định cho user khi đăng kí
        user.setUserId(getNewUserId());
        user.setRole(RoleNameUser.ROLE_USER);
        user.setUserStatus(true);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));

        userList.add(user);
        IOFile.writeToFile(IOFile.USER_PATH, userList);
    }

    @Override
    public void displayUserList() {
        System.out.println("Danh sách người dùng");
        for (User user : userList) {
            if (user.getRole() != RoleNameUser.ROLE_ADMIN){
                user.displayData();
                System.out.println("---------------------------");
            }
        }
    }

    @Override
    public void searchUserByName() {
        System.out.println("Nhập tên người dùng muốn tìm");
        String searchName = InputMethods.getString();
        boolean isExist = userList.stream().anyMatch(user -> user.getLastName().equalsIgnoreCase(searchName));
        if (isExist){
            System.out.println("Danh sách người có tên "+searchName+" là:");
            userList.stream().filter(user -> user.getLastName().equalsIgnoreCase(searchName)).forEach(User::displayData);
        }else{
            System.out.println("Không có ai trong danh sách có tên như vậy");
        }
    }

    @Override
    public void changeUserStatus() {
        System.out.println("Nhập mã người dùng muốn sửa đổi trạng thái");
        int changeId = InputMethods.getInteger();
        int changeIdIndex = findIndexById(changeId);
        System.out.println("Trạng thái cũ của người dùng là "+(userList.get(changeIdIndex).isUserStatus()?"Unblock":"Block"));
        userList.get(changeIdIndex).setUserStatus(!userList.get(changeIdIndex).isUserStatus());
        System.out.println("Trạng thái mới của người dùng là "+(userList.get(changeIdIndex).isUserStatus()?"Unblock":"Block"));
    }

    private int getNewUserId() { // tìm int id lớn nhất trong userList. tìm thấy thì cộng thêm 1. k thấy thì trả về giá trị 0
        int maxId = userList.stream().map(user -> user.getUserId()).max(Comparator.naturalOrder()).orElse(0);
        return maxId + 1;
    }
    public int findIndexById(int id){
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId()==id){
                return i;
            }
        }
        return -1;
    }
}
