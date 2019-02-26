package br.ufmt.ic;

public enum TipoToken {
    Identificadores(""),
    PalavraReservadaIF("if"),
    Comentario("/* */"),
    OperadordeAtribuicao("="),
    OperadorAritmeticoMais("+"),
    OperadorAritmeticoMenos("-"),
    Multiplicação("*"),
    AbreParenteses("("),
    FechaParentese(")"),
    AbreChaves("{"),
    FechaChaves("}"),
    Integer("integer"),
    OperadorRelacional("OperadorRelacional"),
    NumeroInteiro("Numero Inteiro"),
    NumeroFloat("Numero Float");



    private String token;

    TipoToken(String token){
        this.token= token;
    }
}
