package ifrn.tads.pds.domain;

//import java.lang.reflect.Method;
//import org.apache.commons.lang.WordUtils;
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

public abstract class AppDomain<T>{
	
	protected String primaryKeyField;
	protected String displayField;
	
	// TODO: implementar e aprimorar esta classe
	// ROADMAP: setar os métodos getPrimaryKey() e getDisplayField() para retornar os seus valores correspondentes.
	
	
	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	public int getId() {
		return id;
	}
	
	// usado no find("list")
	// TODO: tentar setar dinamicamente
	public int getPrimaryKey() {
		if(this.primaryKeyField == null || this.primaryKeyField.isEmpty()){
			this.primaryKeyField = "id";
		}
		
		return this.hasMethod("getId") ? this.getId() : null;
	}
	public String getDisplayField() {
		String method_name = "get" + WordUtils.capitalizeFully(this.displayField).replaceAll("\\s+", "");
		
		if( this.hasMethod(method_name) )
			return Class.forName(this.getClass().toString()).getDeclaredMethod(method_name);
		
		//return this.getTitle();
	}
	
	private boolean hasMethod(String method){
		Method[] methods = this.getClass().getMethods();
		for (Method method_class : methods) {
			if(method_class.getName().equalsIgnoreCase(method) ){
				return true;
			}
		}
		return false;
	}*/
}
