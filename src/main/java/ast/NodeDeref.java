package ast;

import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;
import typechecking.TypeDescriptor;


/*
 * Nodo dereferenziatore identificatore
 * 
 * Esempi:
 * x
 * y
 * 
 * Rappresenta l'utilizzo di una variabile all'interno di un'espressione
 */
public class NodeDeref extends NodeExpr{
	
    private NodeId id;

    public NodeDeref(NodeId id) {
        this.id = id;
    }

    public NodeId getId() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
    @Override
    public TypeDescriptor calcResType() {
        return id.calcResType();
    }

    @Override
    public String calcCodice() {

        Attributes attr =
            SymbolTable.lookup(id.getName());

        return "l" + attr.getRegistro() + " ";
    }
}
