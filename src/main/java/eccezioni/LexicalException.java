package eccezioni;

/*
 * Eccezione sollevata durante
 * l'analisi lessicale.
 * 
 * Indica la presenza di sequenze
 * di caratteri non valide nel
 * file sorgente.
 */
public class LexicalException extends Exception {

	/*
	 * Identificatore di versione per la serializzazione
	 * della classe, dichiarato per evitare warning
	 */
	private static final long serialVersionUID = 1L;

	// Exception per carattere illegale
    public LexicalException(int riga, char c) {
        super("LexicalException a riga: " + riga + ", carattere illegale: " + c);
    }

    // Exception per id o numeri non validi
    public LexicalException(int riga, String id) {
        super("LexicalException a riga: " + riga + ", sequenza di caratteri: '" + id + "' non riconosciuta");
    }

    // Errore generico del lexer
    public LexicalException(int riga) {
        super("LexicalException a riga: " + riga);
    }
}