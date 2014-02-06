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
public class ExpertiseService extends AppService {

	public ExpertiseService() {
		this.tableName = "expertise";
	}

	public Expertise findByID(Integer id) {
		logger.debug("Retrieving expertise for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Expertise) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Expertise.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Expertise> findAll() {
		logger.debug("Retrieving all expertises");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Expertise> expertises = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Expertise>(Expertise.class));
		return expertises;
	}

	public boolean add(Expertise expertise) {
		logger.debug("Adding new expertise");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";

		try{
			getJdbcTemplate().update(sql, expertise.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Expertise expertise) {
		logger.debug("Editing existing expertise");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, expertise.getTitle(), expertise.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}
}
