package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Civilstatus;
import ifrn.tads.pds.domain.Educationlevel;
import ifrn.tads.pds.domain.Individual;
import ifrn.tads.pds.domain.Skin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("individualService")
@Transactional
public class IndividualService extends AppService<Individual> {

	@Resource(name="skinService")
	private SkinService skinService;

	@Resource(name="educationlevelService")
	private EducationlevelService educationlevelService;

	@Resource(name="civilstatusService")
	private CivilstatusService civilstatusService;

	public IndividualService() {
		this.tableName = "individual";
	}
	
	// Insere no resultado da consulta, os objetos associados a este model
	protected void setAssociates(Individual individual){
		Skin skin = skinService.findBy("id", individual.getSkin_id()+"");
		if(skin != null){
			individual.setSkin(skin);
		}

		Educationlevel educationlevel = educationlevelService.findBy("id", individual.getEducationlevel_id()+"");
		if(educationlevel != null){
			individual.setEducationlevel(educationlevel);
		}

		Civilstatus civilstatus = civilstatusService.findBy("id", individual.getCivilstatus_id()+"");
		if(educationlevel != null){
			individual.setCivilstatus(civilstatus);
		}
	}

	public boolean add(Individual individual) {
		//logger.debug("Adding new individual");
		// TODO: criar forma de chamar tabela das entidades relacionadas
		if(individual == null){
			logger.error("Nenhum dado foi setado");
			return false;
		}
		try{
			/*String sql = "INSERT INTO  "+ this.tableName
					+" (skin_id, educationlevel_id, civilstatus_id, cpf, first_name, last_name, birthday, email) VALUES (?,?,?,?,?,?,?,?)";*/
			
			String sql = this.getSqlUtil().buildInsert(
						"skin_id, educationlevel_id, civilstatus_id, cpf, first_name, last_name, birthday, email", 
						"?,?,?,?,?,?,?,?", 
						this.tableName
					);
			
			if(getJdbcTemplate().update(sql,
				individual.getSkin_id(),
				individual.getEducationlevel_id(),
				individual.getCivilstatus_id(),
				individual.getCpf(),
				individual.getFirst_name(),
				individual.getLast_name(),
				individual.getBirthday(),
				individual.getEmail()
			) != 1){
				// TODO: tentar exibir detalhes do erro
				logger.error("Não foi possível salvar o individual");
				return false;
			}else{
				List<Individual> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}

				individual.setId(this.lastInsertId());
				this.putCacheByAction("all", individual);
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

	public boolean edit(Individual individual) {
		try{
			String sql = this.getSqlUtil().buildUpdate(
						"cpf, first_name, last_name, birthday, email", 
						"?, ?, ?, ?, ?", 
						"id = ?", 
						this.tableName
					);
			
			/*String sql = " UPDATE "+ this.tableName +
						 " SET cpf = ?, first_name = ?, last_name = ?, birthday = ?, email = ? " +
						 " WHERE id = ?";*/
			
			if(getJdbcTemplate().update(sql,
				individual.getCpf(),
				individual.getFirst_name(),
				individual.getLast_name(),
				individual.getBirthday(),
				individual.getEmail(),
				individual.getId()
			) != 1){
				logger.error("Não foi possível atualizar o individual");
				return false;
			}else{
				// atualiza o cache local
				// TODO: atualizar em todos os caches possiveis
				List<Individual> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(individual, cache_by_action);
					if(indice > 0){
						cache_by_action.set(indice, individual);
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
	
	// TODO: Criar forma de deletar os models associados (User, Doctor, Patient)
	public boolean delete(Individual entry) {
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
				List<Individual> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(Individual compareTo, List<Individual> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
