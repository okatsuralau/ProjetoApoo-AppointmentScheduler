package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Civilstatus;

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

@Service("civilstatusService")
@Transactional
public class CivilstatusService {

	protected static Logger logger = Logger.getLogger("service");
	private JdbcTemplate jdbcTemplate;
	private String tableName = "civilstatus";

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public Civilstatus findByID(Integer id) {
		logger.debug("Retrieving civilstatus for id: " + id);
		try{
			String sql = "select * from "+ this.tableName +" where id = ?";
			Civilstatus civilstatus = (Civilstatus) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Civilstatus.class));
	
			return civilstatus;
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }        
	    return null;
	}
	
	public List<Civilstatus> findAll() {
		logger.debug("Retrieving all civilstatuss");

		String sql = "select * from "+ this.tableName;
		List<Civilstatus> civilstatuss = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Civilstatus>(Civilstatus.class));
		return civilstatuss;
	}

	public void add(Civilstatus civilstatus) {
		logger.debug("Adding new civilstatus");

		String sql = "insert into "+ this.tableName +" (title) values (:title)";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("title", civilstatus.getTitle());

		getJdbcTemplate().update(sql, parameters);
	}
	
	public void edit(Integer id, String title, String slug) {
		logger.debug("Editing existing civilstatus");

		String sql = "update "+ this.tableName +" set title = :title "+ " where id = :id";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("title", title);

		getJdbcTemplate().update(sql, parameters);
	}

	public void delete(Integer id) {
		logger.debug("Deleting existing civilstatus");

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
		logger.debug("Checking if civilstatus with id(" + id + ") exists");

		String sql = "SELECT COUNT(*) FROM " + this.tableName +" where id = ?";
		int total = getJdbcTemplate().queryForInt(sql, id);

		return total > 0;
	}
}
