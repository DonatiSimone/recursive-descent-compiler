package scanner;

import java.io.FileNotFoundException;
import eccezioni.LexicalException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.*;
import token.*;


/*
 * Analizzatore lessicale del compilatore.
 * 
 * Legge il file sorgente carattere per carattere
 * e produce token.
 */
public class Scanner {
	final char EOF = (char) -1; 
	private int riga;
	private PushbackReader buffer;
	private Token nextTk = null;

	// insiemi
    private Set<Character> skipChars;
    private Set<Character> letters;
    private Set<Character> digits;

    // mapping operatori e delimitatori
    private Map<Character, TokenType> operTkType;
    private Map<Character, TokenType> delimTkType;

    // parole chiave
    private Map<String, TokenType> keyWordsTkType;

    /*
     * Costruisce lo scanner associandolo
     * al file sorgente da analizzare.
     */
    public Scanner(String fileName) throws FileNotFoundException {
    	this.buffer = new PushbackReader(new FileReader(fileName));
		init();
    }
	
    /*
     * Inizializza:
     * - strutture dati dello scanner
     * - caratteri validi
     * - operatori
     * - delimitatori
     * - parole chiave
     */
	private void init() {
	    riga = 1;

	    skipChars = new HashSet<>(Arrays.asList(' ', '\n', '\t', '\r'));

	    letters = new HashSet<>();
	    for (char c = 'a'; c <= 'z'; c++) {
	        letters.add(c);
	    }

	    digits = new HashSet<>();
	    for (char c = '0'; c <= '9'; c++) {
	        digits.add(c);
	    }

	    operTkType = new HashMap<>();
	    operTkType.put('+', TokenType.PLUS);
	    operTkType.put('-', TokenType.MINUS);
	    operTkType.put('*', TokenType.TIMES);
	    operTkType.put('/', TokenType.DIVIDE);
	    operTkType.put('=', TokenType.ASSIGN);

	    delimTkType = new HashMap<>();
	    delimTkType.put(';', TokenType.SEMI);

	    keyWordsTkType = new HashMap<>();
	    keyWordsTkType.put("int", TokenType.TYINT);
	    keyWordsTkType.put("float", TokenType.TYFLOAT);
	    keyWordsTkType.put("print", TokenType.PRINT);
	}
	
	
	
	/*
	 * Legge il prossimo token dal file sorgente.
	 * 
	 * Gestisce:
	 * - skip di whitespace
	 * - identificatori e keyword
	 * - operatori e delimitatori
	 * - numeri interi e float
	 * - EOF
	 * - errori lessicali
	 */
	public Token nextToken() throws LexicalException{

		if (nextTk != null){
			Token t = nextTk;
			nextTk = null;
			return t;
		}
		
		char nextChar;
		nextChar = peekChar();

		while(true){
		    if (skipChars.contains(nextChar)) {
		        if (nextChar == '\n') {
		            riga++;
		        }
		        readChar();
		    } else if (nextChar == EOF){
		    	return new Token(TokenType.EOF, riga);
			} else {
				break;
			}
    		nextChar = peekChar();
		}

		if (letters.contains(nextChar)) {
			return scanId();
		}

		if(operTkType.containsKey(nextChar) || delimTkType.containsKey(nextChar)){
			return scanOperator();
		}

		if(digits.contains(nextChar)){
			return scanNumber();
		}

		else {
			char c = readChar();
    		throw new LexicalException(riga, c);
		}
	}
	
	/*
	 * Restituisce il prossimo token senza consumarlo.
	 * 
	 * Implementa un lookahead di 1 token.
	 */
	public Token peekToken() throws LexicalException{
		if (nextTk == null){
			nextTk = nextToken();
		}
		return nextTk;
	}

	/*
	 * Legge identificatori e parole chiave.
	 * 
	 * Un identificatore può contenere:
	 * - lettere
	 * - cifre
	 * 
	 * Se la stringa letta corrisponde
	 * ad una keyword, viene restituito
	 * il relativo TokenType.
	 */
	private Token scanId() throws LexicalException{
		String id = "";
    	char c = peekChar();

    	while (letters.contains(c) || digits.contains(c)) {
        	id += readChar();
        	c = peekChar();
    	}

    	// controllo keyword
    	if (keyWordsTkType.containsKey(id)) {
        	return new Token(keyWordsTkType.get(id), riga);
    	}

    	// altrimenti è un identificatore
    	return new Token(TokenType.ID, riga, id);
	}
	
	/*
	 * Riconosce:
	 * - operatori aritmetici
	 * - operatori di assegnamento composto
	 * - delimitatori
	 * 
	 * Gestisce operatori come:
	 * += -= *= /=
	 */
	private Token scanOperator() throws LexicalException{
		char c = readChar();
		if ((operTkType.containsKey(c) && peekChar() == '=')) {
			readChar();
			return new Token(TokenType.OP_ASSIGN, riga, c + "=");
		}
		if (operTkType.containsKey(c)){
			return new Token(operTkType.get(c), riga);
		}
		else{
			return new Token(delimTkType.get(c), riga);
		}
	}

	/*
	 * Legge numeri interi e float.
	 * 
	 * Vincoli:
	 * - massimo 5 cifre decimali
	 * - un solo punto decimale
	 * 
	 * Genera LexicalException in caso di:
	 * - più punti decimali
	 * - troppi decimali
	 * - caratteri non validi nel numero
	 */
	private Token scanNumber() throws LexicalException{
		String number = "";
		boolean contieneDecimale = false;
		int countDecimali = 0;

		while(digits.contains(peekChar()) || peekChar() == '.'){

			char c = peekChar();

        	if (c == '.') {
        	    if (contieneDecimale) {
        	        while (digits.contains(peekChar()) || letters.contains(peekChar()) || peekChar() == '.') {
        	            number += readChar();
        	        }
        	        throw new LexicalException(riga, number);
        	    }
        	    contieneDecimale = true;
        	    number += readChar();
        	    continue;
        	}
		
        if (contieneDecimale) {
            if (countDecimali == 5) {
                // lancia errore se troppe cifre decimali (>5)
                while (digits.contains(peekChar()) || letters.contains(peekChar()) || peekChar() == '.') {
                    number += readChar();
                }
                throw new LexicalException(riga, number);
            }
            countDecimali++;
        }

        number += readChar();
    }

		if(letters.contains(peekChar())){
			while (digits.contains(peekChar()) || letters.contains(peekChar())){
				number += readChar();
			}
			throw new LexicalException(riga, number);
		}
		
		if(number.contains(".")){
			return new Token(TokenType.FLOAT, riga, number);
		} else {
			return new Token(TokenType.INT, riga, number);
		}
	}

	
	/*
	 * Consuma e restituisce il prossimo carattere
	 * dal buffer di input.
	 */
	private char readChar() throws LexicalException {
		try {
			return ((char) this.buffer.read());
		} catch (IOException e) {
			throw new LexicalException(riga);
		}
	}

	/*
	 * Restituisce il prossimo carattere
	 * senza consumarlo.
	 * 
	 * Utilizza unread() del PushbackReader.
	 */
	private char peekChar() throws LexicalException {
		char c = 0;
		try {
			c = (char) buffer.read();
		} catch (IOException e) {
			throw new LexicalException(riga);
		}
		try {
			buffer.unread(c);
		} catch (IOException e) {
			throw new LexicalException(riga);
		}
		return c;
	}

}
