package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.User;
import ifrn.tads.pds.domain.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("logService")
@Transactional
public class LogService extends AppService<Log> {

	@Resource(name="userService")
	private UserService userService;

	public LogService() {
		this.tableName = "log";
	}

	// TODO: criar captura de log por campos e/ou atividades especificas

	// Insere no resultado da consulta, os objetos associados a este model
	protected void setAssociates(Log log){
		User user = userService.findBy("id", log.getUser_id()+"");
		if(user != null){
			log.setUser(user);
		}
	}

	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<Log> list_logs = this.find("list", parameters, false);

		if(!list_logs.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (Log log : list_logs) {
				map_list.put(log.getPrimaryKey()+"", log.getDisplayField());
			}
		}
		return map_list;
	}

	public boolean add(Log log) {
		try{
			logger.debug("Adding new log");
			
			String sql = this.getSqlUtil().buildInsert(
				"user_id, record_id, model, activity, description, ip, hostname", 
				"?, ?, ?, ?, ?, ?, ?", 
				this.tableName
			);
			
			InetAddress ip = InetAddress.getLocalHost();
			getJdbcTemplate().update(
				sql,
				log.getUser_id(),
				log.getRecord_id(),
				log.getModel(),
				log.getActivity(),
				log.getDescription(),
				ip.getHostAddress(),
				ip.getHostName()
			);
			
			return true;
		}catch (UnknownHostException e) {
			//e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean delete(Log entry) {
		try{
			logger.info("Deleting existing entry from "+ this.tableName );
			
			String sql = "DELETE FROM "+ this.tableName +" WHERE id = ?";
			Object[] parameters = new Object[] { entry.getId() };
			
			if(getJdbcTemplate().update(sql, parameters) != 1){
				logger.error("Não foi possível deletar o registro");
				return false;
			}else{
				// atualiza o cache local
				// TODO: eliminar de todos os caches possiveis
				List<Log> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(entry, cache_by_action);
					if(indice > 0){
						cache_by_action.remove(indice);
					}
				}
			}
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}

	// Assume que os valores não são nulos
	private int getArrayListIndex(Log compareTo, List<Log> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
