package com.loskon.androidprojectdictaphone.files;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * Работа с файлами и папками
 */

public class FileManager {

    public static File createAudioFile(Date date) throws IOException {
        String path = pathFolderSave();
        File file = new File(path);

        boolean hasFileCreated = hasFolderCreated(file);

        if (hasFileCreated) {
            String name = getNowDateStringForTitle(date) + ".ulaw";
            return new File(path, name);
        } else {
            throw new IOException("Невозможно создать файл");
        }
    }

    private static String pathFolderSave() {
        String docs = Environment.DIRECTORY_DOCUMENTS;
        String pathDocs = Environment.getExternalStoragePublicDirectory(docs).getAbsolutePath();
        String pathSaveFolder = "Dictaphone";
        return pathDocs + File.separator + pathSaveFolder + File.separator;
    }

    private static boolean hasFolderCreated(File file) {
        boolean hasFileCreated = true;

        if (!file.exists()) {
            hasFileCreated = file.mkdirs();
        }

        return hasFileCreated;
    }

    private static String getNowDateStringForTitle(Date date) {
        int dateFormat = DateFormat.SHORT;
        String dateNowString = DateFormat.getDateTimeInstance(dateFormat, dateFormat).format(date);
        return replaceForbiddenCharacters(dateNowString);
    }

    private static String replaceForbiddenCharacters(String dateString) {
        dateString = dateString.replace("/", ".");
        dateString = dateString.replace(":", "_");
        return dateString;
    }

    public static File[] getListFile() {
        File folder = getFolderSave();
        return folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".ulaw"));
    }

    private static File getFolderSave() {
        return new File(pathFolderSave());
    }
}
