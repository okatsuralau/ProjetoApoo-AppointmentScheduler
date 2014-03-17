package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Doctor;
import ifrn.tads.pds.domain.Expertise;
import ifrn.tads.pds.domain.Individual;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("doctorService")
@Transactional
public class DoctorService extends AppService<Doctor> {
	@Resource(name="individualService")
	private IndividualService individualService;

	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;

	public DoctorService() {
		this.tableName = "doctor";
	}

	// Insere no resultado da consulta, os objetos associados a este model
	protected void setAssociates(Doctor doctor){
		Individual individual = individualService.findBy("id", doctor.getIndividual_id()+"", null, true, false);
		if(individual!= null){
			doctor.setIndividual(individual);
		}
		
		Expertise expertise = expertiseService.findBy("id", doctor.getExpertise_id()+"", null, false, false);
		if(expertise != null){
			doctor.setExpertise(expertise);
		}
	}
	
	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<Doctor> list_doctors = this.find("list", parameters);
		
		if(list_doctors != null && !list_doctors.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (Doctor doctor : list_doctors) {
				map_list.put(doctor.getPrimaryKey()+"", doctor.getDisplayField());
			}
		}
		return map_list;
	}

	// TODO: não escrever os SQLs manualmente. Montar uma classe pra cuidar disso
	public boolean add(Doctor doctor) {
		try{
			logger.info("Adding new doctor");
			
			int lastID = -1;
			
			// Add individual
			if(this.individualService.add(
				new Individual(doctor.getSkin_id(), doctor.getEducationlevel_id(), doctor.getCivilstatus_id(), doctor.getCpf(), doctor.getFirst_name(), doctor.getLast_name(), doctor.getBirthday(), doctor.getEmail() )
			)){
				lastID = this.lastInsertId();
			}
	
			if(lastID == -1){
				logger.error("Não foi possível salvar o Doctor");
				return false;
			}
			// Add Doctor
		
			String sql = this.getSqlUtil().buildInsert("individual_id, expertise_id", "?, ?", this.tableName);
			if( getJdbcTemplate().update(sql, lastID, doctor.getExpertise_id()) != 1){
				logger.error("Não foi possível salvar o Doctor");
				return false;
			}else{
				List<Doctor> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}
				
				doctor.setId(this.lastInsertId());
				this.putCacheByAction("all", doctor);

				//logService.add(new Log(1, this.getLastInsertId(), this.tableName, "add", ""));
				return true;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean edit(Doctor doctor) {
		try{
			logger.info("Editing existing doctor");

			// Update Individual
			if( !this.individualService.edit(
				new Individual(
					doctor.getIndividual_id(),
					doctor.getSkin_id(),
					doctor.getEducationlevel_id(),
					doctor.getCivilstatus_id(),
					doctor.getCpf(),
					doctor.getFirst_name(),
					doctor.getLast_name(),
					doctor.getBirthday(),
					doctor.getEmail()
				)
			)){
				logger.error("Não foi possível atualizar o doctor");
				return false;
			}else{
				// Update doctor
				String sql = this.getSqlUtil().buildUpdate("expertise_id", "?", "id = ?", this.tableName);
	
				if( getJdbcTemplate().update(sql, doctor.getExpertise_id(), doctor.getId()) != 1){
					logger.error("Não foi possível atualizar o doctor");
					return false;
				}else{
					// atualiza o cache local
					// TODO: atualizar em todos os caches possiveis
					List<Doctor> cache_by_action = this.getCacheByAction("all");
					if(cache_by_action != null && !cache_by_action.isEmpty()){
						int indice = getArrayListIndex(doctor, cache_by_action);
						if(indice > 0){
							cache_by_action.set(indice, doctor);
						}
					}else{
						// Popula o cache local, caso esteja vazio
						this.find("all");
					}
					//logService.add(new Log(1, this.getLastInsertId(), this.tableName, "add", ""));
					return true;
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean delete(Doctor entry) {
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
				List<Doctor> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(Doctor compareTo, List<Doctor> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
