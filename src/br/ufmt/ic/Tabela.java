package br.ufmt.ic;

public class Tabela extends AnaliseAscendente{

    public Tabela(String codigo) {
        super(codigo);
    }

    public String gerarTabela(int Estado, Token token) {
        switch (Estado) {
            case 0:
                switch (token.tipoToken) {
                    case NumeroInteiro:
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
                        Regras(1,'R');
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

    public void Regras(int regra, char Elemento) {
        switch (regra) {
            case 0:
                switch (Elemento) {
                    case 'E':
                        Estados.add(1);
                    case 'T':
                        Estados.add(2);
                    case 'F':
                        Estados.add(3);
                }
            case 4:
                switch (Elemento) {
                    case 'E':
                        Estados.add(8);
                    case 'T':
                        Estados.add(2);
                    case 'F':
                        Estados.add(3);
                }
            case 6:
                switch (Elemento) {

                    case 'T':
                        Estados.add(9);
                    case 'F':
                        Estados.add(3);

                }
            case 7:
                switch (Elemento) {
                    case 'F':
                        Estados.add(10);
                }

        }

    }
}
