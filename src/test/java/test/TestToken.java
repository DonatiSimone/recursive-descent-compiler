package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken {

    @Test
    void testTokenSenzaValore() {
        Token t = new Token(TokenType.SEMI, 1);

        assertEquals(TokenType.SEMI, t.getType());
        assertEquals(1, t.getRiga());
        assertNull(t.getValue());

        assertEquals("SEMI, riga: 1", t.toString());
    }

    @Test
    void testTokenConValore() {
        Token t = new Token(TokenType.INT, 2, "5");

        assertEquals(TokenType.INT, t.getType());
        assertEquals(2, t.getRiga());
        assertEquals("5", t.getValue());

        assertEquals("INT, riga: 2, valore: 5", t.toString());
    }
    
    @Test
    void testTokenFloat() {
        Token t = new Token(TokenType.FLOAT, 2, "3.2");

        assertEquals("FLOAT, riga: 2, valore: 3.2", t.toString());
    }

    @Test
    void testTokenID() {
        Token t = new Token(TokenType.ID, 3, "tempa");

        assertEquals("ID, riga: 3, valore: tempa", t.toString());
    }

    @Test
    void testTokenPrint() {
        Token t = new Token(TokenType.PRINT, 2);

        assertEquals("PRINT, riga: 2", t.toString());
    }
    
    @Test
    void testTokenTyInt() {
        Token t = new Token(TokenType.TYINT, 2);

        assertEquals("TYINT, riga: 2", t.toString());
    }
    
    @Test
    void testTokenTyFloat() {
        Token t = new Token(TokenType.TYFLOAT, 2);

        assertEquals("TYFLOAT, riga: 2", t.toString());
    }
    
    @Test
    void testTokenPlus() {
        Token t = new Token(TokenType.PLUS, 2);

        assertEquals("PLUS, riga: 2", t.toString());
    }
    
    @Test
    void testTokenMinus() {
        Token t = new Token(TokenType.MINUS, 2);

        assertEquals("MINUS, riga: 2", t.toString());
    }
    
    @Test
    void testTokenTimes() {
        Token t = new Token(TokenType.TIMES, 2);

        assertEquals("TIMES, riga: 2", t.toString());
    }
    
    @Test
    void testTokenDivide() {
        Token t = new Token(TokenType.DIVIDE, 2);

        assertEquals("DIVIDE, riga: 2", t.toString());
    }
    
    @Test
    void testTokenAssign() {
        Token t = new Token(TokenType.ASSIGN, 2, "3.2");

        assertEquals("ASSIGN, riga: 2, valore: 3.2", t.toString());
    }
    
    @Test
    void testTokenOpAssign() {
        Token t = new Token(TokenType.OP_ASSIGN, 2, "3.2");

        assertEquals("OP_ASSIGN, riga: 2, valore: 3.2", t.toString());
    }

    @Test
    void testTokenEOF() {
        Token t = new Token(TokenType.EOF, 2, "3.2");

        assertEquals("EOF, riga: 2, valore: 3.2", t.toString());
    }
}