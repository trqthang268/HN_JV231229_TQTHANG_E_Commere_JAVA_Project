package ra.config;

import ra.business.entity.User;
import ra.business.implement.OrderImplement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile {
    public static final String USER_PATH = "src/ra/data/user.txt";
    public static final String CATALOG_PATH = "src/ra/data/catalog.txt";
    public static final String ODER_PATH = "src/ra/data/oder.txt";
    public static final String PRODUCTS_PATH = "src/ra/data/product.txt";

    public static final String USER_LOGIN = "src/ra/data/userLogin.txt";
    public static <T> void writeToFile(String path, List<T> list) {
        File file = new File(path);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos!=null){
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static <T> List<T> readFromFile(String path) {
        List<T> list = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            list = (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }catch (EOFException e) {

        }catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois!=null){
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
//   lấy ra người dùng chưa bị đăng xuất
    public static User readUserActive(){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        User user;
        try {
            fis= new FileInputStream(USER_LOGIN);
            ois = new ObjectInputStream(fis);
            user = (User) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                if (fis != null){
                    fis.close();
                }
                if (ois != null){
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    // cập nhật người dùng userlogin
    public static void writeUserLogin(User user){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = new FileOutputStream(USER_LOGIN);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (fos!=null){
                    fos.close();
                }
                if (oos!= null){
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
