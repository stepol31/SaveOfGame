import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<File> games = List.of(
                new File(String.valueOf(new GameProgress(100, 4, 29, 5))),
                new File(String.valueOf(new GameProgress(50, 10, 15, 8))),
                new File(String.valueOf(new GameProgress(120, 8, 35, 7))));

        saveGame("C://Games//savegames//save.dat", games);
        zipFiles("C://Games//savegames//zip.zip", "C://Games//savegames//save.dat", games);
        deleteFiles("C://Games//savegames//save.dat", games);
    }

    public static void deleteFiles(String filepath, List<File> games) {
        for (int i = 0; i < games.size(); i++) {
            File file = new File(filepath + (i + 1));
            if (file.delete()) {
                System.out.println("Файл " + file.getName() + " удален");
            } else {
                System.out.println("Файл не удален");
            }
        }
    }

    public static void zipFiles(String zipPath, String filePath, List<File> games) {

        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(zipPath))) {
            for (int i = 0; i < games.size(); i++) {
                FileInputStream fis = new FileInputStream(filePath + (i + 1));
                ZipEntry entry = new ZipEntry("save" + (i + 1) + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveGame(String filePath, List<File> games) {
        for (int i = 0; i < games.size(); i++) {
            try (FileOutputStream fos = new FileOutputStream(filePath + (i + 1));
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(games.get(i));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

