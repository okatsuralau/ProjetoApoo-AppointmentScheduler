package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Log;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("logService")
@Transactional
public class LogService extends AppService {

	public LogService() {
		this.tableName = "log";
	}

	public Log findByID(int id) {
		logger.debug("Retrieving log for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Log) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Log.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Log> findAll() {
		logger.debug("Retrieving all logs");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Log> logs = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Log>(Log.class));
		return logs;
	}

	/*public boolean add(Log log) {
		logger.debug("Adding new log");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, log.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Log log) {
		logger.debug("Editing existing log");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, log.getTitle(), log.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}*/
}
