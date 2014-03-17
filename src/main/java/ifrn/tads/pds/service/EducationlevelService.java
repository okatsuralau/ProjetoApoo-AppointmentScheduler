package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Educationlevel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("educationlevelService")
@Transactional
public class EducationlevelService extends AppService<Educationlevel> {

	public EducationlevelService() {
		this.tableName = "educationlevel";
	}

	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<Educationlevel> list_educationlevels = this.find("list", parameters, false);

		if(list_educationlevels != null && !list_educationlevels.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (Educationlevel educationlevel : list_educationlevels) {
				map_list.put(educationlevel.getPrimaryKey()+"", educationlevel.getDisplayField());
			}
		}
		return map_list;
	}

	public boolean add(Educationlevel educationlevel) {
		try{
			logger.debug("Adding new "+ this.tableName);
			String sql = this.getSqlUtil().buildInsert("title", "?", this.tableName);

			if( getJdbcTemplate().update(sql, educationlevel.getTitle()) != 1){
				logger.error("Não foi possível salvar o "+ this.tableName);
				return false;
			}else{
				List<Educationlevel> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}

				educationlevel.setId(this.lastInsertId());
				this.putCacheByAction("all", educationlevel);
				
				return true;
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
	}

	public boolean edit(Educationlevel educationlevel) {
		try{
			logger.debug("Editing existing "+ this.tableName);

			String sql = this.getSqlUtil().buildUpdate("title", "?", "id = ?", this.tableName);

			if( getJdbcTemplate().update(sql, educationlevel.getTitle(), educationlevel.getId()) != 1){
				logger.error("Não foi possível atualizar o educationlevel");
				return false;
			}else{
				// atualiza o cache local
				// TODO: atualizar em todos os caches possiveis
				List<Educationlevel> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(educationlevel, cache_by_action);
					if(indice > 0){
						cache_by_action.set(indice, educationlevel);
					}
				}else{
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}
				//logService.add(new Log(1, this.getLastInsertId(), this.tableName, "add", ""));
				return true;
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
	}

	public boolean delete(Educationlevel entry) {
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
				List<Educationlevel> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(entry, cache_by_action);
					if(indice > 0){
						cache_by_action.remove(indice);
					}
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	// Assume que os valores não são nulos
	private int getArrayListIndex(Educationlevel compareTo, List<Educationlevel> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
