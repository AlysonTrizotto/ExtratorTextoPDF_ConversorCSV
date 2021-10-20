package com.mycompany.extracao;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompactarZip {
    static final int TAMANHO_BUFFER = 4096; //4kb
    
    public static void Compactar() throws IOException{
        Path dirZip = Paths.get("C:\\Users\\PESSOAL\\Documents");
        Path dirFile = Paths.get("D:/AlysonTrizotto");
        Path zipFile = dirZip.resolve("alysonTrizotto.zip");
        try(ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipFile.toFile()));
            DirectoryStream<Path> stream = Files.newDirectoryStream(dirFile)){
            
            for(Path path: stream){
                ZipEntry zipEntry = new ZipEntry(path.getFileName().toString());
                zip.putNextEntry(zipEntry);
                FileInputStream fileInputStram = new FileInputStream(path.toFile());
                byte[] buff = new byte[2048];
                int byteRead;
                while((byteRead = fileInputStram.read(buff)) > 0 ){
                    zip.write(buff,0,byteRead);
                }
                zip.closeEntry();
                fileInputStram.close();
            }
        
        }catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }
}
