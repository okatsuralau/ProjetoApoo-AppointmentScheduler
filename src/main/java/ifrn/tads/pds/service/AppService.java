package ifrn.tads.pds.service;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AppService {

	protected static Logger logger = Logger.getLogger("service");
	protected JdbcTemplate jdbcTemplate;
	protected String tableName = "";

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// TODO: Utilizar JdbcDaoSupport
	// http://www.mkyong.com/spring/spring-jdbctemplate-jdbcdaosupport-examples/
	protected JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public boolean delete(int id) {
		logger.debug("Deleting existing datebook");

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

	public int findCount() {
		String sql = "SELECT COUNT(*) FROM " + this.tableName;
		return getJdbcTemplate().queryForObject(sql, Integer.class);
	}

	public boolean exists(int id) {
		String sql = "SELECT COUNT(*) FROM " + this.tableName +" WHERE id = ?";
		int total = getJdbcTemplate().queryForObject(sql, Integer.class, id);

		return total > 0;
	}
}
