package eccezioni;

import token.TokenType;

/*
 * Eccezione sollevata durante
 * l'analisi sintattica.
 * 
 * Indica che la sequenza di token
 * ricevuta dal parser non rispetta
 * la grammatica del linguaggio.
 */
public class SyntacticException extends Exception{
	
	/*
	 * Identificatore di versione per la serializzazione
	 * della classe, dichiarato per evitare warning
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Crea una eccezione sintattica
	 * con messaggio personalizzato.
	 */
	public SyntacticException(String message) {
		super(message);
	}
	
	/*
	 * Crea una eccezione sintattica
	 * propagando la causa originale.
	 */
	public SyntacticException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/*
	 * Crea un messaggio di errore sintattico
	 * indicando:
	 * - riga dell'errore
	 * - token atteso
	 * - token trovato
	 */
	public SyntacticException(int riga, String atteso, TokenType trovato) {
	    super("Errore alla riga " + riga +
	          ": atteso " + atteso +
	          ", trovato " + trovato);
	}
}