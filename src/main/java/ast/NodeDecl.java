package ast;

import codeGeneration.Registri;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;
import typechecking.TipoTD;
import typechecking.TypeDescriptor;


/*
 * Nodo dichiarazione variabile
 */
public class NodeDecl extends NodeDecSt{
	
	private NodeId id;
	private LangType type;
	private NodeExpr init;

	
	public NodeDecl(NodeId id, LangType type, NodeExpr init) {
		this.id = id;
		this.type = type;
		this.init = init;
	}
	
	public NodeId getId() {
		return id;
	}
	
	public LangType getType() {
		return type;
	}
	
	public NodeExpr getInit() {
		return init;
	}
	
	@Override
	public String toString() {
		if (init==null) {
			return "DECL: " + getId() + ", " + getType();
		}
		return "DECL: " + getId() + ", " + getType() + ", " + getInit();
	}
	
	@Override
	public TypeDescriptor calcResType() {

	    boolean ok =
	        SymbolTable.enter(
	            id.getName(),
	            new Attributes(type)
	        );

	    if(!ok) {

	        return new TypeDescriptor(
	            "Variabile " + id + " già dichiarata"
	        );
	    }

	    // caso: int a; (dichiarazione senza inizializzazione)
	    if(init == null) {
	        return new TypeDescriptor(TipoTD.OK);
	    }

	    TypeDescriptor exprTD =
	        init.calcResType();

	    TypeDescriptor declTD;

	    if(type == LangType.INT) {
	        declTD =
	            new TypeDescriptor(TipoTD.INT);
	    }
	    else {
	        declTD =
	            new TypeDescriptor(TipoTD.FLOAT);
	    }

	    if(!declTD.compatible(exprTD)) {

	        return new TypeDescriptor(
	            "Tipo inizializzazione incompatibile"
	        );
	    }

	    return new TypeDescriptor(TipoTD.OK);
	}

	@Override
	public String calcCodice() {
		
		Attributes attr =
			    SymbolTable.lookup(id.getName());

	    char reg =
	        Registri.newRegister();

	    if(reg == '\0') {
	        log = "Registri esauriti";
	        return "";
	    }
	    
	    
	    /*
	     * Durante il type checking la variabile viene inserita
	     * nella Symbol Table con il solo tipo.
	     *
	     * In code generation la stessa entry viene completata
	     * associando il registro dc assegnato.
	     */
	    attr.setRegistro(reg);
	    
	    // caso: int a; (dichiarazione senza inizializzazione)
	    if(init == null) {
	        return "";
	    }

	    return init.calcCodice() + "s" + reg + " ";
	}
}