package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Repository("roleDao")
@Service("roleService")
@Transactional
public class RoleService {

	protected static Logger logger = Logger.getLogger("service");
	private JdbcTemplate jdbcTemplate;
	private String tableName = "role";

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// TODO: Utilizar JdbcDaoSupport
	// http://www.mkyong.com/spring/spring-jdbctemplate-jdbcdaosupport-examples/
	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	
	public Role findByID(int id) {
		logger.debug("Retrieving role for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Role) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Role>(Role.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }        
	    return null;
	}
	
	public List<Role> findAll() {
		logger.debug("Retrieving all roles");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Role> roles = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Role>(Role.class));
		return roles;
	}

	public boolean add(Role role) {
		logger.debug("Adding new role");
		
		String sql = "INSERT INTO "+ this.tableName +" (title, slug) VALUES (?, ?)";
		
		try{
			getJdbcTemplate().update(sql, role.getTitle(), role.getSlug());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean edit(Role role) {
		logger.debug("Editing existing role");

		String sql = "UPDATE "+ this.tableName +" SET title = ?, "+ "slug = ? WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, role.getTitle(), role.getSlug(), role.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
		
		return true;
	}

	public boolean delete(int id) {
		logger.debug("Deleting existing role");

		String sql = "DELETE FROM "+ this.tableName +" WHERE id = ?";
		Object[] parameters = new Object[] { id };
		
		try{
			getJdbcTemplate().update(sql, parameters);
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public int findCount() {
		String sql = "SELECT COUNT(*) FROM " + this.tableName;
		int total = getJdbcTemplate().queryForInt(sql);

		return total;
	}
	
	@SuppressWarnings("deprecation")
	public boolean exists(int id) {
		String sql = "SELECT COUNT(*) FROM " + this.tableName +" WHERE id = ?";
		int total = getJdbcTemplate().queryForInt(sql, id);

		return total > 0;
	}
}
