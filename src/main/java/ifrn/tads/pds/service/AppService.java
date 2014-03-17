package ifrn.tads.pds.service;

import ifrn.tads.pds.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.GenericTypeResolver;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AppService<T> extends App {

	// Estudar como aplicar o recurso de GenericDAO: http://nurkiewicz.blogspot.com.br/2013/01/spring-data-jdbc-generic-dao.html

	private final Class<T> genericType; // registra o tipo da classe que está executando o método
	protected JdbcTemplate jdbcTemplate;
	protected String tableName = "";
	//protected int last_insert_id = -1;
	
	// Banco de dados local (verifica primeiro no ArrayList antes de requisitar ao banco)
	protected Map<String, List<T>> cached_results_by_action; // cache local para listagem geral -> find(all)
	protected Map<String, List<T>> cached_results_by_field;  // cache local para listagens customizadas -> findBy(campo, valor)
	
	protected JSONArray associatedModels;
	private SqlGenerator sqlUtil; // Helper para construção das queries

	@Resource(name="logService")
	protected LogService logService;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@SuppressWarnings("unchecked")
	public AppService(){
		this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AppService.class);
		this.sqlUtil = new SqlGenerator();
		//this.cached_results_by_action = new JSONObject();
		//this.cached_results_by_field = new JSONObject();
		this.cached_results_by_action = new HashMap<String, List<T>>();
		this.cached_results_by_field = new HashMap<String, List<T>>();
		this.associatedModels = new JSONArray();
	}

	public SqlGenerator getSqlUtil(){
		return this.sqlUtil;
	}

	// TODO: Utilizar JdbcDaoSupport
	// http://www.mkyong.com/spring/spring-jdbctemplate-jdbcdaosupport-examples/
	protected JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	/*public int getLastInsertId(){
		return this.last_insert_id;
	}*/
	public int lastInsertId(){
		int last_insert_id = getJdbcTemplate().queryForObject("SELECT last_insert_id()", Integer.class);
		logger.info("ID do último registro inserido: " + last_insert_id);
		return last_insert_id;
	}

	// Insere no resultado da consulta, os objetos associados a este model
	protected void setAssociates(T model){}
	
	public List<T> toList(JSONArray array) throws JSONException {
		 List<T> list = new ArrayList<T>();
		 for (int i = 0; i < array.length(); i++) {
			 list.add( genericType.cast(array.get(i)) );
		 }
		 return list;
	 }
	
	protected void putCacheByField(String field, T value){
		List<T> cache = this.getCacheByField(field);
		if(cache == null || cache.isEmpty()){
			// cria o indice
			cache = new ArrayList<T>();
			cache.add(value);
		}
		this.setCacheByField(field, cache);
	}
	protected void putCacheByAction(String field, T value){
		//this.cached_results_by_field.put(field, value);
		List<T> cache = this.getCacheByField(field);
		if(cache == null || cache.isEmpty()){
			// cria o indice
			cache = new ArrayList<T>();
			cache.add(value);
		}
		this.setCacheByField(field, cache);
	}
	// Armazena um cache para cada <field> 
	protected void setCacheByField(String field, List<T> value){
		this.cached_results_by_field.put(field, value);
	}
	// Armazena um cache para cada <method> 
	protected void setCacheByAction(String method, List<T> value){
		this.cached_results_by_action.put(method, (List<T>)value);
	}
	// Retorna um resultado cacheado, dado o seu campo identificador
	
	protected List<T> getCacheByField(String field){
		return this.cached_results_by_field.get(field) != null ? this.cached_results_by_field.get(field) : null;
	}
	// Retorna um resultado cacheado, dado a sua action identificadora
	protected List<T> getCacheByAction(String field){
		return this.cached_results_by_action.get(field) != null ? this.cached_results_by_action.get(field) : null;
	}
	
	/*
	 * Find Methods
	 *
	 * Efetua uma busca personalizada
	 * A query é montada dinamicamente de acordo com o tipo de busca desejada (all, first, list)
	 *
	 */
	public List<T> find(String method) {
		return this.find(method, new JSONObject(), true, true);
	}
	public List<T> find(String method, JSONObject parameters) {
		return this.find(method, parameters, true, true);
	}
	public List<T> find(String method, JSONObject parameters, boolean useAssociates) {
		return this.find(method, parameters, useAssociates, true);
	}
	public List<T> find(String method, JSONObject parameters, boolean useAssociates, boolean useCache) {
	    List<T> cache_by_action = this.getCacheByAction(method);

	    if( (cache_by_action == null || cache_by_action.isEmpty()) || !useCache ){
	        try{
	            logger.info("Retrieving all data from " + this.tableName);
	            
	            if(parameters == null) parameters = new JSONObject();
	            
	            if( !parameters.has("limit") || parameters.getInt("limit") <= 0 ){
	                parameters.put("limit", 10);
	            }

	            String sql = this.getSqlUtil().buildFind(method, parameters, this.tableName);

	            cache_by_action = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<T>( this.genericType ));
	            if(useAssociates && !cache_by_action.isEmpty()){
	                // agrega o resultado da busca de models associados ao resultado da consulta atual
	                for (T item : cache_by_action) {
	                    this.setAssociates(item);
	                }
	            }
	            
	            // registra o cache local
	            if(useCache)
	            	this.setCacheByAction(method, cache_by_action);

	            return cache_by_action;
	        }catch (EmptyResultDataAccessException e){
	            logger.debug("Nenhum registro encontrado para " + this.tableName);
	            e.printStackTrace();
	        }
	    }
	    
	    return cache_by_action;
	}
	
	// Deve ser implementado na subclasse
	
	public Map<String, String> findList() {
		return this.findList(new JSONObject());
	}
	public Map<String, String> findList(JSONObject parameters) {
		return null;
	}
	// Retorna o primeiro registro encontrado na lista
	public T findBy(String field, String value) {
		return this.findBy(field, value, new JSONObject(), false, true);
	}
	public T findBy(String field, String value, JSONObject parameters) {
		return this.findBy(field, value, parameters, false, true);
	}
	public T findBy(String field, String value, JSONObject parameters, boolean useAssociates) {
		return this.findBy(field, value, parameters, useAssociates, true);
	}
	public T findBy(String field, String value, JSONObject parameters, boolean useAssociates, boolean useCache) {
		List<T> cache_by_field = findListBy(field, value, parameters, useAssociates, useCache);
		T cached_result = null;
		if(cache_by_field != null && cache_by_field.size() >= 1){
			cached_result = cache_by_field.get(0);
		}
		//return cache_by_field != null ? cache_by_field.get(0) : null;
		return cached_result;
	}

	public List<T> findListBy(String field, String value, JSONObject parameters, boolean useAssociates, boolean useCache) {
	    List<T> cache_by_field = this.getCacheByField(field + value);

	    if((cache_by_field == null || cache_by_field.isEmpty()) || !useCache){
	        try{
	            logger.info("Retrieving data for "+ field + ": " + value + " from " + this.tableName);
	            
	            if(parameters == null) parameters = new JSONObject();
	            
	            if( !parameters.has("conditions") || parameters.getString("conditions") == null) {
	                parameters.put("conditions", field +" = "+ value);
	            }else{
	                // Concatena com condições já setadas
	                parameters.put(
	                    "conditions",
	                    parameters.getString("conditions") +
	                    " AND " +
	                    field + " = " + value
	                );
	            }

	            cache_by_field = this.find("all", parameters, useAssociates, false);

	            // registra o cache
	            this.setCacheByField(field + value, cache_by_field);
	            
	            return cache_by_field;
	        }catch (EmptyResultDataAccessException e){
	            logger.warn("Nenhum registro encontrado para "+ field +" = " + value + " from " + this.tableName);
	            e.printStackTrace();
	            return null;
	        }
	    }
	    return cache_by_field;
	}

	public T findFirst(){
		return findFirst(true);
	}
	public T findFirst(boolean useCache){
		T result = null;
		List<T> cached_by_action = this.getCacheByAction("all");

	    if( (cached_by_action == null || cached_by_action.isEmpty()) || !useCache ){
			try{
				logger.debug("Retrieving the first entry for " + this.tableName);
				String sql = "SELECT * FROM "+ this.tableName +" ORDER BY id ASC LIMIT 1";
				result = getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<T>(this.genericType));
			}catch (EmptyResultDataAccessException e){
				logger.debug("Nenhum registro encontrado ");
				logger.trace(e.getMessage());
		    }
		}else{
			// get the first item from local cache
			result = cached_by_action.get(0);
		}
		return result;
	}

	public T findLast(){
		return this.findLast(true);
	}
	public T findLast(boolean useCache){
		T result = null;
		List<T> cached_by_action = this.getCacheByAction("all");
		
		if( (cached_by_action == null || cached_by_action.isEmpty()) || !useCache ){
			try{
				logger.debug("Retrieving the last entry for " + this.tableName);
				String sql = "SELECT * FROM "+ this.tableName +" ORDER BY id DESC LIMIT 1";
				result = getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<T>(this.genericType));
			}catch (EmptyResultDataAccessException e){
				logger.debug("Nenhum registro encontrado ");
				logger.trace(e.getMessage());
		    }
		}else{
			// get the last item from local cache
			result = cached_by_action.get( cached_by_action.size() - 1 );
		}
		return result;
	}
	
	public int findCount() {
		logger.info("Retrieving count from "+ this.tableName );
		String sql = "SELECT COUNT(*) FROM " + this.tableName;
		return getJdbcTemplate().queryForObject(sql, Integer.class);
	}

	public boolean exists(int id) {
		logger.info("Checking if an entry exists in "+ this.tableName + " for id = " + id );
		String sql = "SELECT COUNT(*) FROM " + this.tableName +" WHERE id = ?";
		int total = getJdbcTemplate().queryForObject(sql, Integer.class, id);

		return total > 0;
	}

	public boolean existsBy(String field, String value) {
		int total = -1;
		try{
			logger.info("Checking if an entry exists in "+ this.tableName + " for " + field + " = " + value );
			String sql = "SELECT COUNT(*) FROM " + this.tableName +" WHERE "+ field +" = ?";

			total = getJdbcTemplate().queryForObject(sql, Integer.class, value);
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para "+ field +" = " + value);
			logger.trace(e.getMessage());
		}

		return total > 0;
	}

	// TODO: implementar esse método
	/*public boolean delete(int id) {
		logger.info("Deleting existing entry from "+ this.tableName );

		String sql = "DELETE FROM "+ this.tableName +" WHERE id = ?";
		Object[] parameters = new Object[] { id };

		try{
			getJdbcTemplate().update(sql, parameters);
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}*/
}
