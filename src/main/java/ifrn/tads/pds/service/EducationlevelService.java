package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Educationlevel;

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

@Service("educationlevelService")
@Transactional
public class EducationlevelService {

	protected static Logger logger = Logger.getLogger("service");
	private JdbcTemplate jdbcTemplate;
	private String tableName = "educationlevel";

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public Educationlevel findByID(Integer id) {
		logger.debug("Retrieving educationlevel for id: " + id);
		try{
			String sql = "select * from "+ this.tableName +" where id = ?";
			Educationlevel educationlevel = (Educationlevel) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Educationlevel.class));
	
			return educationlevel;
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }        
	    return null;
	}
	
	public List<Educationlevel> findAll() {
		logger.debug("Retrieving all educationlevels");

		String sql = "select * from "+ this.tableName;
		List<Educationlevel> educationlevels = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Educationlevel>(Educationlevel.class));
		return educationlevels;
	}

	public void add(Educationlevel educationlevel) {
		logger.debug("Adding new educationlevel");

		String sql = "insert into "+ this.tableName +" (title) values (:title)";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("title", educationlevel.getTitle());

		getJdbcTemplate().update(sql, parameters);
	}
	
	public void edit(Integer id, String title, String slug) {
		logger.debug("Editing existing educationlevel");

		String sql = "update "+ this.tableName +" set title = :title "+ " where id = :id";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("title", title);

		getJdbcTemplate().update(sql, parameters);
	}

	public void delete(Integer id) {
		logger.debug("Deleting existing educationlevel");

		String sql = "delete from "+ this.tableName +" where id = ?";
		Object[] parameters = new Object[] { id };

		getJdbcTemplate().update(sql, parameters);
	}

	public int findCount() {
		String sql = "SELECT COUNT(*) FROM " + this.tableName;
		int total = getJdbcTemplate().queryForInt(sql);

		return total;
	}
	
	public boolean exists(Integer id) {
		logger.debug("Checking if educationlevel with id(" + id + ") exists");

		String sql = "SELECT COUNT(*) FROM " + this.tableName +" where id = ?";
		int total = getJdbcTemplate().queryForInt(sql, id);

		return total > 0;
	}
}
