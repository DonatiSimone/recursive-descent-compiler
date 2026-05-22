package test;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;

class TestParser {
	
    final String path = "src/test/data/testParser/";
    SyntacticException e;
    Scanner scanner;
    Parser parser;
    String fileName;
    
    @BeforeEach
    void setup() throws FileNotFoundException {
        if (fileName != null) {
            scanner = new Scanner(path + fileName);
    	    parser = new Parser(scanner);
        }
    }
	
	@Test
	void testDichiarazioni() throws SyntacticException, FileNotFoundException {
	    fileName = "testDichiarazioni.txt";
	    setup();

	    assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testPrint() throws SyntacticException, FileNotFoundException {
		fileName = "testPrint.txt";
		setup();

	    assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testDichiarazioniErr1() throws SyntacticException, FileNotFoundException {
		fileName = "testDichiarazioniErr.txt";
		setup();
	    
        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 3: atteso ID, trovato TYINT", e.getMessage());
	}
	
	@Test
	void testDichiarazioniErr2() throws SyntacticException, FileNotFoundException {
		fileName = "testDichiarazioniErr2.txt";
		setup();
	    
        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 3: atteso ID, trovato INT", e.getMessage());
	}
	
	@Test
	void testPrintErr1() throws SyntacticException, FileNotFoundException {
		fileName = "testPrintErr.txt";
		setup();

        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 2: atteso ID, trovato INT", e.getMessage());
	}
	
	@Test
	void testPrintErr2() throws SyntacticException, FileNotFoundException {
		fileName = "testPrintErr2.txt";
		setup();

        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 3: atteso ID, trovato TYINT", e.getMessage());
	}
	
	@Test
	void testParserCorretto1() throws SyntacticException, FileNotFoundException {
		fileName = "testParserCorretto1.txt";
		setup();

	    assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testParserCorretto2() throws SyntacticException, FileNotFoundException {
		fileName = "testParserCorretto2.txt";
		setup();

	    assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testParserEcc_0() throws SyntacticException, FileNotFoundException {
		fileName = "testParserEcc_0.txt";
		setup();

        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 1: atteso ASSIGN o OP_ASSIGN, trovato SEMI", e.getMessage());
	}
	
	@Test
	void testParserEcc_1() throws SyntacticException, FileNotFoundException {
		fileName = "testParserEcc_1.txt";
		setup();

        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 2: atteso ID, FLOAT o INT, trovato TIMES", e.getMessage());
	}
	
	@Test
	void testParserEcc_2() throws SyntacticException, FileNotFoundException {
		fileName = "testParserEcc_2.txt";
		setup();

        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 3: atteso TYFLOAT, TYINT, ID, PRINT o EOF, trovato INT", e.getMessage());
	}
	
	@Test
	void testParserEcc_3() throws SyntacticException, FileNotFoundException {
		fileName = "testParserEcc_3.txt";
		setup();

        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 2: atteso ASSIGN o OP_ASSIGN, trovato PLUS", e.getMessage());
	}
	
	@Test
	void testParserEcc_4() throws SyntacticException, FileNotFoundException {
		fileName = "testParserEcc_4.txt";
		setup();

        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 2: atteso ID, trovato INT", e.getMessage());
	}
	
	@Test
	void testParserEcc_5() throws SyntacticException, FileNotFoundException {
		fileName = "testParserEcc_5.txt";
		setup();

        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 3: atteso ID, trovato INT", e.getMessage());
	}
	
	@Test
	void testParserEcc_6() throws SyntacticException, FileNotFoundException {
		fileName = "testParserEcc_6.txt";
		setup();

        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 3: atteso ID, trovato TYFLOAT", e.getMessage());
	}
	
	
	@Test
	void testParserEcc_7() throws SyntacticException, FileNotFoundException {
		fileName = "testParserEcc_7.txt";
		setup();

        e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
        assertEquals("Errore alla riga 2: atteso ID, trovato ASSIGN", e.getMessage());
	}
	
	@Test
	void testASTDecl() throws SyntacticException, FileNotFoundException {

	    Scanner scanner = new Scanner(path + "testDichiarazioni.txt");
	    Parser parser = new Parser(scanner);
	    NodeProgram prg = parser.parse();
	    assertEquals("Program: [DECL: x, INT, DECL: ciao, INT, DECL: y, FLOAT]", prg.toString());
	}
	
	@Test
	void testASTPrint() throws SyntacticException, FileNotFoundException {

	    Scanner scanner = new Scanner(path + "testPrint.txt");
	    Parser parser = new Parser(scanner);
	    NodeProgram prg = parser.parse();
	    assertEquals("Program: [Print: ciao, Print: floatf]", prg.toString());
	}
	
	@Test
	void testLeftAssociativity() throws SyntacticException, FileNotFoundException {
	    Scanner scanner = new Scanner(path + "testLeft.txt");
	    Parser parser = new Parser(scanner);
	    NodeProgram prg = parser.parse();
	    assertEquals("Program: [DECL: x, INT, ASSIGN: x = ((1 MINUS 2) MINUS 3)]", prg.toString());	
	}
	
	@Test
	void testTimesAssign() throws SyntacticException, FileNotFoundException {
	    Scanner scanner = new Scanner(path + "testTimesAssign.txt");
	    Parser parser = new Parser(scanner);
	    NodeProgram prg = parser.parse();
	    assertEquals("Program: [DECL: x, INT, ASSIGN: x = (x TIMES (5 PLUS 6))]", prg.toString());	
	}
	
	@Test
	void testASTExpr() throws SyntacticException, FileNotFoundException {
		
	    Scanner scanner = new Scanner(path + "testASTExpr.txt");
	    Parser parser = new Parser(scanner);
	    NodeProgram prg = parser.parse();
		assertEquals("Program: [DECL: temp, INT, ASSIGN: temp = (temp PLUS 7), ASSIGN: temp = ((3 PLUS (7 TIMES 5)) MINUS 6)]", prg.toString());
	}
	
}

