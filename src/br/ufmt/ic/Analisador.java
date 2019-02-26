package br.ufmt.ic;

import java.util.ArrayList;
import java.util.List;

public class Analisador {

    private String Codigo;
    private int posicaoAtual = 0;
    private Peex peex = new Peex();
    private List<Token> token = new ArrayList<Token>();
    private int ValorMaximo = 0;

    public Analisador(String codigo) {
        this.Codigo = codigo;
    }

    public void IniciarAnalise() {
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
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaMenorIgual();
                    peex.novoPeex();
                    posicaoAtual = posicaoAtual + 2;
                    break;
                }
                if (catual == '\n') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '=' && cprox == '=') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaigualIgual();
                    peex.novoPeex();
                    posicaoAtual = posicaoAtual + 2;
                    break;
                }
                if (catual == '!' && cprox == '=') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaDiferente();
                    peex.novoPeex();
                    posicaoAtual = posicaoAtual + 2;
                    break;
                }
                if (catual == '>' && cprox == '=') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaMaiorIgual();
                    peex.novoPeex();
                    posicaoAtual = posicaoAtual + 2;
                    break;
                }
                if (catual == '(') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaAbrePartenteses();
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == ')') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaFechaPartenteses();
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '{') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaAbreChaves();
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '}') {
                        AnalisarPalavra(peex);
                    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaFechaChaves();
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '<') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaMenor();
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '>') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaMaior();
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '+') {
                    AnalisarPalavra(peex);
                    AdicionaMais(); //Analisa a palavra adiciona até o parenteses atual
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '-') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaMenos();
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if (catual == '=') {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaAtribuicao();
                    peex.novoPeex();
                    posicaoAtual++;
                    break;
                }
                if ((catual == '*' && cprox != '/') && (catual == '*' && canterior != '/')) {
                    AnalisarPalavra(peex);    //Analisa a palavra adiciona até o parenteses atual
                    AdicionaOperadorMultiplicacao();
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
        System.out.println("Codigo completo : " + peex.palavra);
        System.out.println("Tipo de Token | Valor");
        for (int i = 0; i < token.size(); i++) {
            System.out.println(token.get(i).tipoToken + "---->" + token.get(i).cod);
        }
    }

    public boolean analisarnumero(Peex peex) {
        int numeroMax = peex.palavra.length();
        int i = 0;
        boolean numero = false;
        String numeros = "0123456789";
        char numeroAtual = '0';
        char charAtual = peex.palavra.charAt(0);
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
                        adicionaNumeroInteiro(peex);
                    } else if (analisarnumeroFloat(peex)) {
                        adicionaNumeroFloat(peex);
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
                            adicionaError(peex);
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
                                adicionaError(peex);
                            else
                                adicionarIdentificador(peex);
                        }
                    }
                }
            }
        }
    }

    public void adicionaNumeroInteiro(Peex peex) {
        Token Inteiro;
        Inteiro = new Token(peex.palavra, TipoToken.NumeroInteiro);
        token.add(Inteiro);
    }

    public void adicionaNumeroFloat(Peex peex) {
        Token Float;
        Float = new Token(peex.palavra, TipoToken.NumeroFloat);
        token.add(Float);
    }

    public void AdicionaMenorIgual() {
        Token MenorIgual;
        MenorIgual = new Token("<=", TipoToken.OperadorRelacional);
        token.add(MenorIgual);
    }

    public void adicionarIdentificador(Peex peex) {
        Token Identificador;
        Identificador = new Token(peex.palavra, TipoToken.Identificadores);
        token.add(Identificador);
    }

    public void adicionaError(Peex peex) {
        Token Error;
        Error = new Token(peex.palavra, TipoToken.Error);
        token.add(Error);
    }

    public void AdicionaMaiorIgual() {
        Token MaiorIgual;
        MaiorIgual = new Token(">=", TipoToken.OperadorRelacional);
        token.add(MaiorIgual);
    }

    public void AdicionaDiferente() {
        Token Diferente;
        Diferente = new Token("!=", TipoToken.OperadorRelacional);
        token.add(Diferente);
    }

    public void AdicionaigualIgual() {
        Token IgualIgual;
        IgualIgual = new Token("==", TipoToken.OperadorRelacional);
        token.add(IgualIgual);
    }

    public void AdicionaAtribuicao() {
        Token Atribuicao;
        Atribuicao = new Token("=", TipoToken.OperadordeAtribuicao);
        token.add(Atribuicao);
    }

    public void AdicionaMais() {
        Token Mais;
        Mais = new Token("+", TipoToken.OperadorAritmeticoMais);
        token.add(Mais);
    }

    public void AdicionaMenos() {
        Token Menos;
        Menos = new Token("+", TipoToken.OperadorAritmeticoMenos);
        token.add(Menos);
    }

    public void AdicionaOperadorMultiplicacao() {
        Token Multiplicacao;
        Multiplicacao = new Token("*", TipoToken.Multiplicação);
        token.add(Multiplicacao);
    }

    public void AdicionaMaior() {
        Token Maior;
        Maior = new Token(">", TipoToken.OperadorRelacional);
        token.add(Maior);
    }

    public void AdicionaMenor() {
        Token Menor;
        Menor = new Token("<", TipoToken.OperadorRelacional);
        token.add(Menor);
    }

    public void AdicionaAbrePartenteses() {
        Token AbreParenteses;
        AbreParenteses = new Token("(", TipoToken.AbreParenteses);
        token.add(AbreParenteses);
    }

    public void AdicionaFechaPartenteses() {
        Token FechaParenteses;
        FechaParenteses = new Token(")", TipoToken.FechaParentese);
        token.add(FechaParenteses);
    }

    public void AdicionaAbreChaves() {
        Token AbreChaves;
        AbreChaves = new Token("{", TipoToken.AbreChaves);
        token.add(AbreChaves);
    }

    public void AdicionaFechaChaves() {
        Token FechaChaves;
        FechaChaves = new Token("}", TipoToken.FechaChaves);
        token.add(FechaChaves);
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
