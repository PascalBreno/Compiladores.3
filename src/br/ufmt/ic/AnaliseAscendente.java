package br.ufmt.ic;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AnaliseAscendente extends Tokenizador {
    public List<String> Pilha = new ArrayList<String>();         //Essa é a pilha de que será inicializada
    public List<Integer> Estados = new ArrayList<Integer>();
    public Integer Estado = 0;

    public AnaliseAscendente(String codigo) {
        super(codigo);
    }

    public void tabela() {
        System.out.println("Tabela:");
        Pilha.add("$");
        Token Final;
        Final = new Token("$", TipoToken.Final);
        token.add(Final);
        Estados.add(0);
        String codigo = "";
        Tabela tabela = new Tabela(codigo);
        for (int i = 0; i < token.size(); i++) {
            AnalisarCod_Tabela(tabela, i, codigo);
        }
        System.out.println(codigo);
        /*
        L->En    {print(E.val); }
        E->E1+T  {E.val = E1.val + Tval;}
        E->T     {E.val = T.val;}
        T->T1*F  {T.val=T1.valxF.val;}
        T->F     {T.val=F.val;}
        F->(E)   {F.val = E.val;}
        F-> digit{F.val = digit.lexval;}
        */
    }

    public void AnalisarCod_Tabela(Tabela tabela, int i, String codigo) {
        //Aqui irá acumular o código retirado dos tokens da tabela.
        Integer Estado = Estados.get(Estados.size()-1);//Pega o ultimo valor do Estado
        codigo = tabela.gerarTabela(Estado, token.get(i));
        AnalisarSimbolo(codigo, i);
    }
    public void ImprimirTabeladeEstados() {
        String codigo = "";
        for (int i = 0; i < Estados.size(); i++) {
            codigo += "\n" + Estados.get(i);
        }
        System.out.println(codigo);
    }
    public void ImprimirTabeladeSimbolos() {
        String codigo = "";
        for (int i = 0; i < Pilha.size(); i++) {
            codigo += "\n" + Pilha.get(i);
        }
        System.out.println(codigo);
    }

    public void ImprimirTokens() {
        String codigo = "";
        for (int i = 0; i < token.size(); i++) {
            codigo += "\n\t" + token.get(i).tipoToken + "----> " + token.get(i).cod + "\n";
        }
        JOptionPane.showMessageDialog(null, codigo, "Tokens", JOptionPane.INFORMATION_MESSAGE);
    }

    public void AnalisarSimbolo(String codigo, int i) {
        if (codigo.charAt(0) == 'S') {
            Pilha.add(token.get(i).cod);
            String palavra = "";
            palavra += codigo.charAt(1);
            Estados.add(Integer.parseInt(palavra));
            ImprimirTabeladeSimbolos();
            ImprimirTabeladeEstados();
        } else if (codigo.charAt(0) == 'R') {

            String palavra = "";
            palavra += codigo.charAt(1);
            this.Estado = Integer.parseInt(palavra);
        } else {
            System.out.println("Erro na operação.\nArgumento Inválido.");
        }

    }
}
