package com.finaltask.networkofgiving.service.interfaces;

import com.finaltask.networkofgiving.data.CustomFile;

import java.io.FileNotFoundException;
import java.util.List;

public interface IFileService {

    List<CustomFile> getAllFiles();
    CustomFile getFile(String filename) throws FileNotFoundException;
}
