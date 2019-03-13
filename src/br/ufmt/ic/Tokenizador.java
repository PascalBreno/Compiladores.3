package br.ufmt.ic;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import javax.swing.*;


public class Tokenizador extends JFrame {

    private String Codigo;
    private int posicaoAtual = 0;
    private Peex peex = new Peex();
    public List<Token> token = new ArrayList<Token>();
    private int ValorMaximo = 0;

    public Tokenizador(String codigo) {
        this.Codigo = codigo;
    }  // Iniciar o Tokenizador aderindo o código lido.

    public void CriarTokens() {
        char catual, cprox = ' ', canterior;
        peex.novoPeex();
        this.ValorMaximo = Codigo.length() - 1;
        while (posicaoAtual < ValorMaximo) {
            retirarEspaco(Codigo);
            do {
                // Recebe o char atual e o seguinte.
                catual = Codigo.charAt(posicaoAtual);
                if (posicaoAtual == 0)
                    canterior = Codigo.charAt(posicaoAtual);
                else
                    canterior = Codigo.charAt(posicaoAtual - 1);
                if (posicaoAtual + 1 < ValorMaximo)                  //Aqui estou verificando se o próximo valor é maior que o tamanho da String
                    cprox = Codigo.charAt(posicaoAtual + 1);          // Isso evita erro de pegar um valor que não tem na string
                else
                    cprox = Codigo.charAt(posicaoAtual);
                if (peex.palavra.equals("//")) {
                    analisarComentariobarra(peex);
                    break;
                }
                if (catual == ' ') {
                    AnalisarPalavra(peex);
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '<' && cprox == '=') {
                    AnalisarPalavra(peex);
                    AdicionarToken("<=", TipoToken.OperadorRelacional); //Analisa a palavra adiciona até o parenteses atual
                    peex.novoPeex();
                    posicaoAtual = posicaoAtual + 2;
                    break;
                }
                if (catual == '\n') {
                    AnalisarPalavra(peex);    //Analiza a palavra lida até o simbolo de pular Linha
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '=' && cprox == '=') {
                    AnalisarPalavra(peex);
                    AdicionarToken("==", TipoToken.OperadorRelacional);
                    peex.novoPeex();
                    posicaoAtual = posicaoAtual + 2;
                    break;
                }
                if (catual == '!' && cprox == '=') {
                    AnalisarPalavra(peex);
                    AdicionarToken("!=", TipoToken.OperadorRelacional);
                    peex.novoPeex();
                    posicaoAtual = posicaoAtual + 2;
                    break;
                }
                if (catual == '>' && cprox == '=') {
                    AnalisarPalavra(peex);
                    AdicionarToken(">=", TipoToken.OperadorRelacional);
                    peex.novoPeex();
                    posicaoAtual = posicaoAtual + 2;
                    break;
                }
                if (catual == '(') {
                    AnalisarPalavra(peex);
                    AdicionarToken("(", TipoToken.AbreParenteses);
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == ')') {
                    AnalisarPalavra(peex);
                    AdicionarToken(")", TipoToken.FechaParentese);
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '{') {
                    AnalisarPalavra(peex);
                    AdicionarToken("{", TipoToken.AbreChaves);
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '}') {
                    AnalisarPalavra(peex);

                    AdicionarToken("}", TipoToken.FechaChaves);
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '<') {
                    AnalisarPalavra(peex);
                    AdicionarToken("<", TipoToken.OperadorRelacional);
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '>') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionarToken(">", TipoToken.OperadorRelacional);
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '+') {
                    AnalisarPalavra(peex);
                    AdicionarToken("+", TipoToken.OperadorAritmeticoMais);
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '-') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionarToken("-", TipoToken.OperadorAritmeticoMenos);
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '=') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionarToken("=", TipoToken.OperadordeAtribuicao);

                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if ((catual == '*' && cprox != '/') && (catual == '*' && canterior != '/')) {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionarToken("*", TipoToken.Multiplicação);
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                peex.palavra += catual;        //Adiciona ao Peex o ultimo caracter
                if (catual == ' ')
                    break;
                if (peex.palavra.equals("int ") || peex.palavra.equals("int")) {
                    analisarInt(peex, Codigo, catual, cprox);
                }


                if (peex.palavra.equals("/*")) {
                    analisarComentario(peex, Codigo, catual, cprox);
                    break;
                }
                //analisarcodigo(catual, cprox);
                posicaoAtual++;
            } while (posicaoAtual < ValorMaximo);
            peex.novoPeex();
        }
    }

    public boolean analisarnumero(Peex peex) {
        int numeroMax = peex.palavra.length();
        int i = 0;
        boolean numero = false;
        String numeros = "0123456789";
        char numeroAtual = '0';
        char charAtual;
        do {
            for (int x = 0; x < 10; x++) {
                numeroAtual = numeros.charAt(x);
                charAtual = peex.palavra.charAt(i);
                if (charAtual == ' ')
                    break;
                if (charAtual == numeroAtual) {
                    numero = true;
                    break;
                } else {
                    numero = false;
                }
            }
            if (numero != true)
                return false;
            i++;
        } while (i < numeroMax);
        return numero;
    }

    public boolean analisarnumeroFloat(Peex peex) {
        int numeroMax = peex.palavra.length();
        int i = 0;
        boolean numero = false;
        String numeros = "0123456789.";
        char numeroAtual = '0';
        char charAtual = peex.palavra.charAt(0);
        do {
            for (int x = 0; x < 11; x++) {
                numeroAtual = numeros.charAt(x);
                charAtual = peex.palavra.charAt(i);
                if (charAtual == ' ')
                    break;
                if (charAtual == numeroAtual) {
                    numero = true;
                    break;
                } else {
                    numero = false;
                }
            }
            if (numero != true)
                return false;
            i++;
        } while (i < numeroMax);
        return numero;
    }

    public void AnalisarPalavra(Peex peex) {
        char catual = Codigo.charAt(posicaoAtual);
        char cprox = ' ';
        if (peex.palavra.equals("if")) {
            analisarIF(peex);
        } else {
            if (posicaoAtual + 1 < Codigo.length()) {
                cprox = Codigo.charAt(posicaoAtual + 1);
            } else {
                cprox = Codigo.charAt(posicaoAtual);
            }
            //Aqui irei analisar a palavra que foi inserida antes dos operadores de CHAR
            if (!(peex.palavra.equals("")) && !(peex.palavra.equals(" "))) {
                if (peex.palavra.equals("int ") || peex.palavra.equals("int")) {
                    analisarInt(peex, Codigo, catual, cprox);
                }
                if (!(peex.palavra.equals(" ")) && !(peex.palavra.equals("\n"))) {
                    if (analisarnumero(peex)) {
                        AdicionarToken(peex.palavra, TipoToken.NumeroInteiro);
                    } else if (analisarnumeroFloat(peex)) {
                        AdicionarToken(peex.palavra, TipoToken.NumeroFloat);
                    } else {
                        String alfabetoError = "0123456789!@#$%&;,./[]^~:|";
                        String simbolosError = "!@#$%&;,./[]^~:|";
                        boolean error = true;
                        for (int i = 0; i < 26; i++) {
                            if (peex.palavra.charAt(0) == alfabetoError.charAt(i)) {
                                error = true;
                                break;
                            } else {
                                error = false;
                            }
                        }
                        if (error) {
                            AdicionarToken("Error", TipoToken.Error);
                        } else {
                            for (int x = 0; x < peex.palavra.length(); x++) {
                                for (int y = 0; y < 16; y++) {
                                    if (peex.palavra.charAt(x) == simbolosError.charAt(y)) {
                                        error = true;
                                        break;
                                    } else {
                                        error = false;
                                    }
                                }
                            }
                            if (error)
                                AdicionarToken("Error", TipoToken.Error);
                            else
                                AdicionarToken(peex.palavra, TipoToken.Identificadores);
                        }
                    }
                }
            }
        }
    }

    public void AdicionarToken(String string_Token, TipoToken tipoToken) {
        Token newtoken;
        newtoken = new Token(string_Token, tipoToken);
        token.add(newtoken);
    }

    public void analisarIF(Peex peex) {
        Token palavrareservadaIF;
        palavrareservadaIF = new Token(peex.palavra, TipoToken.PalavraReservadaIF);
        token.add(palavrareservadaIF);
    }

    public void analisarComentariobarra(Peex peex) {
        String comentario = "";
        posicaoAtual++;
        char catual = ' ';
        //Ignorar Comentário
        do {
            catual = Codigo.charAt(posicaoAtual);
            if ((catual == '\n'))
                break;
            comentario += catual;
            posicaoAtual++;
        } while ((catual != '\n'));
        posicaoAtual++;
        peex.novoPeex();
        //Adicionando o token de comentário na lista
        Token coment;
        coment = new Token(comentario, TipoToken.Comentario);
        token.add(coment);
    }

    public void analisarComentario(Peex peex, String codigo, char catual, char cprox) {
        String comentario = "";
        posicaoAtual++;
        //Ignorar Comentário
        do {
            catual = codigo.charAt(posicaoAtual);
            cprox = codigo.charAt(posicaoAtual + 1);
            if (((catual == '*') && (cprox == '/')))
                break;
            comentario += catual;
            posicaoAtual++;
        } while (!((catual == '*') && (cprox == '/')));

        posicaoAtual = posicaoAtual + 2;
        peex.novoPeex();
        //Adicionando o token de comentário na lista
        Token coment;
        coment = new Token(comentario, TipoToken.Comentario);
        token.add(coment);
    }

    public void analisarInt(Peex peex, String codigo, char catual, char cprox) {
        Token integer;
        integer = new Token(peex.palavra, TipoToken.Integer);
        token.add(integer);
        peex.novoPeex();
        //Adicionando o token de comentário na lista
    }

    public void retirarEspaco(String codigo) {
        char catual = codigo.charAt(this.posicaoAtual);
        if (catual == ' ') {
            do {
                catual = codigo.charAt(this.posicaoAtual);
                this.posicaoAtual++;
            } while (catual != ' ');
        }
    }
}
