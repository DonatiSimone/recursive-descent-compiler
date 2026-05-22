package token;

public enum TokenType {

    // costanti e parole chiave
    INT,    // [0-9]+
    FLOAT,  // [0-9]+.([0-9]{0,5})
    PRINT,  // print
    TYINT,  // int
    TYFLOAT, // float

    // identificatore
    ID, //[a-z][a-z0-9]*

    // operatori
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,
    ASSIGN,   // =
    OP_ASSIGN,   // +=

    // delimitatori
    SEMI,     // ;

    // fine file
    EOF;    // (char) -1
}