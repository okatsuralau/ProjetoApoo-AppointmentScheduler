package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Civilstatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("civilstatusService")
@Transactional
public class CivilstatusService extends AppService<Civilstatus> {

	public CivilstatusService(){
		this.tableName = "civilstatus";
	}

	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<Civilstatus> list_civilstatuses = this.find("list", parameters, false);

		if(!list_civilstatuses.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (Civilstatus civilstatus : list_civilstatuses) {
				map_list.put(civilstatus.getPrimaryKey()+"", civilstatus.getDisplayField());
			}
		}
		return map_list;
	}

	public boolean add(Civilstatus civilstatus) {
		try{
			logger.debug("Adding new "+ this.tableName);
			
			String sql = this.getSqlUtil().buildInsert("title", "?", this.tableName);

			if( getJdbcTemplate().update(sql, civilstatus.getTitle()) != 1){
				// TODO: tentar exibir detalhes do erro
				logger.error("Não foi possível salvar o "+ this.tableName);
				return false;
			}else{
				List<Civilstatus> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}

				civilstatus.setId(this.lastInsertId());
				this.putCacheByAction("all", civilstatus);

				//logService.add(new Log(1, civilstatus.getId(), this.tableName, "add", ""));
				return true;
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
	}

	public boolean edit(Civilstatus civilstatus) {
		try{
			logger.debug("Editing existing civilstatus");

			String sql = this.getSqlUtil().buildUpdate("title", "?", "id = ?", this.tableName);

			if( getJdbcTemplate().update(sql, civilstatus.getTitle(), civilstatus.getId()) != 1){
				logger.error("Não foi possível atualizar o civilstatus");
				return false;
			}else{
				// atualiza o cache local
				// TODO: atualizar em todos os caches possiveis
				List<Civilstatus> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(civilstatus, cache_by_action);
					if(indice > 0){
						cache_by_action.set(indice, civilstatus);
					}
				}else{
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}
				
				//logService.add(new Log(1, this.getLastInsertId(), this.tableName, "add", ""));
				return true;
			}
		}
		catch (InvalidResultSetAccessException e){
			logger.trace(e.getMessage());
			return false;
		}
	}

	public boolean delete(Civilstatus entry) {
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
				List<Civilstatus> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(Civilstatus compareTo, List<Civilstatus> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
