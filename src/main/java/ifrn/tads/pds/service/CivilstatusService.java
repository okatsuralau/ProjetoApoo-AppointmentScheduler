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
public class CivilstatusService extends AppService {
	
	public CivilstatusService(){
		this.tableName = "civilstatus";
	}

	public Civilstatus findByID(Integer id) {
		logger.debug("Retrieving civilstatus for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Civilstatus) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Civilstatus.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Civilstatus> findAll() {
		logger.debug("Retrieving all civilstatuss");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Civilstatus> civilstatuss = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Civilstatus>(Civilstatus.class));
		return civilstatuss;
	}

	public boolean add(Civilstatus civilstatus) {
		logger.debug("Adding new civilstatus");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";

		try{
			getJdbcTemplate().update(sql, civilstatus.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Civilstatus civilstatus) {
		logger.debug("Editing existing civilstatus");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, civilstatus.getTitle(), civilstatus.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}
}
