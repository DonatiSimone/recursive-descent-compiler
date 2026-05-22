package ast;

import typechecking.TypeDescriptor;

/* 
 * Nodo base astratto di tutto l'AST
 * Tutti i nodi ereditano da questa classe
 */
public abstract class NodeAST {
	public abstract TypeDescriptor calcResType();
	public abstract String calcCodice();
	protected String log = "";
	
	public String getLog() {
	    return log;
	}

	public void setLog(String log) {
	    this.log = log;
	}
}
