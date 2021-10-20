package com.mycompany.extracao;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class Extracao {
    //Criando fariavel 
    private static String CSV_PATH = "D:\\Arquivo Gerado CSV";   
    
    public static void main(String[] args) throws IOException {
       
       CompactarZip compactaArq = new CompactarZip();
        String dirSaida = "D:/AlysonTrizotto.zip"; 
        //dir += "/AlysonTrizotto/*"; 
        compactaArq.Compactar();
       
        String DiretorioArquivo = "C:\\padrao_tiss_componente_organizacional_202108.pdf";
        String dir = "";
        try {
            
        System.out.println("Deseja fazer uso do diretório default ou deseja inserir um diretório?");
        System.out.println("PAra inserir tecle 1, para usar dafault tecle 2");
        Scanner scan = new Scanner(System.in);
        byte x = scan.nextByte();
        switch(x){
            case 1:
                //Pegando o diretório
                System.out.println("Qual o diretório onde o arquivo se encontra?"); 
                    
                DiretorioArquivo = scan.next();
                if(DiretorioArquivo != ""){
                    dir = Coleta(DiretorioArquivo); 
                    
                    throw new IOException("Erro de input/output");
                    
                }else{
                    System.out.println("Diretório inválido, encerrando aplicação");
                }
                break;
            case 2:
                System.out.println("Para fazer uso do diretório default vocÊ precisa deixar o arquivo em "
                        + "'C:\' com o nome de " + DiretorioArquivo);
                System.out.println("Tecle 'S' para continuar");
                scan.next();
                dir = Coleta(DiretorioArquivo);
                
                break;
            default:
                System.out.print("Opção inválida, encerrando operação.");
        }
//        CompactarZip compactaArq = new CompactarZip();
//        String dirSaida = "D:/AlysonTrizotto.zip"; 
//        dir += "/AlysonTrizotto/*"; 
//        compactaArq.Compactar(dirSaida, dir);
   
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } 
    }
    
  
    public static String Coleta(String Diretorio) {
        String dir = "";
        //Criando Arrays
        ArrayList<String[]> objTableList;
        ArrayList<String[]> objTableList1;
        ArrayList<String[]> objTableList2;
        System.out.println("Iniciando coleta");
        //Coletando a primeira tabela passando (diretorio, pagina inicial, pagina final, quantidade de colunas
        objTableList = ExtraiQuadro1(Diretorio, 107);
        //Coletando a segunda tabela
        objTableList1 = ExtraiQuadro2(Diretorio, 108, 113);
        //Coletando a terceira tabela
        objTableList2 = ExtraiQuadro3(Diretorio, 113);
        //Instanciando a classe projeto
        ExportaCSV proj = new ExportaCSV();
        Scanner scan = new Scanner(System.in);
//        System.out.println("Qual o diretório para salvar o arquivo? O nome do arquivo será Extração CSV");
//        dir = scan.next();
        //Chamando e passando os parâmetros para a escrita do arquivo
        proj.SalvaTabela1CSV(objTableList,objTableList1,objTableList2, dir);
        return dir;
    }
    
    public static ArrayList<String[]> ExtraiQuadro1(String Caminho, int pageNoStart) {
        //Instanciando um arrayList para receber os dados
        ArrayList<String[]> objArrayList = new ArrayList<>();
        try {
            //Abrindo o arquivo
            PDDocument document = PDDocument.load(new File(Caminho));
            //PEgando a classe do arquivo com seus dados
            document.getClass();
            //Verifgicando se o pdf não é encripografado
            if (!document.isEncrypted()) {
               
                //Instanciando o StripperByArea
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                //Libeando a posição
                stripper.setSortByPosition(true);
                String Coluna1 = null;
                
                ArrayList<String> lineArr = new ArrayList<String>();
                int Linhas = 0;
                for(int y = 300; y < 380; y=y+10){
                    Linhas++;
                    Coluna1 = null;
                    
                    Rectangle rec = new Rectangle(96,y,100,10);
                    stripper.addRegion("class1", rec);
                    stripper.extractRegions(document.getPage(pageNoStart));
                    Rectangle rec2 = new Rectangle(200,y,150,10);
                    stripper.addRegion("class2", rec2);
                    stripper.extractRegions(document.getPage(pageNoStart));
                    Coluna1 = stripper.getTextForRegion("class1");
                    Coluna1 += stripper.getTextForRegion("class2");
                    lineArr.add(Coluna1);
                    
                    
                    System.out.println(lineArr);
                    
                }
                for(String lineAr : lineArr){
                            
                        objArrayList.add(lineAr.split(","));
                }
               
            }
        } 
        catch (Exception e) {
            System.out.println("Exception " +e);
        }
        
            return objArrayList;
    }
    
    public static ArrayList<String[]> ExtraiQuadro2(String Caminho, int pageNoStart, int pageNoEnd) {
        //Instanciando um arrayList para receber os dados
        ArrayList<String[]> objArrayList = new ArrayList<>();
        try {
            //Abrindo o arquivo
            PDDocument document = PDDocument.load(new File(Caminho));
            //PEgando a classe do arquivo com seus dados
            document.getClass();
            //Verifgicando se o pdf não é encripografado
            if (!document.isEncrypted()) {
               
                //Instanciando o StripperByArea
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                //Libeando a posição
                stripper.setSortByPosition(true);
                String Coluna1 = null;
                
                ArrayList<String> lineArr = new ArrayList<String>();
                int Linhas = 0;
                int pageNow = pageNoStart;
                while(pageNow <= pageNoEnd){
                    int yMax = 750;
                    if(pageNow == 113){
                        yMax = 320;
                    }
                    for(int y = 150; y < yMax; y=y+10){
                            Linhas++;
                            Coluna1 = null;

                            Rectangle rec = new Rectangle(96,y,100,10);
                            stripper.addRegion("class1", rec);
                            stripper.extractRegions(document.getPage(pageNow));
                            //distancia, altura, largura quadrado, altura do quadrado
                            Rectangle rec2 = new Rectangle(180,y,350,10);
                            stripper.addRegion("class2", rec2);
                            stripper.extractRegions(document.getPage(pageNow));
                            Coluna1 = stripper.getTextForRegion("class1");
                            Coluna1 += stripper.getTextForRegion("class2");
                            lineArr.add(Coluna1);
                            System.out.println(lineArr);
                    }
                    pageNow++;
                }
                for(String lineAr : lineArr){
                    objArrayList.add(lineAr.split(","));
                }               
                
            }
        } 
        catch (Exception e) {
            System.out.println("Exception " +e);
        }
        
            return objArrayList;
    }
    
    
    public static ArrayList<String[]> ExtraiQuadro3(String Caminho, int pageNoStart) {
        //Instanciando um arrayList para receber os dados
        ArrayList<String[]> objArrayList = new ArrayList<>();
        try {
            //Abrindo o arquivo
            PDDocument document = PDDocument.load(new File(Caminho));
            //PEgando a classe do arquivo com seus dados
            document.getClass();
            //Verifgicando se o pdf não é encripografado
            if (!document.isEncrypted()) {
               
                //Instanciando o StripperByArea
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                //Libeando a posição
                stripper.setSortByPosition(true);
                String Coluna1 = null;
                
                ArrayList<String> lineArr = new ArrayList<String>();
                int Linhas = 0;
                for(int y = 460; y < 510; y=y+10){
                    Linhas++;
                    Coluna1 = null;
                    
                    Rectangle rec = new Rectangle(96,y,70,10);
                    stripper.addRegion("class1", rec);
                    stripper.extractRegions(document.getPage(pageNoStart));
                    Rectangle rec2 = new Rectangle(166,y,150,10);
                    stripper.addRegion("class2", rec2);
                    stripper.extractRegions(document.getPage(pageNoStart));
                    Coluna1 = stripper.getTextForRegion("class1");
                    Coluna1 += stripper.getTextForRegion("class2");
                    lineArr.add(Coluna1);
                    
                    
                    System.out.println(lineArr);
                    
                }
                for(String lineAr : lineArr){
                            
                        objArrayList.add(lineAr.split(","));
                }
               
            }
        } 
        catch (Exception e) {
            System.out.println("Exception " +e);
        }
        
            return objArrayList;
    }

}
