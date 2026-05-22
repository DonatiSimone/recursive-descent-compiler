package ast;

import typechecking.TipoTD;
import typechecking.TypeDescriptor;

/*
 * Nodo costante
 */
public class NodeCost extends NodeExpr {

    private String value;
    private LangType type;

    public NodeCost(String value, LangType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public LangType getType() {
        return type;
    }

    @Override
    public String toString() {
        //return "COST: " + value() + ", " + type();
    	return value;
    }
    
    @Override
    public TypeDescriptor calcResType() {
    	
    	if(type == LangType.INT) {
    		return new TypeDescriptor(TipoTD.INT);
    	}
    	
    	return new TypeDescriptor(TipoTD.FLOAT);
    }

	@Override
	public String calcCodice() {
		return value + " ";
	}
}