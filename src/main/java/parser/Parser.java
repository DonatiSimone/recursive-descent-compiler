package parser;

import java.util.ArrayList;

import ast.*;
import eccezioni.LexicalException;
import eccezioni.SyntacticException;

import token.Token;
import token.TokenType;
import scanner.Scanner;

/*
 * Parser ricorsivo discendente LL(1)
 * 
 * Riceve token dallo Scanner e costruisce l'AST
 * 
 * Ogni metodo parse implementa una regola grammaticale
 */
public class Parser {
	private Scanner scanner;
	
	public Parser(Scanner scanner) {
		this.scanner=scanner;
	}
	
	public NodeProgram parse() throws SyntacticException{
		return this.parsePrg();
	}
	
	// Prg → DSs $
	private NodeProgram parsePrg() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
			case TYFLOAT, TYINT, ID, PRINT, EOF -> { // Prg -> DSs $
				ArrayList<NodeDecSt> ds = parseDSs();
				match(TokenType.EOF);
				return new NodeProgram(ds);
			}
		
			// token tk alla riga tk.getRiga() non e’ inizio di programma
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "TYFLOAT, TYINT, ID, PRINT o EOF",
				    tk.getType()
				);
		}
	}
	
	// DSs → Dcl DSs | Stm DSs | ϵ
	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
			case TYFLOAT, TYINT -> {
				NodeDecl dcl = parseDcl();
				ArrayList<NodeDecSt> list = parseDSs();
				list.add(0, dcl);
				return list;
			}
			case ID, PRINT -> {
				NodeStm stm = parseStm();
				ArrayList<NodeDecSt> list = parseDSs();
	            if (stm != null) {
	                list.add(0, stm);
	            }
				return list;
			}
			case EOF -> {
				return new ArrayList<NodeDecSt>();
			}
			
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "TYFLOAT, TYINT, ID, PRINT o EOF",
				    tk.getType()
				);
		}
	}
	
	// Dcl → Ty id DclP
	private NodeDecl parseDcl() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
			case TYFLOAT, TYINT -> {
				LangType ty = parseTy();
				NodeId id = new NodeId(match(TokenType.ID).getValue());
				NodeExpr dclp = parseDclP();
				return new NodeDecl(id, ty, dclp);
			}
			
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "TYFLOAT o TYINT",
				    tk.getType()
				);
		}
		
	}
	
	// Ty → float | int
	private LangType parseTy() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
			case TYFLOAT -> {
				match(TokenType.TYFLOAT);
				return LangType.FLOAT;
			}
			case TYINT -> {
				match(TokenType.TYINT);
				return LangType.INT;
			}
			
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "TYFLOAT o TYINT",
				    tk.getType()
				);
		}
	}
	
	// Dclp → ; | = Exp ;
	private NodeExpr parseDclP() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
			case SEMI -> {
				match(TokenType.SEMI);
				return null;
			}
			case ASSIGN -> {
				match(TokenType.ASSIGN);
				NodeExpr expr = parseExp();
				match(TokenType.SEMI);
				return expr;
			}
			
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "SEMI o ASSIGN",
				    tk.getType()
				);
		}
	}
	
	// Stm → id Op Exp ; | print id ;
	private NodeStm parseStm() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
		case ID -> {

			NodeId id = new NodeId(match(TokenType.ID).getValue());

			LangOper op = parseOp();

			NodeExpr expr = parseExp();

			if(op != LangOper.ASSIGN) {
			    expr = new NodeBinOp(
			        new NodeDeref(id),
			        op,
			        expr
			    );
			}

			match(TokenType.SEMI);

			return new NodeAssign(id, expr);
		}
			case PRINT -> {
				match(TokenType.PRINT);
				NodeId id = new NodeId(match(TokenType.ID).getValue());
				match(TokenType.SEMI);
				return new NodePrint(id);
			}
			
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "ID o PRINT",
				    tk.getType()
				);
		}
	}
	
	
	// Exp → Tr ExpP
	private NodeExpr parseExp() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
			case ID, FLOAT, INT -> {
				NodeExpr tr = parseTr();
				NodeExpr expP = parseExpP(tr);
				return expP;
			}
			
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "ID, FLOAT o INT",
				    tk.getType()
				);
		}
	}
	
	// ExpP → - Tr ExpP | + Tr ExpP | ϵ
	private NodeExpr parseExpP(NodeExpr left) throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
			case PLUS -> {
			    match(TokenType.PLUS);
			    NodeExpr right = parseTr();
			    NodeBinOp node = new NodeBinOp(
			        left,
			        LangOper.PLUS,
			        right
			    );
			    return parseExpP(node);
			}
			
			case MINUS -> {
			    match(TokenType.MINUS);
			    NodeExpr right = parseTr();
			    NodeBinOp node = new NodeBinOp(
			        left,
			        LangOper.MINUS,
			        right
			    );
			    return parseExpP(node);
			}
			
			case SEMI -> {
				return left;
			}
			
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "PLUS, MINUS o SEMI",
				    tk.getType()
				);
		}
	}
	
	// Tr → Val TrP
	private NodeExpr parseTr() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
			case ID, FLOAT, INT -> {
				NodeExpr left = parseVal();
				return parseTrP(left);
			}
			
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "ID, FLOAT o INT",
				    tk.getType()
				);
		}
	}
	
	// TrP → / Val TrP | * Val Trp | ϵ
	private NodeExpr parseTrP(NodeExpr left) throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
			case TIMES -> {
			    match(TokenType.TIMES);
			    NodeExpr right = parseVal();
			    NodeBinOp node = new NodeBinOp(
			        left,
			        LangOper.TIMES,
			        right
			    );
			    return parseTrP(node);
			}
			case DIVIDE -> {
			    match(TokenType.DIVIDE);
			    NodeExpr right = parseVal();
			    NodeBinOp node = new NodeBinOp(
			        left,
			        LangOper.DIV,
			        right
			    );
			    return parseTrP(node);
			}
			case MINUS, PLUS, SEMI -> {
				return left;
			}
			
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "TIMES, DIVIDE, MINUS, PLUS o SEMI",
				    tk.getType()
				);
		}
	}
	
	// Val → intVal | floaVal | id
	private NodeExpr parseVal() throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		switch (tk.getType()) {
			case INT -> {
			    String value = match(TokenType.INT).getValue();
			    return new NodeCost(value, LangType.INT);
			}
			case FLOAT -> {
			    String value = match(TokenType.FLOAT).getValue();
			    return new NodeCost(value, LangType.FLOAT);
			}
			case ID -> {
			    String value = match(TokenType.ID).getValue();
			    return new NodeDeref(new NodeId(value));
			}
			
			default -> throw new SyntacticException(
				    tk.getRiga(),
				    "ID, FLOAT o INT",
				    tk.getType()
				);
		}
	}
	
	// Op → = | opAss
	private LangOper parseOp() throws SyntacticException {

	    Token tk;

	    try {
	        tk = scanner.peekToken();
	    } catch (LexicalException e) {
	        throw new SyntacticException("Lexical Exception", e);
	    }

	    switch (tk.getType()) {

	        case ASSIGN -> {
	            match(TokenType.ASSIGN);
	            return LangOper.ASSIGN;
	        }

	        case OP_ASSIGN -> {

	            String op = match(TokenType.OP_ASSIGN).getValue();

	            /*
	             * Gli operatori di assegnamento composto
	             * vengono convertiti nel corrispondente
	             * operatore aritmetico.
	             */
	            return switch(op) {
	                case "+=" -> LangOper.PLUS;
	                case "-=" -> LangOper.MINUS;
	                case "*=" -> LangOper.TIMES;
	                case "/=" -> LangOper.DIV;
	                default -> null;
	            };
	        }

	        default -> throw new SyntacticException(
	            tk.getRiga(),
	            "ASSIGN o OP_ASSIGN",
	            tk.getType()
	        );
	    }
	}
	
	
	/**
	 * Verifica che il token corrente sia del tipo atteso
	 * e lo consuma avanzando nel flusso dei token.
	 */
	private Token match(TokenType type) throws SyntacticException{
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException("Lexical Exception", e);
		}
		
		if (type.equals(tk.getType())) {
			try {
				return scanner.nextToken();
			} catch (LexicalException e){
				throw new SyntacticException("Lexical Exception", e);
			}
		} else {
			throw new SyntacticException(tk.getRiga(), type.toString(), tk.getType());
		}
	}
	
}
