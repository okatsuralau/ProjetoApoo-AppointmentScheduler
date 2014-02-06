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

@Service("officeService")
@Transactional
public class OfficeService extends AppService {

	public OfficeService() {
		this.tableName = "office";
	}

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

	public boolean add(Office office) {
		logger.debug("Adding new office");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, office.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Office office) {
		logger.debug("Editing existing office");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, office.getTitle(), office.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}
}
