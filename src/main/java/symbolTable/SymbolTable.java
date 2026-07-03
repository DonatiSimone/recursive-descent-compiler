package symbolTable;

import java.util.HashMap;

import ast.LangType;


/*
 * Tabella globale dei simboli del compilatore.
 * 
 * Mantiene le informazioni sulle variabili
 * dichiarate nel programma
 * 
 * Implementata tramite membri statici
 * per consentire accesso condiviso
 * tra le diverse fasi del compilatore.
 */
public class SymbolTable {
	
	/*
	 * Attributi associati ad un identificatore
	 * nella Symbol Table
	 * 
	 * Contiene:
	 * - tipo della variabile
	 * - registro associato nella code generation
	 */
	public static class Attributes{
		
		private LangType tipo;
		private char registro;
		
		/*
		 * Crea attributi con solo il tipo.
		 */
		public Attributes(LangType tipo) {
			this.tipo = tipo;
		}

		public LangType getType() {
			return tipo;
		}

		public char getRegistro() {
			return registro;
		}
		
		/*
		 * Associa il registro dc all'identificatore.
		 *
		 * Durante il type checking gli attributi vengono
		 * creati con le sole informazioni di tipo.
		 * In fase di code generation il registro viene
		 * assegnato successivamente completando la stessa
		 * entry della Symbol Table.
		 */
		public void setRegistro(char registro) {
		    this.registro = registro;
		}
		
	}
	
	private static HashMap<String, Attributes> table;
	
	/*
	 * Inizializza la Symbol Table.
	 */
	public static void init() {
		table = new HashMap<>();
	}
	
    /*
     * Inserisce un identificatore nella Symbol Table.
     * 
     * Ritorna false se l'identificatore
     * è già presente.
     */
    public static boolean enter(String id, Attributes entry) {

        if(table.containsKey(id)) {
            return false;
        }

        table.put(id, entry);
        return true;
    }
    

    /*
     * Cerca un identificatore nella Symbol Table
     * e ne restituisce gli attributi associati.
     */
    public static Attributes lookup(String id) {
        return table.get(id);
    }
    
    
    /*
     * Restituisce il numero di simboli presenti.
     */
    public static int size() {
        return table.size();
    }
    
    public static String toStr() {
        return table.toString();
    }
}
