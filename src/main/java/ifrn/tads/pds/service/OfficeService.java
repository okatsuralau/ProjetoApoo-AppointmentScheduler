package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Office;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("officeService")
@Transactional
public class OfficeService extends AppService<Office> {

	public OfficeService() {
		this.tableName = "office";
	}

	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<Office> list_offices = this.find("list", parameters, false);

		if(!list_offices.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (Office office : list_offices) {
				map_list.put(office.getPrimaryKey()+"", office.getDisplayField());
			}
		}
		return map_list;
	}

	public boolean add(Office office) {
		try{
			logger.debug("Adding new "+ this.tableName);
			
			String sql = this.getSqlUtil().buildInsert("title", "?", this.tableName);

			if( getJdbcTemplate().update(sql, office.getTitle()) != 1){
				// TODO: tentar exibir detalhes do erro
				logger.error("Não foi possível salvar o "+ this.tableName);
				return false;
			}else{
				List<Office> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}

				office.setId(this.lastInsertId());
				this.putCacheByAction("all", office);

				//logService.add(new Log(1, office.getId(), this.tableName, "add", ""));
				return true;
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
	}

	public boolean edit(Office office) {
		try{
			logger.debug("Editing existing office");

			String sql = this.getSqlUtil().buildUpdate("title", "?", "id = ?", this.tableName);

			if( getJdbcTemplate().update(sql, office.getTitle(), office.getId()) != 1){
				logger.error("Não foi possível atualizar o office");
				return false;
			}else{
				// atualiza o cache local
				// TODO: atualizar em todos os caches possiveis
				List<Office> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(office, cache_by_action);
					if(indice > 0){
						cache_by_action.set(indice, office);
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

	public boolean delete(Office entry) {
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
				List<Office> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(Office compareTo, List<Office> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
