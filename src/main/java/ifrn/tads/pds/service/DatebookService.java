package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Datebook;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("datebookService")
@Transactional
public class DatebookService extends AppService {

	public DatebookService(){
		this.tableName = "datebook";
	}

	public Datebook findByID(int id) {
		logger.debug("Retrieving datebook for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Datebook) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Datebook.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Datebook> findAll() {
		logger.debug("Retrieving all datebooks");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Datebook> datebooks = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Datebook>(Datebook.class));
		return datebooks;
	}

	/*public boolean add(Datebook datebook) {
		logger.debug("Adding new datebook");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, datebook.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Datebook datebook) {
		logger.debug("Editing existing datebook");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, datebook.getTitle(), datebook.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}*/
}
