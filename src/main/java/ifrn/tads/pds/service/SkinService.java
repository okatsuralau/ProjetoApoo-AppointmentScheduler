package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Skin;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Repository("skinDao")
@Service("skinService")
@Transactional
public class SkinService {

	protected static Logger logger = Logger.getLogger("service");
	private JdbcTemplate jdbcTemplate;
	private String tableName = "skin";

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
	public Skin findByID(int id) {
		logger.debug("Retrieving skin for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Skin) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Skin.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }        
	    return null;
	}
	
	public List<Skin> findAll() {
		logger.debug("Retrieving all skins");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Skin> skins = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Skin>(Skin.class));
		return skins;
	}

	public void add(Skin skin) {
		logger.debug("Adding new skin");
		
		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";

		getJdbcTemplate().update(sql, skin.getTitle());
	}
	
	public void edit(Skin skin) {
		logger.debug("Editing existing skin");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		getJdbcTemplate().update(sql, skin.getTitle(), skin.getId());
	}

	public void delete(int id) {
		logger.debug("Deleting existing skin");

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
