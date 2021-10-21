package com.mycompany.extracao;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class Extracao {
          
    public static void main(String[] args) throws IOException, Exception {
        //Diretório de onde está salvo o arquivo.
        String DiretorioArquivo = "C:\\padrao_tiss_componente_organizacional_202108.pdf";
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
                    //Chamando o bloco  responsável pelo extrator, onde são armazenado os dados.
                    Coleta(DiretorioArquivo); 
                }else{
                    System.out.println("Diretório inválido, encerrando aplicação");
                }
                break;
            case 2:
                System.out.println("Para fazer uso do diretório default vocÊ precisa deixar o arquivo em "
                        + "'C:\' com o nome de " + DiretorioArquivo);
                System.out.println("Aperte qualquer tecla para continuar");
                scan.next();
                //Chamando o bloco responsável pelo extrator e pelo armazenamento dos dadso.
                Coleta(DiretorioArquivo);
                
                break;
            default:
                System.out.print("Opção inválida, encerrando operação.");
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } 
    }
    
  
    public static void Coleta(String Diretorio) throws IOException, Exception {
        try{        
        //Criando Arrays
        ArrayList<String[]> objTableList;
        ArrayList<String[]> objTableList1;
        ArrayList<String[]> objTableList2;
        System.out.println("Iniciando coleta");
        
        //Coletando a primeira tabela passando (diretorio, pagina inicial)
        objTableList = ExtraiQuadro1(Diretorio, 107);
        //Coletando a segunda tabela passando (diretorio, pagina inicial e página final)
        objTableList1 = ExtraiQuadro2(Diretorio, 108, 113);
        //Coletando a terceira tabela passando (diretorio, pagina inicial)
        objTableList2 = ExtraiQuadro3(Diretorio, 113);
    
        //Instanciando a classe responsável por exportar os dados para csv
        ExportaCSV proj = new ExportaCSV();

        //Chamando e passando Passando todas as tabelas
        proj.SalvaTabela1CSV(objTableList,objTableList1,objTableList2);
       
        //Instânciando a classe responsável por compactar o arquivo em .zip
        CompactarZip compactaArq = new CompactarZip();
        compactaArq.Compactar();
        }catch(IOException e){
            throw new IOException(e.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
       
    }
    
    public static ArrayList<String[]> ExtraiQuadro1(String Caminho, int pageNoStart) {
        //Instanciando um arrayList para receber os dados
        ArrayList<String[]> objArrayList = new ArrayList<>();
        try {
            System.out.println("\n\nInciando extração da Primeira tabela\n\n");
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
                
                //Nesta variável é armazenado as linhas e colunas
                String Coluna1 = null;
                //Criando um array para pegar os dados coletados
                ArrayList<String> lineArr = new ArrayList<String>();
               
                //Controlando as linhas que serão coletadas a partir da altura que se encontram
                for(int y = 300; y < 380; y=y+10){
                    //Limpado a variável
                    Coluna1 = null;
                    
                    //Posicionando o primeiro retânguro para coleta dos dados 
                    Rectangle rec = new Rectangle(96,y,100,10);
                    //Adicionando a posição
                    stripper.addRegion("class1", rec);
                    //Extraindo a página
                    stripper.extractRegions(document.getPage(pageNoStart));
                   //Posicionando o Segundo retânguro para coleta dos dados 
                    Rectangle rec2 = new Rectangle(200,y,150,10);
                    //Adicionando a posição
                    stripper.addRegion("class2", rec2);
                    //Extraindo a página
                    stripper.extractRegions(document.getPage(pageNoStart));
                    //Extriando a primeira coluna
                    Coluna1 = stripper.getTextForRegion("class1");
                    //Extraindo a seugnda coluna
                    Coluna1 += stripper.getTextForRegion("class2");
                    //Adicionaod o array
                    lineArr.add(Coluna1);
                }
                //Percorrendo o Array com os dados coletados e separando,
                //e passando para o objetoArray que será Retornado
                //Para ser salvo
                for(String lineAr : lineArr){
                        objArrayList.add(lineAr.split(","));
                }
               
            }
        } 
        catch (Exception e) {
            System.out.println("Exception " +e);
        }
        finally{
            System.out.println("\n\nPrimeira tabela coletada com sucesso.\n\n");
        }
            return objArrayList;
    }
    
    public static ArrayList<String[]> ExtraiQuadro2(String Caminho, int pageNoStart, int pageNoEnd) {
        //Instanciando um arrayList para receber os dados
        ArrayList<String[]> objArrayList = new ArrayList<>();
        try {
            System.out.println("\n\nInciando extração da Segunda tabela\n\n");
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
                //Variavel onde será armazenado os dados coletados de cada linha
                String Coluna1 = null;
                 //Criando um array para pegar os dados coletados
                ArrayList<String> lineArr = new ArrayList<String>();
                //Controlando a página atual
                int pageNow = pageNoStart;
                //Executa enquando a página final não chegar
                while(pageNow <= pageNoEnd){
                    //Controla a posição de onde será coletado os dados. no caso da página final
                    int yMax = 750;
                    //Se chegar na página final diminui a área
                    if(pageNow == 113){
                        yMax = 320;
                    }
                    //PErcorre linha por linha delimitando a posição de cada linha
                    for(int y = 150; y < yMax; y=y+10){
                         //Limpando as linhas já armazenadas   
                        Coluna1 = null;
                        //Criando a primeira área de coleta
                        Rectangle rec = new Rectangle(96,y,100,10);
                        //Adicionando a região code coleta
                        stripper.addRegion("class1", rec);
                        //Extraindo a página para coleta
                        stripper.extractRegions(document.getPage(pageNow));
                        //Adicionando a segunda área de coleta
                        //distancia, altura, largura quadrado, altura do quadrado
                        Rectangle rec2 = new Rectangle(180,y,350,10);
                        //Adicionando a segunda região
                        stripper.addRegion("class2", rec2);
                        //Extraindo a página para coleta
                        stripper.extractRegions(document.getPage(pageNow));
                        //Extraindo a primeira coluna
                        Coluna1 = stripper.getTextForRegion("class1");
                        //Extraindo a segunda coluna
                        Coluna1 += stripper.getTextForRegion("class2");
                        //Passando as rows coletadas para o array
                        lineArr.add(Coluna1);
                    }
                    //Direcionando para a próxima página
                    pageNow++;
                }
                //Passando linha por linha para o objArray e separando por vírgula
                for(String lineAr : lineArr){
                    objArrayList.add(lineAr.split(","));
                }               
            }
        } 
        catch (Exception e) {
            System.out.println("Exception " +e);
        }        
        finally{
            System.out.println("\n\nSegunda tabela coletada com sucesso.\n\n");
        }        
            return objArrayList;
    }
    
    
    public static ArrayList<String[]> ExtraiQuadro3(String Caminho, int pageNoStart) {
        //Instanciando um arrayList para receber os dados
        ArrayList<String[]> objArrayList = new ArrayList<>();
        try {
            System.out.println("\n\nIniciando extraçã da terceira tabela\n\n");
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
                //Váriavel responsável por armazenar as linhas
                String Coluna1 = null;
                //Array que armazena os dados coletados dentro do for
                ArrayList<String> lineArr = new ArrayList<String>();
                //Coletando linha por linha com o delimititador de distância
                for(int y = 460; y < 510; y=y+10){
                    //Limpando a variável que armazena as rows
                    Coluna1 = null;
                    //Instâncianod primeira área
                    Rectangle rec = new Rectangle(96,y,70,10);
                    //Adicionando a área ao région
                    stripper.addRegion("class1", rec);
                    //Extraindo a página
                    stripper.extractRegions(document.getPage(pageNoStart));
                    //Instãnciando segunda área
                    Rectangle rec2 = new Rectangle(166,y,150,10);
                    //Adicionando ao région
                    stripper.addRegion("class2", rec2);
                    //extraindo a página
                    stripper.extractRegions(document.getPage(pageNoStart));
                    //Colatando as colunas 1 e 2
                    Coluna1 = stripper.getTextForRegion("class1");
                    Coluna1 += stripper.getTextForRegion("class2");
                    //Salvando no array
                    lineArr.add(Coluna1);
                }
                //Percorrendo linha por linha e salvando no obj
                for(String lineAr : lineArr){
                            
                        objArrayList.add(lineAr.split(","));
                }
               
            }
        } 
        catch (Exception e) {
            System.out.println("Exception " +e);
        }
        finally{
            System.out.println("\n\nTerceira tabela coletada com sucesso\n\n");
        }
         return objArrayList;
    }
}
