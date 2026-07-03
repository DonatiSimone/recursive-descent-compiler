package test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import eccezioni.LexicalException;


import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestScanner {

    final String path = "src/test/data/testScanner/";

    Scanner scanner;
    LexicalException e;
    String fileName;

    
    /*
     * Crea lo Scanner per il file di test specificato.
     *
     * Non è stato utilizzato @BeforeEach perché i test
     * utilizzano file di input differenti e richiedono
     * quindi una inizializzazione personalizzata.
     */
    private void loadScanner(String fileName) throws FileNotFoundException {
        if (fileName != null) {
            scanner = new Scanner(path + fileName);
        }
    }

    @Test
    void testCaratteriNonAmmessi() throws FileNotFoundException, LexicalException {

        loadScanner("caratteriNonCaratteri.txt");
        
        e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
        assertEquals("LexicalException a riga: 1, carattere illegale: ^", e.getMessage());
        e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
        assertEquals("LexicalException a riga: 1, carattere illegale: &", e.getMessage());
        assertEquals(TokenType.SEMI, scanner.nextToken().getType());
        e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
        assertEquals("LexicalException a riga: 2, carattere illegale: |", e.getMessage());
        assertEquals(TokenType.PLUS, scanner.nextToken().getType());
        assertEquals(TokenType.EOF, scanner.nextToken().getType());
    }
    
    @Test
    void testErroriNumbers() throws FileNotFoundException, LexicalException {
    	
        loadScanner("erroriNumbers.txt");
        
    	assertEquals(TokenType.INT, scanner.nextToken().getType());
    	assertEquals(TokenType.INT, scanner.nextToken().getType());
    	e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
    	assertEquals("LexicalException a riga: 3, sequenza di caratteri: '123.121212' non riconosciuta", e.getMessage());
    	e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
    	assertEquals("LexicalException a riga: 5, sequenza di caratteri: '123.123.123' non riconosciuta", e.getMessage());
    	e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
    	assertEquals("LexicalException a riga: 7, sequenza di caratteri: '123.12a' non riconosciuta", e.getMessage());
    	e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
    	assertEquals("LexicalException a riga: 9, sequenza di caratteri: '12a' non riconosciuta", e.getMessage());
    	assertEquals(TokenType.EOF, scanner.nextToken().getType());
    }

    @Test
    void testCaratteriSkip() throws FileNotFoundException, LexicalException {
    	
        loadScanner("caratteriSkip.txt");
        
    	assertEquals(TokenType.EOF, scanner.nextToken().getType());
    }

    @Test
    void testEOF() throws FileNotFoundException, LexicalException {
    	
    	loadScanner("testEOF.txt");
        
    	assertEquals(TokenType.EOF, scanner.nextToken().getType());
    }

    @Test
    void testFloat() throws FileNotFoundException, LexicalException{
    	
    	loadScanner("testFloat.txt");
        
    	assertEquals(TokenType.FLOAT, scanner.nextToken().getType());
    	assertEquals(TokenType.FLOAT, scanner.nextToken().getType());
    	assertEquals(TokenType.FLOAT, scanner.nextToken().getType());
    	assertEquals(TokenType.FLOAT, scanner.nextToken().getType());
    }

    @Test
    void testGenerale() throws FileNotFoundException, LexicalException {
    	
    	loadScanner("testGenerale.txt");

        assertEquals("TYINT, riga: 1", scanner.nextToken().toString());
        assertEquals("ID, riga: 1, valore: temp", scanner.nextToken().toString());
        assertEquals("SEMI, riga: 1", scanner.nextToken().toString());

        assertEquals("TYINT, riga: 2", scanner.nextToken().toString());
        assertEquals("ID, riga: 2, valore: temp1", scanner.nextToken().toString());
        assertEquals("SEMI, riga: 2", scanner.nextToken().toString());

        assertEquals("ID, riga: 3, valore: temp", scanner.nextToken().toString());
        assertEquals("OP_ASSIGN, riga: 3, valore: +=", scanner.nextToken().toString());
        assertEquals("FLOAT, riga: 3, valore: 5.", scanner.nextToken().toString());
        assertEquals("SEMI, riga: 3", scanner.nextToken().toString());

        assertEquals("TYFLOAT, riga: 5", scanner.nextToken().toString());
        assertEquals("ID, riga: 5, valore: b", scanner.nextToken().toString());
        assertEquals("SEMI, riga: 5", scanner.nextToken().toString());

        assertEquals("ID, riga: 6, valore: b", scanner.nextToken().toString());
        assertEquals("ASSIGN, riga: 6", scanner.nextToken().toString());
        assertEquals("ID, riga: 6, valore: temp1", scanner.nextToken().toString());
        assertEquals("PLUS, riga: 6", scanner.nextToken().toString());
        assertEquals("FLOAT, riga: 6, valore: 3.2", scanner.nextToken().toString());
        assertEquals("SEMI, riga: 6", scanner.nextToken().toString());

        assertEquals("PRINT, riga: 7", scanner.nextToken().toString());
        assertEquals("ID, riga: 7, valore: b", scanner.nextToken().toString());
        assertEquals("SEMI, riga: 7", scanner.nextToken().toString());

        assertEquals(TokenType.EOF, scanner.nextToken().getType());
    }

    @Test
    void testId() throws FileNotFoundException, LexicalException {
    	
    	loadScanner("testId.txt");
        
    	assertEquals(TokenType.ID, scanner.nextToken().getType());
    	assertEquals(TokenType.ID, scanner.nextToken().getType());
    	assertEquals(TokenType.ID, scanner.nextToken().getType());
    	assertEquals(TokenType.ID, scanner.nextToken().getType());
    }

    @Test
    void testIdKeyWords() throws FileNotFoundException, LexicalException {
    	
    	loadScanner("testIdKeywords.txt");


        assertEquals(TokenType.TYINT, scanner.nextToken().getType());
        assertEquals("inta", scanner.nextToken().getValue());

        assertEquals(TokenType.TYFLOAT, scanner.nextToken().getType());

        assertEquals(TokenType.PRINT, scanner.nextToken().getType());

        assertEquals("nome", scanner.nextToken().getValue());

        assertEquals("intnome", scanner.nextToken().getValue());

        assertEquals(TokenType.TYINT, scanner.nextToken().getType());
        assertEquals("nome", scanner.nextToken().getValue());

        assertEquals(TokenType.EOF, scanner.nextToken().getType());
    }

    @Test 
    void testInt() throws FileNotFoundException, LexicalException {
    	
    	loadScanner("testInt.txt");

    	assertEquals(TokenType.INT, scanner.nextToken().getType());
    	assertEquals(TokenType.INT, scanner.nextToken().getType());
    	assertEquals(TokenType.INT, scanner.nextToken().getType());
    	assertEquals(TokenType.INT, scanner.nextToken().getType());
    }

    @Test 
    void testKeywords() throws FileNotFoundException, LexicalException {
    	
    	loadScanner("testKeywords.txt");

        assertEquals(TokenType.PRINT, scanner.nextToken().getType());
        assertEquals(TokenType.TYFLOAT, scanner.nextToken().getType());
        assertEquals(TokenType.TYINT, scanner.nextToken().getType());

        assertEquals(TokenType.EOF, scanner.nextToken().getType());
    }

    @Test
    void testOpsDels() throws FileNotFoundException, LexicalException {
    	
    	loadScanner("testOpsDels.txt");

        assertEquals(TokenType.PLUS, scanner.nextToken().getType());
        assertEquals(TokenType.OP_ASSIGN, scanner.nextToken().getType());

        assertEquals(TokenType.MINUS, scanner.nextToken().getType());
        assertEquals(TokenType.TIMES, scanner.nextToken().getType());

        assertEquals(TokenType.DIVIDE, scanner.nextToken().getType());

        assertEquals(TokenType.OP_ASSIGN, scanner.nextToken().getType());

        assertEquals(TokenType.ASSIGN, scanner.nextToken().getType());
        assertEquals(TokenType.OP_ASSIGN, scanner.nextToken().getType());

        assertEquals(TokenType.MINUS, scanner.nextToken().getType());
        assertEquals(TokenType.ASSIGN, scanner.nextToken().getType());
        assertEquals(TokenType.OP_ASSIGN, scanner.nextToken().getType());

        assertEquals(TokenType.SEMI, scanner.nextToken().getType());

        assertEquals(TokenType.EOF, scanner.nextToken().getType());
    }
    
    @Test
    void peekToken() throws FileNotFoundException, LexicalException {
    	
    	loadScanner("testGenerale.txt");
        
    	assertEquals(scanner.peekToken().getType(), TokenType.TYINT );
    	assertEquals(scanner.nextToken().getType(), TokenType.TYINT );
    	assertEquals(scanner.peekToken().getType(), TokenType.ID );
    	assertEquals(scanner.peekToken().getType(), TokenType.ID );
    	Token t = scanner.nextToken();
    	assertEquals(t.getType(), TokenType.ID);
    	assertEquals(t.getRiga(), 1);
    	assertEquals(t.getValue(), "temp");
    }
}

    