package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import symbolTable.SymbolTable;
import typechecking.TipoTD;
import typechecking.TypeDescriptor;

public class TestTypechecking {
	
	final String path = "src/test/data/testTypeChecking/";
	
    @BeforeEach
    void setup() {
        SymbolTable.init();
    }
	
	@Test
	void testDicRipetute() throws FileNotFoundException, SyntacticException {

	    Scanner scanner =
	        new Scanner(path + "1_dicRipetute.txt");

	    Parser parser = new Parser(scanner);

	    NodeProgram program = parser.parse();

	    TypeDescriptor td =
	        program.calcResType();

	    
	    assertEquals(TipoTD.ERROR, td.getTipo());    
	    assertEquals("Variabile a già dichiarata", td.getMsg());
	}
	
	@Test
	void testIdNonDec() throws FileNotFoundException, SyntacticException {

	    Scanner scanner =
	        new Scanner(path + "2_idNonDec.txt");

	    Parser parser = new Parser(scanner);

	    NodeProgram program = parser.parse();
	    
	    TypeDescriptor td =
	        program.calcResType();

	    
	    assertEquals(TipoTD.ERROR, td.getTipo());    
	    assertEquals("Variabile b non dichiarata", td.getMsg());
	}
	
	@Test
	void testIdNonDec2() throws FileNotFoundException, SyntacticException {

	    Scanner scanner =
	        new Scanner(path + "3_idNonDec.txt");

	    Parser parser = new Parser(scanner);

	    NodeProgram program = parser.parse();

	    TypeDescriptor td =
	        program.calcResType();	    
	    
	    assertEquals(TipoTD.ERROR, td.getTipo());    
	    assertEquals("Variabile c non dichiarata", td.getMsg());
	}
	
	@Test
	void testIdNonComp() throws FileNotFoundException, SyntacticException {

	    Scanner scanner =
	        new Scanner(path + "4_tipoNonCompatibile.txt");

	    Parser parser = new Parser(scanner);

	    NodeProgram program = parser.parse();

	    TypeDescriptor td =
	        program.calcResType();

	    
	    assertEquals(TipoTD.ERROR, td.getTipo());    
	    assertEquals("Assegnamento incompatibile", td.getMsg());
	}
	
	@Test
	void testInitNonCompatibile() throws FileNotFoundException, SyntacticException {

	    Scanner scanner =
	        new Scanner(path + "5_initNonCompatibile.txt");

	    Parser parser = new Parser(scanner);

	    NodeProgram program = parser.parse();

	    TypeDescriptor td =
	        program.calcResType();

	    assertEquals(TipoTD.ERROR, td.getTipo());
	    assertEquals("Tipo inizializzazione incompatibile",td.getMsg());
	}
	
	@Test
	void testCorretto1() throws FileNotFoundException, SyntacticException {

	    Scanner scanner =
	        new Scanner(path + "6_corretto.txt");

	    Parser parser = new Parser(scanner);

	    NodeProgram program = parser.parse();

	    TypeDescriptor td =
	        program.calcResType();
	    
	    assertEquals(TipoTD.OK, td.getTipo());
	}
	
	@Test
	void testCorretto2() throws FileNotFoundException, SyntacticException {

	    Scanner scanner =
	        new Scanner(path + "7_corretto.txt");

	    Parser parser = new Parser(scanner);
	    
	    NodeProgram program = parser.parse();

	    TypeDescriptor td =
	        program.calcResType();
	    
	    assertEquals(TipoTD.OK, td.getTipo());
	}
	
	@Test
	void testCorretto3() throws FileNotFoundException, SyntacticException {

	    Scanner scanner =
	        new Scanner(path + "8_corretto.txt");

	    Parser parser = new Parser(scanner);

	    NodeProgram program = parser.parse();

	    TypeDescriptor td =
	        program.calcResType();
	    
	    assertEquals(TipoTD.OK, td.getTipo());
	}
	
}
