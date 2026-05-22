package ast;

import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;
import typechecking.TipoTD;
import typechecking.TypeDescriptor;


/*
 * Nodo assegnamento
 */
public class NodeAssign extends NodeStm{
	
    private NodeId id;
    private NodeExpr expr;

    public NodeAssign(NodeId id, NodeExpr expr) {
        this.id = id;
        this.expr = expr;
    }

    public NodeId getId() {
        return id;
    }

    public NodeExpr getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "ASSIGN: " + id + " = " + expr;
    }
    
    @Override
    public TypeDescriptor calcResType() {
    	
    	TypeDescriptor leftTD =
    			id.calcResType();
    	
    	TypeDescriptor rightTD =
    			expr.calcResType();
    	
        if(leftTD.getTipo() == TipoTD.ERROR) {
            return leftTD;
        }

        if(rightTD.getTipo() == TipoTD.ERROR) {
            return rightTD;
        }
        
        if(!leftTD.compatible(rightTD)) {

            return new TypeDescriptor(
                "Assegnamento incompatibile"
            );
        }
        
        return new TypeDescriptor(TipoTD.OK);
    }
    
    @Override
    public String calcCodice() {

        String exprCode =
            expr.calcCodice();

        Attributes attr =
            SymbolTable.lookup(id.getName());

        return exprCode + "s" + attr.getRegistro() + " ";
    }
}
