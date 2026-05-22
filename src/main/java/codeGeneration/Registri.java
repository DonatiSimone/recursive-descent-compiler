package codeGeneration;


/*
 * Gestore dei registri dc.
 * 
 * Assegna un registro univoco ad ogni
 * variabile dichiarata.
 * 
 * Utilizza i registri:
 * a ... z
 */
public class Registri {
	
	private static char current = 'a';
	
	public static char newRegister() {
		if (current > 'z') {
			return '\0';
		}
		return current++;
	}
	
	public static void reset() {
		current = 'a';
	}
}
