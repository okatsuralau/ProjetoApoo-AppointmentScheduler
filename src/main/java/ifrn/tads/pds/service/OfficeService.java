package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Office;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Repository("officeDao")
@Service("officeService")
@Transactional
public class OfficeService {

	protected static Logger logger = Logger.getLogger("service");
	private JdbcTemplate jdbcTemplate;
	private String tableName = "office";

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// TODO: Utilizar JdbcDaoSupport
	// http://www.mkyong.com/spring/spring-jdbctemplate-jdbcdaosupport-examples/
	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Office findByID(int id) {
		logger.debug("Retrieving office for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Office) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Office.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }        
	    return null;
	}
	
	public List<Office> findAll() {
		logger.debug("Retrieving all offices");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Office> offices = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Office>(Office.class));
		return offices;
	}

	public void add(Office office) {
		logger.debug("Adding new office");
		
		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";

		getJdbcTemplate().update(sql, office.getTitle());
	}
	
	public void edit(Office office) {
		logger.debug("Editing existing office");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		getJdbcTemplate().update(sql, office.getTitle(), office.getId());
	}

	public void delete(int id) {
		logger.debug("Deleting existing office");

		String sql = "DELETE FROM "+ this.tableName +" WHERE id = ?";
		Object[] parameters = new Object[] { id };

		getJdbcTemplate().update(sql, parameters);
	}

	public int findCount() {
		String sql = "SELECT COUNT(*) FROM " + this.tableName;
		int total = getJdbcTemplate().queryForObject(sql, Integer.class);

		return total;
	}
	
	public boolean exists(int id) {
		String sql = "SELECT COUNT(*) FROM " + this.tableName +" WHERE id = ?";
		int total = getJdbcTemplate().queryForObject(sql, Integer.class, id);

		return total > 0;
	}
}
