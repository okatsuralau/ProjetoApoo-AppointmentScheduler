package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.User;
import ifrn.tads.pds.domain.Individual;
import ifrn.tads.pds.domain.Role;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserService extends AppService<User>{

	@Resource(name="individualService")
	private IndividualService individualService;

	@Resource(name="roleService")
	private RoleService roleService;

	public UserService() {
		this.tableName = "user";
	}

	// Insere no resultado da consulta, os objetos associados a este model
	protected void setAssociates(User user){
		Individual individual = individualService.findBy("id", user.getIndividual_id()+"", null, true);
		if(individual != null){
			user.setIndividual(individual);
		}

		Role role = roleService.findBy("id", user.getRole_id()+"");
		if(role != null){
			user.setRole(role);
		}
	}

	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<User> list_users = this.find("list", parameters, false);

		if(!list_users.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (User user : list_users) {
				map_list.put(user.getPrimaryKey()+"", user.getDisplayField());
			}
		}
		return map_list;
	}

	public boolean add(User user) {
		try{
			int lastID = -1;

			// ver se dá pra deixar essa busca de existência mais dinâmica (escolher o campo a ser verificado)
			if(!this.individualService.existsBy("cpf", user.getCpf())){
				logger.info("Adding new user");

				this.individualService.add(new Individual(
					user.getSkin_id(), user.getEducationlevel_id(), user.getCivilstatus_id(), user.getCpf(), user.getFirst_name(), user.getLast_name(), user.getBirthday(), user.getEmail() )
				);
				lastID = this.lastInsertId();
			}else{
				logger.info("Recuperando individuo existente");
				Individual existent_individual = this.individualService.findBy("cpf", user.getCpf());
				lastID = existent_individual.getId();
			}

			if(lastID == -1){
				logger.error("Não foi possível salvar o User");
				return false;
			}

			// Add new user
			String sql = this.getSqlUtil().buildInsert("individual_id, role_id, username, passcode", "?, ?, ?, ?", this.tableName);

			if(getJdbcTemplate().update(sql,
				lastID,
				user.getRole_id(),
				user.getUsername(),
				user.getPasscode()
			) != 1){
				// TODO: tentar exibir detalhes do erro
				logger.error("Não foi possível salvar o user");
				return false;
			}else{
				List<User> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}

				user.setId(this.lastInsertId());
				this.putCacheByAction("all", user);

				//logService.add(new Log(1, user.getId(), this.tableName, "add", ""));
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

	// TODO: verificar quais campos foram alterados antes de salvar
	// TODO: verificar se está funcionando
	public boolean edit(User user) {
		try{
			logger.debug("Editing existing user");

			if(!this.existsBy("id", user.getId() + "")){
				logger.error("O "+ this.tableName +" de ID = "+ user.getId() +" não existe");
				return false;
			}

			// Update Individual
			if(!this.individualService.edit(
				new Individual(
					user.getIndividual_id(),
					user.getSkin_id(),
					user.getEducationlevel_id(),
					user.getCivilstatus_id(),
					user.getCpf(),
					user.getFirst_name(),
					user.getLast_name(),
					user.getBirthday(),
					user.getEmail()
				)
			)){
				logger.error("Não foi possível atualizar o user");
				return false;
			}else{
				// Update User
				// TODO: adicionar campos (activated, deleted)
				String sql = this.getSqlUtil().buildUpdate("role_id, username", "?, ?", "id = ?", this.tableName);
				
				logger.warn("USER: " + user.isDeleted());
				
				if( getJdbcTemplate().update(
					sql,
					user.getRole_id(),
					user.getUsername(),
					user.getId()
				) != 1){
					logger.error("Não foi possível atualizar o user");
					return false;
				}else{
					// atualiza o cache local
					// TODO: atualizar em todos os caches possiveis
					List<User> cache_by_action = this.getCacheByAction("all");
					if(cache_by_action != null && !cache_by_action.isEmpty()){
						int indice = getArrayListIndex(user, cache_by_action);
						if(indice > 0){
							cache_by_action.set(indice, user);
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
			logger.debug(e.getMessage());
			return false;
		}
	}

	public boolean delete(User entry) {
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
				List<User> cache_by_action = this.getCacheByAction("all");
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
	private int getArrayListIndex(User compareTo, List<User> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
