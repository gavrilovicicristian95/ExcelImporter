package com.excel.importer.util;

import com.excel.importer.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.List;

public class UtilFunctions {

    public FileMetadata getMetadataFile(MultipartFile file) throws IOException {
        FileMetadata fileMetadata =  new FileMetadata();

        File convertedFile = convert(file);
        BasicFileAttributes attr = Files.readAttributes(convertedFile.toPath(), BasicFileAttributes.class);
        FileTime time = attr.creationTime();
        Date creationDate = new Date( time.toMillis() );

        fileMetadata.setFilename(file.getOriginalFilename());
        fileMetadata.setSizeFile(String.valueOf(file.getSize()));
        fileMetadata.setCreationDate(creationDate);
        fileMetadata.setUploadDate(new Date());

        return fileMetadata;
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
