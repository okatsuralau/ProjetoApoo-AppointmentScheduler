package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Datebook;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("datebookService")
@Transactional
public class DatebookService extends AppService<Datebook> {

	public DatebookService(){
		this.tableName = "datebook";
	}

	public boolean add(Datebook datebook) {
		try{
			logger.debug("Adding new "+ this.tableName);
			String sql = this.getSqlUtil().buildInsert(
				"availability_id, patient_id, exam_id, health_plan_id",
				"?,?,?,?",
				this.tableName
			);

			if( getJdbcTemplate().update(sql,
					datebook.getAvailability_id(),
					datebook.getPatient_id(),
					datebook.getExam_id(),
					datebook.getHealth_plan_id()
				) != 1){
				logger.error("Não foi possível salvar o "+ this.tableName);
				return false;
			}else{
				List<Datebook> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}
				datebook.setId(this.lastInsertId());
				this.putCacheByAction("all", datebook);

				return true;
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
	}

	/*public boolean edit(Datebook datebook) {
		logger.debug("Editing existing datebook");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, datebook.getTitle(), datebook.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}*/
	
	public boolean delete(Datebook entry) {
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
				List<Datebook> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(Datebook compareTo, List<Datebook> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
