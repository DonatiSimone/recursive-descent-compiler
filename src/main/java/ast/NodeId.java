package ast;


import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;
import typechecking.TipoTD;
import typechecking.TypeDescriptor;

/*
 * Nodo identificatore
 * 
 * Contiene semplicemente il nome simbolico della variabile
 */
public class NodeId extends NodeAST {
	
	private String name;
	
	public NodeId(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		//return "ID: " + name();
		return name;
	}

	@Override
	public TypeDescriptor calcResType() {
		
		Attributes attr =
				SymbolTable.lookup(name);
		
		if(attr == null) {
			return new TypeDescriptor("Variabile " + name + " non dichiarata");
		}
		if(attr.getType() == LangType.INT) {
			return new TypeDescriptor(TipoTD.INT);
		}
		return new TypeDescriptor(TipoTD.FLOAT);
	}

	@Override
	public String calcCodice() {
		return "";
	}
}
