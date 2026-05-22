package typechecking;


/*
 * Descrittore di tipo utilizzato durante
 * l'analisi semantica.
 * 
 * Rappresenta il risultato del type checking
 * di espressioni, assegnamenti e operazioni.
 * 
 * Contiene:
 * - il tipo risultante
 * - eventuali messaggi di errore
 */
public class TypeDescriptor {

    private TipoTD tipo;
    private String msg;

    /*
     * Crea un TypeDescriptor valido
     * associato ad un tipo semantico.
     */
    public TypeDescriptor(TipoTD tipo) {
        this.tipo = tipo;
    }

    /*
     * Crea un TypeDescriptor di errore
     * contenente il messaggio diagnostico.
     */
    public TypeDescriptor(String msg) {
        this.tipo = TipoTD.ERROR;
        this.msg = msg;
    }

    public TipoTD getTipo() {
        return tipo;
    }

    public String getMsg() {
        return msg;
    }

    /*
     * Verifica la compatibilità tra tipi.
     * 
     * Regole:
     * - ERROR non è compatibile con nessun tipo
     * - tipi uguali sono compatibili
     * - INT è compatibile con FLOAT
     */
    public boolean compatible(TypeDescriptor td) {

        if(this.tipo == TipoTD.ERROR ||
           td.tipo == TipoTD.ERROR) {
            return false;
        }

        if(this.tipo == td.tipo) {
            return true;
        }

        if(this.tipo == TipoTD.FLOAT &&
           td.tipo == TipoTD.INT) {
            return true;
        }

        return false;
    }
}
