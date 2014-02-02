package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Expertise;

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

@Service("expertiseService")
@Transactional
public class ExpertiseService {

	protected static Logger logger = Logger.getLogger("service");
	private JdbcTemplate jdbcTemplate;
	private String tableName = "expertise";

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public Expertise findByID(Integer id) {
		logger.debug("Retrieving expertise for id: " + id);
		try{
			String sql = "select * from "+ this.tableName +" where id = ?";
			Expertise expertise = (Expertise) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Expertise.class));
	
			return expertise;
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }        
	    return null;
	}
	
	public List<Expertise> findAll() {
		logger.debug("Retrieving all expertises");

		String sql = "select * from "+ this.tableName;
		List<Expertise> expertises = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Expertise>(Expertise.class));
		return expertises;
	}

	public void add(Expertise expertise) {
		logger.debug("Adding new expertise");

		String sql = "insert into "+ this.tableName +" (title) values (:title)";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("title", expertise.getTitle());

		getJdbcTemplate().update(sql, parameters);
	}
	
	public void edit(Integer id, String title, String slug) {
		logger.debug("Editing existing expertise");

		String sql = "update "+ this.tableName +" set title = :title "+ " where id = :id";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("title", title);

		getJdbcTemplate().update(sql, parameters);
	}

	public void delete(Integer id) {
		logger.debug("Deleting existing expertise");

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
		logger.debug("Checking if expertise with id(" + id + ") exists");

		String sql = "SELECT COUNT(*) FROM " + this.tableName +" where id = ?";
		int total = getJdbcTemplate().queryForInt(sql, id);

		return total > 0;
	}
}
