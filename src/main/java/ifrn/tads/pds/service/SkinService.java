package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Skin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("skinService")
@Transactional
public class SkinService extends AppService<Skin> {

	public SkinService() {
		this.tableName = "skin";
	}

	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<Skin> list_skins = this.find("list", parameters, false);

		if(!list_skins.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (Skin skin : list_skins) {
				map_list.put(skin.getPrimaryKey()+"", skin.getDisplayField());
			}
		}
		return map_list;
	}

	public boolean add(Skin skin) {
		try{
			logger.debug("Adding new "+ this.tableName);
			
			String sql = this.getSqlUtil().buildInsert("title", "?", this.tableName);

			if( getJdbcTemplate().update(sql, skin.getTitle()) != 1){
				// TODO: tentar exibir detalhes do erro
				logger.error("Não foi possível salvar o "+ this.tableName);
				return false;
			}else{
				List<Skin> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}

				skin.setId(this.lastInsertId());
				this.putCacheByAction("all", skin);

				//logService.add(new Log(1, skin.getId(), this.tableName, "add", ""));
				return true;
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
	}

	public boolean edit(Skin skin) {
		try{
			logger.debug("Editing existing skin");

			String sql = this.getSqlUtil().buildUpdate("title", "?", "id = ?", this.tableName);

			if( getJdbcTemplate().update(sql, skin.getTitle(), skin.getId()) != 1){
				logger.error("Não foi possível atualizar o skin");
				return false;
			}else{
				// atualiza o cache local
				// TODO: atualizar em todos os caches possiveis
				List<Skin> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(skin, cache_by_action);
					if(indice > 0){
						cache_by_action.set(indice, skin);
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

	public boolean delete(Skin entry) {
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
				List<Skin> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(Skin compareTo, List<Skin> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
