package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.HealthPlan;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("healthPlanService")
@Transactional
public class HealthPlanService extends AppService<HealthPlan> {

	public HealthPlanService() {
		this.tableName = "health_plan";
	}

	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<HealthPlan> list_healthPlans = this.find("list", parameters, false);

		if(!list_healthPlans.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (HealthPlan healthPlan : list_healthPlans) {
				map_list.put(healthPlan.getPrimaryKey()+"", healthPlan.getDisplayField());
			}
		}
		return map_list;
	}

	public boolean add(HealthPlan healthPlan) {
		try{
			logger.debug("Adding new "+ this.tableName);

			String sql = this.getSqlUtil().buildInsert("title", "?", this.tableName);

			if( getJdbcTemplate().update(sql, healthPlan.getTitle()) != 1){
				// TODO: tentar exibir detalhes do erro
				logger.error("Não foi possível salvar o "+ this.tableName);
				return false;
			}else{
				List<HealthPlan> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}
				
				healthPlan.setId(this.lastInsertId());
				this.putCacheByAction("all", healthPlan);
				return true;
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
	}

	public boolean edit(HealthPlan healthPlan) {
		try{
			logger.debug("Editing existing healthPlan");

			String sql = this.getSqlUtil().buildUpdate("title", "?", "id = ?", this.tableName);
			
			if( getJdbcTemplate().update(sql, healthPlan.getTitle(), healthPlan.getId()) != 1){
				logger.error("Não foi possível atualizar o healthPlan");
				return false;
			}else{
				// atualiza o cache local
				// TODO: atualizar em todos os caches possiveis
				List<HealthPlan> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(healthPlan, cache_by_action);
					if(indice > 0){
						cache_by_action.set(indice, healthPlan);
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

	public boolean delete(HealthPlan entry) {
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
				List<HealthPlan> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(HealthPlan compareTo, List<HealthPlan> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
