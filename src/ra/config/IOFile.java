package ra.config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile {
    public static final String USER_PATH = "src/ra/data/user.txt";
    public static final String CATALOG_PATH = "src/ra/data/catalog.txt";
    public static final String ODER_PATH = "src/ra/data/oder.txt";
    public static final String PRODUCTS_PATH = "src/ra/data/product.txt";
    public static <T> void writeToFile(String path, List<T> list) {
        File file = new File(path);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(path,true);
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
}
