package com.mycompany.extracao;

import java.util.ArrayList;

public interface Exporta {
    //Declarando uma assinatura para a interface
     void SalvaTabela1CSV(ArrayList<String[]> Tabela1,ArrayList<String[]> Tabela2, ArrayList<String[]> Tabela3);
    //Tabela 1 passa para a tabela 2 que passa para a 3
     void SalvaTabela2CSV(ArrayList<String[]> Tabela2,ArrayList<String[]> Tabela3,String Caminho);
     void SalvaTabela3CSV( ArrayList<String[]> Tabela3, String Caminho);
}
