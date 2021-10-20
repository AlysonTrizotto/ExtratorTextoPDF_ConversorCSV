
package com.mycompany.extracao;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ExportaCSV implements Exporta{
    //Sobrescrevendo o metodo declarado com a assinatura Salva CSV
    String diretorio = "C:\\Users\\PESSOAL\\Documents/AlysonTrizotto";
    @Override
    public void SalvaTabela1CSV(ArrayList<String[]> Tabela1,ArrayList<String[]> Tabela2 ,ArrayList<String[]> Tabela3, String CSV_PATH) {
        try{
            //Mostrando que estamos escrevendo o arquivo
                System.out.println("Iniciando criação de CSV");
                
                CSV_PATH += "C:\\Users\\PESSOAL\\Documents/AlysonTrizotto";
                File file = new File(CSV_PATH);
                if(!file.exists()){
                    file.mkdirs();
                }
                diretorio = CSV_PATH;
                CSV_PATH += "/Tabela1 CSV";
                //Falando para o FileWriter que vamos  criar um arquivo no diretorio desejado
                FileWriter fw = new FileWriter(CSV_PATH);
                //Chamando o CSVWriter para criar o arquivo no diretorio passado para o FileWriter
                CSVWriter cw = new CSVWriter(fw);
                //Passando os dados para escrita
                cw.writeAll(Tabela1);
               //Fechando o csv
                cw.close();
                //fechando o fw
                fw.close();
                //Dizendo que terminamos
                
                SalvaTabela2CSV(Tabela2, Tabela3, CSV_PATH);
        }
        catch(Exception e){
            System.out.println("Exception "+e);
        }
    }
    
    @Override
    public void SalvaTabela2CSV(ArrayList<String[]> Tabela2, ArrayList<String[]> Tabela3, String CSV_PATH) {
       try{
            //Mostrando que estamos escrevendo o arquivo
                System.out.println("Iniciando criação de CSV");
                Scanner scan = new Scanner(System.in);
               
                 
                CSV_PATH = diretorio+"/Tabela2 CSV";
                //Falando para o FileWriter que vamos  criar um arquivo no diretorio desejado
                FileWriter fw = new FileWriter(CSV_PATH);
                //Chamando o CSVWriter para criar o arquivo no diretorio passado para o FileWriter
                CSVWriter cw = new CSVWriter(fw);
                //Passando os dados para escrita
                cw.writeAll(Tabela2);
               //Fechando o csv
                cw.close();
                //fechando o fw
                fw.close();
                //Dizendo que terminamos
                
                SalvaTabela3CSV(Tabela3, CSV_PATH);
        }
        catch(Exception e){
            System.out.println("Exception "+e);
        }
    }
    
    @Override
    public void SalvaTabela3CSV(ArrayList<String[]> Tabela3, String CSV_PATH) {
       try{
            //Mostrando que estamos escrevendo o arquivo
                System.out.println("Iniciando criação de CSV");
               
               
                CSV_PATH = diretorio + "/Tabela3 CSV"; 
                //Falando para o FileWriter que vamos  criar um arquivo no diretorio desejado
                FileWriter fw = new FileWriter(CSV_PATH);
                //Chamando o CSVWriter para criar o arquivo no diretorio passado para o FileWriter
                CSVWriter cw = new CSVWriter(fw);
                //Passando os dados para escrita
                cw.writeAll(Tabela3);
               //Fechando o csv
                cw.close();
                //fechando o fw
                fw.close();
                //Dizendo que terminamos
                System.out.print("Escrita de arquivo finalizado");
                System.out.print("Inciando compactação do arquivo");
                
                
        }
        catch(Exception e){
            System.out.println("Exception "+e);
        }
       
    }
    
}
