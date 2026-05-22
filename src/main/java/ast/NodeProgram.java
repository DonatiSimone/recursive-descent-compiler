package ast;

import java.util.ArrayList;

import typechecking.TipoTD;
import typechecking.TypeDescriptor;


/*
 * Nodo radice del programma.
 * Contiene la lista di dichiarazioni e statement
 * presenti nel sorgente
 */
public class NodeProgram extends NodeAST{
	
	private final ArrayList<NodeDecSt> decSts;
	
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}
	
	public ArrayList<NodeDecSt> getDecSts() {
		return decSts;
	}
	
	@Override
	public String toString() {
		return "Program: " + decSts;
	}
	
	@Override
	public TypeDescriptor calcResType() {

	    for(NodeDecSt node : decSts) {

	        TypeDescriptor td =
	            node.calcResType();

	        if(td.getTipo() == TipoTD.ERROR) {
	            return td;
	        }
	    }

	    return new TypeDescriptor(TipoTD.OK);
	}

	@Override
	public String calcCodice() {

	    String code = "";

	    for(NodeDecSt node : decSts) {

	        if(!log.isEmpty()) {
	            break;
	        }

	        code += node.calcCodice();

	        if(!node.getLog().isEmpty()) {
	            log = node.getLog();
	        }
	    }

	    return code;
	}
}
