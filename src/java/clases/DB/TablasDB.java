package clases.DB;

public class TablasDB {
	//tabla de usuarios
	public static final String USUARIOS = "Usuarios";
	private static final String[] USUARIOS_CAMPOS = {"id","correo","nombre","alias","fecha","password"};
	//tabla de abarrotes
	public static final String ABARROTES = "abarrotes";
	private static final String[] ABARROTES_CAMPOS = {"id","nombre"};
	//tabla de 
	public static final String LISTAS = "listas";
	private static final String[] LISTAS_CAMPOS = {"id","nombre"};
		
	public static String[] getCamposTabla(String tabla){
		switch(tabla){
			case USUARIOS: return USUARIOS_CAMPOS;
			case ABARROTES: return ABARROTES_CAMPOS;
			case LISTAS: return LISTAS_CAMPOS;
		}
		return null;
	}
	
	public static String getCampoTabla(String tabla, int index){
		switch(tabla){
			case USUARIOS: if(index<=USUARIOS_CAMPOS.length && index>=0){
				return USUARIOS_CAMPOS[index];
			};
			case ABARROTES: if(index<=ABARROTES_CAMPOS.length && index>=0){
				return ABARROTES_CAMPOS[index];
			};
			case LISTAS: if(index<=LISTAS_CAMPOS.length && index>=0){
				return LISTAS_CAMPOS[index];
			};
		}
		return null;
	}
}
