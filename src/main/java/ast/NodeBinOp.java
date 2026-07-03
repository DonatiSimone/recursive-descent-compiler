package ast;

import typechecking.TipoTD;
import typechecking.TypeDescriptor;


/*
 * Nodo operazione binaria
 * 
 * Esempi:
 * 2 + 7
 * a * b
 * 
 * Contiene:
 * - espressione dx
 * - operatore
 * - espressione sx
 */
public class NodeBinOp extends NodeExpr {

    private LangOper op;
    private NodeExpr left;
    private NodeExpr right;

    public NodeBinOp(NodeExpr left, LangOper op, NodeExpr right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public LangOper getOp() {
        return op;
    }

    public NodeExpr getLeft() {
        return left;
    }

    public NodeExpr getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
    
    @Override
    public TypeDescriptor calcResType() {

        TypeDescriptor leftTD =
            left.calcResType();

        TypeDescriptor rightTD =
            right.calcResType();

        // propagazione errori

        if(leftTD.getTipo() == TipoTD.ERROR) {
            return leftTD;
        }

        if(rightTD.getTipo() == TipoTD.ERROR) {
            return rightTD;
        }
        
        
        // se è una divisione con almeno un float,
        // promuovi l'operatore a DIV_FLOAT
        
        if(op == LangOper.DIV &&
     		   (leftTD.getTipo() == TipoTD.FLOAT ||
     		    rightTD.getTipo() == TipoTD.FLOAT)) {

     		    op = LangOper.DIV_FLOAT;
        }

        
        // float prevale

        if(leftTD.getTipo() == TipoTD.FLOAT ||
           rightTD.getTipo() == TipoTD.FLOAT) {

            return new TypeDescriptor(TipoTD.FLOAT);
        }


        return new TypeDescriptor(TipoTD.INT);
        
    }

    @Override
    public String calcCodice() {

        String leftCode =
            left.calcCodice();

        String rightCode =
            right.calcCodice();

        String opCode = switch(op) {

            case PLUS -> "+ ";
            case MINUS -> "- ";
            case TIMES -> "* ";
            case DIV -> "/ ";
            case DIV_FLOAT -> "/ ";

            default -> "";
        };

        /* divisione floating point
         * 
         * "5 k / 0 k "
         * Imposta precisione decimale a 5 cifre per la divisione
         * e successivamente ripristina la precisione intera
         */

        if(op == LangOper.DIV_FLOAT) {

            return leftCode +
            		rightCode +
            		"5 k / 0 k ";
        }

        return leftCode +
               rightCode +
               opCode;
    }
}