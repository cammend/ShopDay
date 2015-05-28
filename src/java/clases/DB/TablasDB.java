package clases.DB;

public class TablasDB {
	//tabla de usuarios
	public static final String USUARIOS = "usuario";
	private static final String[] USUARIOS_CAMPOS = {"idusuario","nombrecompleto","password","Edad"};
	//tabla de abarrotes
	public static final String ABARROTES = "abarrotes";
	private static final String[] ABARROTES_CAMPOS = {"id","descripcionabarrote","marca","Precio","cantidadplan","idcategoria","idunidadmedida","idusuario"};
	//tabla de listas de usario
	public static final String LISTAS = "lista";
	private static final String[] LISTAS_CAMPOS = {"idlista","descripcionlista","fecha","edad","estado","idusuario"};
	//tabla de detalles de listas
	public static final String DETALLE_LISTA = "detallelista";
	private static final String[] DETALLE_LISTA_CAMPOS = {"idlista","idabarrote","cantidadplan","cantidadcompra","abastecido"};
	//tabla de categorias
	public static final String CATEGORIA = "categoria";
	private static final String[] CATEGORIA_CAMPOS = {"idcategoria","descripcion"};
	//tabla de unidades de medida
	public static final String MEDIDA = "unidadmedida";
	private static final String[] MEDIDA_CAMPOS = {"idunidadMedida","Descripcion"};
	
	public static String[] getCamposTabla(String tabla){
		switch(tabla){
			case USUARIOS: return USUARIOS_CAMPOS;
			case ABARROTES: return ABARROTES_CAMPOS;
			case LISTAS: return LISTAS_CAMPOS;
                        case DETALLE_LISTA: return DETALLE_LISTA_CAMPOS;
                        case CATEGORIA: return CATEGORIA_CAMPOS;
                        case MEDIDA: return MEDIDA_CAMPOS;
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
