
package com.mycompany.extracao;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ExportaCSV implements Exporta{
    //Sobrescrevendo o metodo declarado com a assinatura Salva CSV
    //Diretório default para salvar o arquivo e criar a pasta
    String diretorio = "C:\\Users\\PESSOAL\\Documents\\Teste_Intuitive_Care_Alyson_Trizotto";
    @Override
    public void SalvaTabela1CSV(ArrayList<String[]> Tabela1,ArrayList<String[]> Tabela2 ,ArrayList<String[]> Tabela3) {
         try{
                //Mostrando que estamos escrevendo o arquivo
                System.out.println("Iniciando criação de CSV tabela 1");
                //doretório default
                String CSV_PATH = "C:\\Users\\PESSOAL\\Documents\\Teste_Intuitive_Care_Alyson_Trizotto";
                //Abrindo o diretório
                File file = new File(CSV_PATH);
                //Verificando se a pasta já existe
                if(!file.exists()){
                    //Caso não exista ele cria
                    file.mkdirs();
                }                
            diretorio = CSV_PATH;
            //Passando o nome do preimeiro arquivo
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

            //Passando para o outro método, metodo que cira a tabela 2, passamos como parâmetro a tabela 2 e 3 junto com o diretório
            SalvaTabela2CSV(Tabela2, Tabela3, CSV_PATH);
        }
        catch(Exception e){
            System.out.println("Exception "+e);
        }
         finally{
             System.out.println("\n\nCriação do csv 1 concluido");
         }
    }
    
    @Override
    public void SalvaTabela2CSV(ArrayList<String[]> Tabela2, ArrayList<String[]> Tabela3, String CSV_PATH) {
       try{
           //Mostrando que estamos escrevendo o arquivo
            System.out.println("Iniciando criação de CSV tabela 2");
           //Passando o nome da segunda tabela para co fileWriter
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
            //Passando para o método que salva o terceiro arquivo
            SalvaTabela3CSV(Tabela3, CSV_PATH);
        }
        catch(Exception e){
            System.out.println("Exception "+e);
        }
       finally{
           System.out.println("\n\nSegundo arquivo gerado com sucesso");
       }
    }
    
    @Override
    public void SalvaTabela3CSV(ArrayList<String[]> Tabela3, String CSV_PATH) {
       try{
        //Mostrando que estamos escrevendo o arquivo
            System.out.println("Iniciando criação de CSV");
           //Passando com o paâmetro o nome do terceiro arquivo
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
            }
        catch(Exception e){
            System.out.println("Exception "+e);
        }
       finally{
           System.out.println("\n\nTerceiro arquivo csv criado com sucesso");
       }
    }
}
