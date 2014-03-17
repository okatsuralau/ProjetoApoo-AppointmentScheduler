package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Exam;
import ifrn.tads.pds.domain.Expertise;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("examService")
@Transactional
public class ExamService extends AppService<Exam> {

	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;

	public ExamService() {
		this.tableName = "exam";
	}

	// Insere no resultado da consulta, os objetos associados a este model
	protected void setAssociates(Exam exam){
		Expertise expertise = expertiseService.findBy("id", exam.getExpertise_id()+"");
		if(expertise != null){
			exam.setExpertise(expertise);
		}
	}

	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<Exam> list_exams = this.find("list", parameters, false);

		if(!list_exams.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (Exam exam : list_exams) {
				map_list.put(exam.getPrimaryKey()+"", exam.getDisplayField());
			}
		}
		return map_list;
	}

	public boolean add(Exam exam) {
		try{
			logger.debug("Adding new "+ this.tableName);
			String sql = this.getSqlUtil().buildInsert("title, expertise_id", "?, ?", this.tableName);

			if( getJdbcTemplate().update(sql, exam.getTitle(), exam.getExpertise_id()) != 1){
				// TODO: tentar exibir detalhes do erro
				logger.error("Não foi possível salvar o "+ this.tableName);
				return false;
			}else{
				List<Exam> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}

				exam.setId(this.lastInsertId());
				this.putCacheByAction("all", exam);

				//logService.add(new Log(1, civilstatus.getId(), this.tableName, "add", ""));
				return true;
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
	}

	public boolean edit(Exam exam) {
		try{
			logger.debug("Editing existing exam");

			String sql = this.getSqlUtil().buildUpdate("title, expertise_id", "?, ?", "id = ?", this.tableName);
			
			if( getJdbcTemplate().update(sql, exam.getTitle(), exam.getExpertise_id(), exam.getId()) != 1){
				logger.error("Não foi possível atualizar o exam");
				return false;
			}else{
				// atualiza o cache local
				// TODO: atualizar em todos os caches possiveis
				List<Exam> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(exam, cache_by_action);
					if(indice > 0){
						cache_by_action.set(indice, exam);
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

	public boolean delete(Exam entry) {
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
				List<Exam> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(Exam compareTo, List<Exam> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
