package com.finaltask.networkofgiving.service;

import com.finaltask.networkofgiving.data.CustomFile;
import com.finaltask.networkofgiving.service.interfaces.IFileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotAuthorizedException;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class FileService implements IFileService {

    @Override
    public List<CustomFile> getAllFiles(){

        List<CustomFile> images = new ArrayList<CustomFile>();
        String currentDirectory = new File(".").getAbsolutePath();
        File fileFolder = new File(currentDirectory + "/images");
        if(fileFolder.exists()) {
            for (final File file : fileFolder.listFiles()) {
                if (!file.isDirectory()) {
                    String image = getBase64File(file);
                    if (image != null) {
                        images.add(new CustomFile(file.getName(),image));
                    }
                }
            }
        }
        return images;
    }

    @Override
    public CustomFile getFile(String filename){

        String currentDirectory = new File(".").getAbsolutePath();
        File fileFolder = new File(currentDirectory + "/images");
        if(!fileFolder.equals(null)) {
            for (final File file : fileFolder.listFiles()) {
                if (!file.isDirectory() && compareFilename(file, filename)) {
                    String image = getBase64File(file);
                    if(image != null){
                        return new CustomFile(filename, image);
                    }
                }
            }
        }
        return new CustomFile(filename, "");
    }

    private String getBase64File(File file){
        String encodeBase64 = null;
        try {
            String extension = FilenameUtils.getExtension(file.getName());
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            encodeBase64 = Base64.getEncoder().encodeToString(bytes);
            fileInputStream.close();
            String image = "data:image/" + extension + ";base64," + encodeBase64;
            return image;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean compareFilename(File file, String filename){
        return FilenameUtils.removeExtension(filename).equals(FilenameUtils.removeExtension(file.getName()));
    }
}
