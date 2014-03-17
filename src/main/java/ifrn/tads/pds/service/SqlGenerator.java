package ifrn.tads.pds.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Classe responsável por construir as queries de consultas
public class SqlGenerator {
	protected static final Logger logger = LoggerFactory.getLogger(SqlGenerator.class);
	private String fields, tableName, order, direction, sql, conditions;
	private Integer limit, offset;

	public String buildFind(String method, JSONObject parameters, String tableName){
		if(method == null || method.isEmpty() ) method = "all";
		//JSONObject conditions;

		this.tableName = tableName;

		if(parameters.length() > 0){
			//for(int i = 0; i < parameters.length(); i++){}
			// TODO: implementar em forma de array
			if( parameters.has("conditions") && parameters.getString("conditions") != null )
				this.conditions = parameters.getString("conditions");
			else
				this.conditions = null;

			if( parameters.has("fields") && parameters.getString("fields") != null) {
				this.fields = parameters.getString("fields");
			}else{
				this.fields = "*";
			}

			Integer offset_page = 0;

			if( parameters.has("limit") )  this.limit   = parameters.getInt("limit");
			if( parameters.has("order") )  this.order   = parameters.getString("order");
			if( parameters.has("offset") ) {
				offset_page = parameters.getInt("offset");
				if(offset_page == null || offset_page < 0) offset_page = 0;
			}

			if(this.limit == null){ this.limit = 10; }
			this.offset  = offset_page * this.limit;
		}

		if( method == "all" ) this.sql   = this.buildFindAll();
		if( method == "list" ) this.sql  = this.buildFindList();
		if( method == "first" ) this.sql = this.buildFindFirst();
		if( method == "count" ) this.sql = this.buildFindCount();

		return this.sql;
	}

	public String buildInsert(String fields, String values, String tableName){
		if( fields == null || values == null || tableName == null || fields.isEmpty() ||  values.isEmpty() || tableName.isEmpty() ){
			return "";
		}

		return "INSERT INTO " + tableName + " (" + fields + ") VALUES ("+ values +")";
	}

	public String buildUpdate(String fields, String values, String conditions, String tableName){
		String[] tmp_fields = new String[fields.length()], tmp_values = new String[values.length()];
		
		if(!fields.isEmpty()) tmp_fields = fields.split(","); 
		if(!fields.isEmpty()) tmp_values = values.split(","); 
		if(tmp_fields.length != tmp_values.length) return "";
		
		return this.buildUpdate(tmp_fields, tmp_values, conditions, tableName);
	}

	public String buildUpdate(String[] fields, String[] values, String conditions, String tableName){
		if( fields == null || values == null || tableName == null || fields.length == 0 ||  values.length == 0 || tableName.isEmpty() ){
			return "";
		}
		String set_fields = "";
		for (int i = 0; i < fields.length; i++) {
			set_fields += fields[i] + " = " + values[i];
			if(i+1 < fields.length){
				set_fields += ", ";
			}
		}

		String sql_output = "UPDATE " + tableName + " SET " + set_fields;

		if(conditions != null && !conditions.isEmpty()){
			sql_output += " WHERE " + conditions;
		}

		return sql_output;
	}

	// TODO: usar arrays de campo => valor
	private void buildFindConditions(){
		if(this.conditions != null && !this.conditions.isEmpty()){
			this.sql += " WHERE "+ this.conditions;
		}
	}

	private String buildFindAll(){
		this.sql = "SELECT "+ this.fields +" FROM "+ this.tableName;

		this.buildFindConditions();
		
		if(this.order != null && this.direction != null){
			this.sql += " ORDER BY "+ this.order + " "+ this.direction;
		}

		if(this.limit != null && this.offset != null){
			this.sql += " LIMIT "+ this.offset +", "+ this.limit;
		}
		return this.sql;
	}

	private String buildFindList(){
		// TODO: fazer reconhecimento de campos para limitar o resultado à PrimaryKey => DisplayField
		//if(this.fields == null || this.fields == "*"){ return ""; }

		this.sql = "SELECT "+ this.fields +" FROM "+ this.tableName;
		if(this.order != null && this.direction != null){
			this.sql += " ORDER BY "+ this.order + " "+ this.direction;
		}

		this.buildFindConditions();

		if(this.limit != null && this.offset != null){
			this.sql += " LIMIT "+ this.offset +", "+ this.limit;
		}
		return this.sql;
	}

	private String buildFindFirst(){
		this.sql = "SELECT "+ this.fields +" FROM "+ this.tableName;
		this.limit = 1;
		this.offset = 1;

		this.buildFindConditions();

		if(this.order != null && this.direction != null){
			this.sql += " ORDER BY "+ this.order + " "+ this.direction;
		}
		this.sql += " LIMIT "+ this.offset +", "+ this.limit;

		return this.sql;
	}
	private String buildFindCount(){
		// TODO: cabe ao método informar apenas um campo para contagem
		this.sql = "SELECT count("+ this.fields +") FROM "+ this.tableName;
		this.buildFindConditions();
		return this.sql;
	}
}
