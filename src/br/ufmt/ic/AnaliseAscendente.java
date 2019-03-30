package br.ufmt.ic;

import java.util.ArrayList;
import java.util.List;

public class AnaliseAscendente extends Tokenizador {
    public List<String> Simbolos = new ArrayList<String>();         //Essa é a pilha de que será inicializada
    public List<Integer> Estados = new ArrayList<Integer>();
    public Integer Passo = 1;
    int tokenActual = 0;

    public AnaliseAscendente(String codigo) {
        super(codigo);
    }

    public void tabela() {
        Token newtoken;
        newtoken = new Token("$", TipoToken.Final);
        token.add(newtoken);
        Estados.add(0);
        Goto(Estados.get(Estados.size() - 1), token.get(tokenActual)); //Irei avaliar o estado e o tipo da entrada atual.
    }

    public void Goto(int Estado, Token token) {
        String Retorno = VerificarTabela(Estado, token);
        if (Retorno.equals("acc")) {
            imprimirPasso(Retorno);
            System.out.println("ok");
        }
        if (Retorno.charAt(0) == 'S') {
            Simbolos.add(token.cod);
            Estados.add(Character.getNumericValue(Retorno.charAt(1)));
            this.tokenActual++;
            imprimirPasso(Retorno);

            Goto(Estados.get(Estados.size() - 1), this.token.get(tokenActual));
        }
        if (Retorno.charAt(0) == 'R') {

            int tamReducao = TamanhoRed(Character.getNumericValue(Retorno.charAt(1)));
            do {
                Simbolos.remove(Simbolos.size() - 1);
                Estados.remove(Estados.size() - 1);
                tamReducao--;
            } while (tamReducao > 0);   //Aqui remove o tamanho de estados que serão removidos .
            String charRed = regra(Character.getNumericValue(Retorno.charAt(1)));
            Simbolos.add(charRed);
            char SimboloActual = charRed.charAt(0);
            AddEstadoRed(Estados.get(Estados.size() - 1), SimboloActual);
            imprimirPasso(Retorno);

            Goto(Estados.get(Estados.size() - 1), this.token.get(tokenActual));
        } else if (Retorno.equals("error")) {
            System.out.println("Error");
        }
    }

    public void imprimirPasso(String Retorno) {
        System.out.print(this.Passo+" ");
        System.out.print("| Simbolos : ");
        Integer simbolo = 0;
        do {
            System.out.print(Simbolos.get(simbolo));
            simbolo++;
        } while (simbolo < Simbolos.size());
        System.out.print(" |  Estados:  ");
        Integer estado = 0;
        do {
            System.out.print(Estados.get(estado));
            estado++;
        } while (estado < Estados.size());
        System.out.print("  | Entrada : ");
        Integer entrada = this.tokenActual;
        do {
            System.out.print(token.get(entrada).cod);
            entrada++;
        } while (entrada < token.size());
        System.out.print(" |  Ação " + Retorno);
        System.out.print("\n");
        this.Passo++;
    }

    public void AddEstadoRed(Integer estado, char CharSimbo) {
        switch (estado) {
            case 0:
                switch (CharSimbo) {
                    case 'E':
                        Estados.add(1);
                        break;
                    case 'T':
                        Estados.add(2);
                        break;
                    case 'F':
                        Estados.add(3);
                        break;
                }
                break;
            case 4:
                switch (CharSimbo) {
                    case 'E':
                        Estados.add(8);
                        break;
                    case 'T':
                        Estados.add(2);
                        break;
                    case 'F':
                        Estados.add(3);
                        break;
                }
                break;

            case 6:
                switch (CharSimbo) {

                    case 'T':
                        Estados.add(9);
                        break;
                    case 'F':
                        Estados.add(3);
                        break;
                }
                break;

            case 7:
                switch (CharSimbo) {
                    case 'F':
                        Estados.add(10);
                        break;
                }
                break;

        }

    }

    public String regra(Integer regra) {
        switch (regra) {
            case 1:
                return "E";
            case 2:
                return "E";
            case 3:
                return "T";
            case 4:
                return "T";
            case 5:
                return "F";
            case 6:
                return "F";
        }
        return " ";
    }

    public Integer TamanhoRed(int tamanho) {

        switch (tamanho) {
            case 1:
                return 3;
            case 2:
                return 1;
            case 3:
                return 3;
            case 4:
                return 1;
            case 5:
                return 3;
            case 6:
                return 1;
        }
        return 0;
    }

    public void ImprimirTokens() {
        String codigo = "";
        for (int i = 0; i < token.size(); i++) {
            codigo += "\n\t" + token.get(i).tipoToken + "----> " + token.get(i).cod + "\n";
        }
        System.out.println(codigo);
    }

    public String VerificarTabela(int Estado, Token token) {
        switch (Estado) {
            case 0:
                switch (token.tipoToken) {
                    case Identificadores:
                        return "S5";
                    case AbreParenteses:
                        return "S4";
                    default:
                        return "error";
                }
            case 1:
                switch (token.tipoToken) {
                    case OperadorAritmeticoMais:
                        return "S6";
                    case Final:
                        return "acc";
                    default:
                        return "error";
                }
            case 2:
                switch (token.tipoToken) {
                    case OperadorAritmeticoMais:
                        return "R2";
                    case Multiplicação:
                        return "S7";
                    case FechaParentese:
                        return "R2";
                    case Final:
                        return "R2";
                    default:
                        return "error";
                }
            case 3:
                switch (token.tipoToken) {
                    case OperadorAritmeticoMais:
                        return "R4";
                    case Multiplicação:
                        return "R4";
                    case FechaParentese:
                        return "R4";
                    case Final:
                        return "R4";
                    default:
                        return "error";
                }
            case 4:
                switch (token.tipoToken) {
                    case Identificadores:
                        return "S5";
                    case AbreParenteses:
                        return "S4";
                    default:
                        return "error";
                }
            case 5:
                switch (token.tipoToken) {
                    case OperadorAritmeticoMais:
                        return "R6";
                    case Multiplicação:
                        return "R6";
                    case FechaParentese:
                        return "R6";
                    case Final:
                        return "R6";
                    default:
                        return "error";

                }
            case 6:
                switch (token.tipoToken) {
                    case Identificadores:
                        return "S5";
                    case AbreParenteses:
                        return "S4";
                    default:
                        return "error";
                }
            case 7:
                switch (token.tipoToken) {
                    case Identificadores:
                        return "S5";
                    case AbreParenteses:
                        return "S4";
                    default:
                        return "error";
                }
            case 8:
                switch (token.tipoToken) {
                    case OperadorAritmeticoMais:
                        return "S6";
                    case FechaParentese:
                        return "S11";
                    default:
                        return "error";
                }
            case 9:
                switch (token.tipoToken) {
                    case OperadorAritmeticoMais:
                        return "R1";
                    case Multiplicação:
                        return "S7";
                    case FechaParentese:
                        return "R1";
                    case Final:
                        return "R1";
                    default:
                        return "error";
                }
            case 10:
                switch (token.tipoToken) {
                    case OperadorAritmeticoMais:
                        return "R3";
                    case Multiplicação:
                        return "R3";
                    case FechaParentese:
                        return "R3";
                    case Final:
                        return "R3";
                    default:
                        return "error";
                }
            case 11:
                switch (token.tipoToken) {
                    case OperadorAritmeticoMais:
                        return "R5";
                    case Multiplicação:
                        return "R5";
                    case FechaParentese:
                        return "R5";
                    case Final:
                        return "R5";
                    default:
                        return "error";
                }
        }
        return " ";
    }
}
