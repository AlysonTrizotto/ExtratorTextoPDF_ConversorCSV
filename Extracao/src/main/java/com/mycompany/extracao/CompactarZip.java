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
        System.out.println("Inicando a compactação do arquivo");
        //Diretório onde vai ficar o arquivo zip
        Path dirZip = Paths.get("C:\\Users\\PESSOAL\\Documents");
        //Diretório onde vai ser feito a letura para o arquivo zip
        Path dirFile = Paths.get("C:\\Users\\PESSOAL\\Documents\\Teste_Intuitive_Care_Alyson_Trizotto");
       //Nome que será salvo o arquivo zip
        Path zipFile = dirZip.resolve("Teste_Intuitive_Care_Alyson_Trizotto.zip");
       //Criando um novo arquivo zip com os dados que serão lidos
        try(ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipFile.toFile()));
            DirectoryStream<Path> stream = Files.newDirectoryStream(dirFile)){
            //Percorrendo a pasta para coletar os arquivos
            for(Path path: stream){
                //Passando o nome do arquivo no formato string
                ZipEntry zipEntry = new ZipEntry(path.getFileName().toString());
                //Preparando a entrada do arquivo
                zip.putNextEntry(zipEntry);
                //Passando a entrada com arquivos
                FileInputStream fileInputStram = new FileInputStream(path.toFile());
                //Crianod um array de bytes para o buffer
                byte[] buff = new byte[2048];
                //Posição de início de leitura e escrita
                int byteRead;
                //Posição de leitura sendo pega pela leitura do burffer onde for maior que zero
                while((byteRead = fileInputStram.read(buff)) > 0 ){
                    //escrevendo o arquivo com os bytes lidos, começando a escrita na posição zero, e passando o os bytes coletados
                    zip.write(buff,0,byteRead);
                }
                //Fechando a entrada zip
                zip.closeEntry();
                //Fechando a entrada de arquivos
                fileInputStram.close();
            }
        }catch(IOException e){
            throw new IOException(e.getMessage());
        }finally{
            System.out.println("Compactação realizada com sucesso");
        }
    }
}
