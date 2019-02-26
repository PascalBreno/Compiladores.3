package br.ufmt.ic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Olá, seja bem vindo ao meu compilador.");
        Scanner ler = new Scanner(System.in);

        System.out.printf("Informe o nome de arquivo texto:\n");
        String nome = ler.nextLine();

        System.out.printf("\nConteúdo do arquivo texto:\n");
        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            String cod = linha;
            cod+='\n';

            while (linha != null) {


                linha = lerArq.readLine(); // lê da segunda até a última linha
                cod+=linha;
                cod+='\n';
            }

            Analisador codigo = new Analisador(cod);
            codigo.IniciarAnalise();
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
    }
}
