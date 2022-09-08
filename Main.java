package com.company;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static StringBuilder log = new StringBuilder();

    public static void main(String[] args) throws IOException {

        String src = "src";
        String res = "res";
        String savegames = "savegames";
        String temp = "temp";
        createFolder(src);
        createFolder(res);
        createFolder(savegames);
        createFolder(temp);
        String main = "src/main";
        String test = "src/test";
        createFolder(main);
        createFolder(test);
        String drawables = "res/drawables";
        String vectors = "res/vectors";
        String icons = "res/icons";
        createFolder(drawables);
        createFolder(vectors);
        createFolder(icons);

        String mainFile = "Main.java";
        String utils = "Utils.java";
        createFile(mainFile, main);
        createFile(utils, main);
        String tempFile = "temp.txt";
        createFile(tempFile, temp);

        try (FileWriter writer = new FileWriter("games/temp/temp.txt")) {
            writer.write(String.valueOf(log));
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        GameProgress saveGame1 = new GameProgress(100, 90, 10, 250);
        GameProgress saveGame2 = new GameProgress(50, 40, 40, 150);
        GameProgress saveGame3 = new GameProgress(90, 80, 60, 50);

        String game1 = "Games/savegames/saveGame1";
        String game2 = "Games/savegames/saveGame2";
        String game3 = "Games/savegames/saveGame3";
        saveGame(saveGame1, game1 + ".dat");
        saveGame(saveGame2, game2 + ".dat");
        saveGame(saveGame3, game3 + ".dat");

        String sg1 = "saveGame1";
        String sg2 = "saveGame2";
        String sg3 = "saveGame3";
        zipFiles(game1 + ".zip", game1 + ".dat", sg1 + ".dat");
        zipFiles(game2 + ".zip", game2 + ".dat", sg2 + ".dat");
        zipFiles(game3 + ".zip", game3 + ".dat", sg3 + ".dat");


    }

    public static void createFolder(String nameFolder) {
        File folder = new File("Games/" + nameFolder);
        if (folder.mkdir()) {
            log.append("Folder ").append(nameFolder).append(" created").append("\n");
        } else {
            log.append("Folder ").append(nameFolder).append(" don't created").append("\n");
        }
    }

    public static void createFile(String nameFile, String nameFolder) {
        File file = new File("Games/" + nameFolder + "/" + nameFile);
        try {
            if (file.createNewFile())
                log.append("File ").append(nameFile).append(" created").append("\n");
        } catch (IOException ex) {
            System.out.println("Error creating file: " + ex.getMessage());
        }
    }

    public static void saveGame(GameProgress gameProgress, String file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles (String pathToZip, String pathToFile, String nameFile) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathToZip));
             FileInputStream fis = new FileInputStream(pathToFile)) {
            ZipEntry entry = new ZipEntry(nameFile);
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
            File removeFile = new File(pathToFile);
            removeFile.delete();
    }


}

