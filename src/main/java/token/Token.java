package token;


/*
 * Rappresenta una unità lessicale prodotta
 * dallo scanner
 */
public class Token {

    private TokenType type;
    private int riga;
    private String value;

    // costruttore per token senza valore (es: PLUS, TYINT, EOF)
    public Token(TokenType type, int riga) {
        this.type = type;
        this.riga = riga;
        this.value = null;
    }

    // costruttore per token con valore (ID, INT, FLOAT)
    public Token(TokenType type, int riga, String value) {
        this.type = type;
        this.riga = riga;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public int getRiga() {
        return riga;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value != null) {
            return type + ", riga: " + riga + ", valore: " + value;
        }
        return type + ", riga: " + riga;
    }
}