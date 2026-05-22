package test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import codeGeneration.Registri;
import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import symbolTable.SymbolTable;

public class TestCodeGeneration {
	
    final String path = "src/test/data/testCodeGenerator/";
	
    @BeforeEach
    void setup() {
    	
        // reset strutture globali
        SymbolTable.init();
        Registri.reset();
    }
    
    
    @Test
    void testAssign() throws FileNotFoundException, SyntacticException {

        Parser parser = new Parser(
            new Scanner(path + "1_assign.txt")
        );

        NodeProgram program = parser.parse();

        String code = program.calcCodice();

        //System.out.print(code);
        
        assertEquals(
            "1 6 / sa la p P ",
            code
        );

        assertEquals(
            "",
            program.getLog()
        );
    }
    
    @Test
    void testDivisioni() throws FileNotFoundException, SyntacticException {

        Parser parser = new Parser(
            new Scanner(path + "2_divisioni.txt")
        );

        NodeProgram program = parser.parse();

        String code = program.calcCodice();

        //System.out.print(code);
        
        assertEquals(
            "0 sa la 1 + sa 6 sb 1.0 6 / la lb / + sc la p P lb p P lc p P ",
            code
        );

        assertEquals(
            "",
            program.getLog()
        );
    }
    
    @Test
    void testGenerale() throws FileNotFoundException, SyntacticException {

        Parser parser = new Parser(
            new Scanner(path + "3_generale.txt")
        );

        NodeProgram program = parser.parse();

        String code = program.calcCodice();

        // System.out.print(code);
        
        assertEquals(
            "5 3 + sa la 0.5 + sb la p P lb 4 / sb lb p P lb 1 - sc lc lb * sc lc p P ",
            code
        );

        assertEquals(
            "",
            program.getLog()
        );
    }
    
    @Test
    void testRegistriFiniti() throws FileNotFoundException, SyntacticException {

        Parser parser = new Parser(
            new Scanner(path + "4_registriFiniti.txt")
        );

        NodeProgram program = parser.parse();

        String code = program.calcCodice();

        //System.out.print(code);
        
        assertEquals(
                "6 2 / sa la p P ",
                code
            );

        assertEquals(
            "Registri esauriti",
            program.getLog()
        );
    }
    

}
