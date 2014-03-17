package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Expertise;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("expertiseService")
@Transactional
public class ExpertiseService extends AppService<Expertise> {

	public ExpertiseService() {
		this.tableName = "expertise";
	}
	
	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<Expertise> list_expertises = this.find("list", parameters);
		
		if(list_expertises != null && !list_expertises.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (Expertise expertise : list_expertises) {
				map_list.put(expertise.getPrimaryKey()+"", expertise.getDisplayField());
			}
		}
		return map_list;
	}

	public boolean add(Expertise expertise) {
		try{
			logger.debug("Adding new expertise");

			String sql = this.getSqlUtil().buildInsert("title", "?", this.tableName);
			
			if( getJdbcTemplate().update(sql, expertise.getTitle()) != 1){
				// TODO: tentar exibir detalhes do erro
				logger.error("Não foi possível salvar o civilstatus");
				return false;
			}else{
				List<Expertise> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}

				expertise.setId(this.lastInsertId());
				this.putCacheByAction("all", expertise);
				//logService.add(new Log(1, this.getLastInsertId(), this.tableName, "add", ""));
				return true;
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
	}

	public boolean edit(Expertise expertise) {
		try{
			logger.debug("Editing existing expertise");

			String sql = this.getSqlUtil().buildUpdate("title", "?", "id = ?", this.tableName);

			if( getJdbcTemplate().update(sql, expertise.getTitle(), expertise.getId()) != 1){
				logger.error("Não foi possível atualizar o civilstatus");
				return false;
			}else{
				// atualiza o cache local
				// TODO: atualizar em todos os caches possiveis
				List<Expertise> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(expertise, cache_by_action);
					if(indice > 0){
						cache_by_action.set(indice, expertise);
					}
				}else{
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}
				//logService.add(new Log(1, this.getLastInsertId(), this.tableName, "add", ""));
				return true;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean delete(Expertise entry) {
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
				List<Expertise> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(Expertise compareTo, List<Expertise> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
