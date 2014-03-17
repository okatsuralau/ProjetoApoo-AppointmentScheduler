package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Patient;
import ifrn.tads.pds.domain.Individual;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("patientService")
@Transactional
public class PatientService extends AppService<Patient> {

	@Resource(name="individualService")
	private IndividualService individualService;

	public PatientService() {
		this.tableName = "patient";
	}

	// Insere no resultado da consulta, os objetos associados a este model
	protected void setAssociates(Patient patient){
		Individual individual = individualService.findBy("id", patient.getIndividual_id()+"");
		if(individual != null){
			patient.setIndividual(individual);
		}
	}

	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<Patient> list_patients = this.find("list", parameters, false);

		if(!list_patients.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (Patient patient : list_patients) {
				map_list.put(patient.getPrimaryKey()+"", patient.getDisplayField());
			}
		}
		return map_list;
	}
	
	// TODO: antes de criar um novo ou recuperar um individuo já existente, verificar se já está cadastrado como paciente
	// TODO: setar o campo individual_id como UNIQUE
	public boolean add(Patient patient) {
		try{
			int lastID = -1;

			if(!this.individualService.existsBy("cpf", patient.getCpf())){
				logger.info("Adding new patient");

				this.individualService.add(new Individual(
					patient.getSkin_id(), patient.getEducationlevel_id(), patient.getCivilstatus_id(), patient.getCpf(), patient.getFirst_name(), patient.getLast_name(), patient.getBirthday(), patient.getEmail() )
				);
				lastID = this.lastInsertId();
			}else{
				logger.info("Recuperando individuo existente");
				Individual existent_individual = this.individualService.findBy("cpf", patient.getCpf());
				lastID = existent_individual.getId();
			}

			if(lastID == -1){
				logger.error("Não foi possível salvar o Doctor");
				return false;
			}

			// Add new patient
			String sql = this.getSqlUtil().buildInsert("individual_id", "?", this.tableName);
			if( getJdbcTemplate().update(sql, lastID) != 1){
				logger.error("Não foi possível salvar o Patient");
				return false;
			}else{
				List<Patient> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}

				patient.setId(this.lastInsertId());
				this.putCacheByAction("all", patient);

				//logService.add(new Log(1, this.getLastInsertId(), this.tableName, "add", ""));
				return true;
			}
		}
		catch (InvalidResultSetAccessException e)
		{
			logger.trace( "InvalidResultSetAccessException: " + e.getMessage());
			return false;
		}
		catch (DataAccessException e)
		{
			logger.trace( "DataAccessException: " + e.getMessage());
			return false;
		}
	}

	public boolean edit(Patient patient) {
		try{
			logger.info("Editing existing patient");

			if(!this.existsBy("id", patient.getId() + "")){
				logger.error("O "+ this.tableName +" de ID = "+ patient.getId() +" não existe");
				return false;
			}

			// Update Individual
			if(!this.individualService.edit(
				new Individual(
					patient.getIndividual_id(),
					patient.getSkin_id(),
					patient.getEducationlevel_id(),
					patient.getCivilstatus_id(),
					patient.getCpf(),
					patient.getFirst_name(),
					patient.getLast_name(),
					patient.getBirthday(),
					patient.getEmail()
				)
			)){
				logger.error("Não foi possível atualizar o patient");
				return false;
			}else{
				// atualiza o cache local
				// TODO: atualizar em todos os caches possiveis
				List<Patient> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(patient, cache_by_action);
					if(indice > 0){
						cache_by_action.set(indice, patient);
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

	public boolean delete(Patient entry) {
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
				List<Patient> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(Patient compareTo, List<Patient> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
