package ast;

import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;
import typechecking.TipoTD;
import typechecking.TypeDescriptor;

/*
 * Nodo print
 */
public class NodePrint extends NodeStm {

	private NodeId id;
	
	public NodePrint(NodeId id) {
		this.id = id;
	}
	
	public NodeId getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Print: " + getId();
	}
	
	@Override
	public TypeDescriptor calcResType() {

	    TypeDescriptor td =
	        id.calcResType();

	    if(td.getTipo() == TipoTD.ERROR) {
	        return td;
	    }

	    return new TypeDescriptor(TipoTD.OK);
	}

	@Override
	public String calcCodice() {

	    Attributes attr =
	        SymbolTable.lookup(id.getName());

	    
	    return "l" +
	           attr.getRegistro() +
	           " p P ";
	}
}
