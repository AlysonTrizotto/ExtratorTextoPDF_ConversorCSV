
package com.mycompany.extracao;


import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class Extracao {
    //Criando fariavel estatica para o diretorio
    private static final String CSV_PATH = "D:\\Arquivo Gerado CSV";
    
    public static void main(String[] args) throws IOException {
        //Criando Arrays
        ArrayList<String[]> objTableList;
        ArrayList<String[]> objTableList1;
        ArrayList<String[]> objTableList2;
        
        //Coletando a primeira tabela passando (diretorio, pagina inicial, pagina final, quantidade de colunas
        objTableList = readParaFromPDF("C:\\Padrão_TISS_Componente_Organizacional_202103.pdf", 78,79,2);
        //Coletando a segunda tabela
        objTableList1 = readParaFromPDF("C:\\Padrão_TISS_Componente_Organizacional_202103.pdf", 79,84,2);
        //Coletando a terceira tabela
        objTableList2 = readParaFromPDF("C:\\Padrão_TISS_Componente_Organizacional_202103.pdf", 85,86,2);
        
        //Chamando o Escritor csv
        SalvaCSV(objTableList, objTableList1, objTableList2);
        
    }
    public static ArrayList<String[]> readParaFromPDF(String pdfPath, int pageNoStart, int pageNoEnd, int noOfColumnsInTable) {
        //Instanciando um arrayList para receber os dados
        ArrayList<String[]> objArrayList = new ArrayList<>();
        try {
            //Abrindo o arquivo
            PDDocument document = PDDocument.load(new File(pdfPath));
            //PEgando a classe do arquivo com seus dados
            document.getClass();
            //Verifgicando se o pdf não é encripografado
            if (!document.isEncrypted()) {
                //Instanciando o StripperByArea
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                //Libeando a posição
                stripper.setSortByPosition(true);              
                //Instanciando o Stripper
                PDFTextStripper tStripper = new PDFTextStripper();
                //Passando as páginas inicial & final
                tStripper.setStartPage(pageNoStart);
                tStripper.setEndPage(pageNoEnd);
                //Buscando o texto e passando para a variavel
                String pdfFileInText = tStripper.getText(document);
                //USando o split para separar 
                String Documentlines[] = pdfFileInText.split("\\r?\\n");
                //PErcorrendo o texto
                for (String line : Documentlines) {
                    //MOntando um vetor com a quantidade de linhas encontradas
                    String lineArr[] = line.split("\\s+");
                    //Verificando se é de se é coluna ou tabela e pegando a Linha
                    if (lineArr.length == noOfColumnsInTable) {
                        //PEgando a linha
                        for (String linedata : lineArr) {
                            //PEgando a linha e unindo com a coluna
                            System.out.print(linedata + "             ");
                        }
                        System.out.println("");
                        objArrayList.add(lineArr);
                    }
                }
            }
        } 
        catch (Exception e) {
            System.out.println("Exception " +e);
        }
        
            return objArrayList;
    }
    
    protected static void SalvaCSV(ArrayList<String[]> Tabela1, ArrayList<String[]> Tabela2, ArrayList<String[]> Tabela3){
        try{
            //Mostrando que estamos escrevendo o arquivo
                System.out.println("Iniciando criação de CSV");
                //Falando para o FileWriter que vamos  criar um arquivo no diretorio desejado
                FileWriter fw = new FileWriter(CSV_PATH);
                //Chamando o CSVWriter para criar o arquivo no diretorio passado para o FileWriter
                CSVWriter cw = new CSVWriter(fw);
                //Passando os dados para escrita
                cw.writeAll(Tabela1);
                cw.writeAll(Tabela2);
                cw.writeAll(Tabela3);
               //Fechando o csv
                cw.close();
                //fechando o fw
                fw.close();
                //Dizendo que terminamos
                System.out.print("Escrita de arquivo finalizado");
        }
        catch(Exception e){
            System.out.println("Exception "+e);
        }
    }
}
