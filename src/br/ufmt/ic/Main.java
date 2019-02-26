package br.ufmt.ic;

public class Main {

    public static void main(String[] args) {
        System.out.println("Olá, seja bem vindo ao meu compilador.");
        String cod = "if (a9 < b) /* operacao relacional */\n" +
                "{\n" +
                " soma = 4.9 + 0.5 // atribuicao\n" +
                " i = i * 1\n" +
                " }\n";
        Analisador codigo = new Analisador(cod);
        codigo.IniciarAnalise();
    }


}
