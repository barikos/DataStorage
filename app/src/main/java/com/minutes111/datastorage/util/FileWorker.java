package com.minutes111.datastorage.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.minutes111.datastorage.Const;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by barikos on 11.05.16.
 */
public class FileWorker {

    public boolean writeFile(Context context, String fileName){
        if (!isStorageStateAvailable()){
            return false;
        }
        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath().toString() + Const.DIR_NAME);
        if (!dir.mkdir()){
            Log.d(Const.LOG_TAG, "Dir not created");
        }
        File file = new File(dir,fileName + Const.FILE_EXT);
        if (!file.exists()){
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(Const.BASE_CONTENT.getBytes());
                Toast.makeText(context, String.format("File %s was created",fileName + Const.FILE_EXT), Toast.LENGTH_SHORT).show();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "File has already exist", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    public boolean writeFile(Context context, String fileName,String content){
        if (!isStorageStateAvailable()){
            return false;
        }
        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath().toString() + Const.DIR_NAME);

        File file = new File(dir,fileName + Const.FILE_EXT);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            Toast.makeText(context, String.format("File %s was changed",fileName + Const.FILE_EXT), Toast.LENGTH_SHORT).show();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String readFile(Context context, String fileName){
        StringBuilder text = new StringBuilder();
        if (isStorageStateAvailable()){
            File sdcard = Environment.getExternalStorageDirectory();
            File dir = new File(sdcard.getAbsolutePath().toString() + Const.DIR_NAME);
            File file = new File(dir,fileName);

            if (file.exists()){
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine())!= null){
                        text.append(line);
                        text.append('\n');
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return text.toString();
    }

    public String[] getArrayFileNames(Context context){
        if (isStorageStateAvailable()) {
            String path = Environment.getExternalStorageDirectory().toString() + Const.DIR_NAME;
            final File file = new File(path);
            File[] files = file.listFiles();
            String[] fileNames = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileNames[i] = files[i].getName();
            }
            return fileNames;
        } else {
            Toast.makeText(context, "Storage isn't available", Toast.LENGTH_SHORT).show();
        }
        return new String[0];
    }

    private boolean isStorageStateAvailable(){
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
