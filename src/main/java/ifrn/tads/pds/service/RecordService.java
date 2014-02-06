package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Record;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("recordService")
@Transactional
public class RecordService extends AppService {

	public RecordService() {
		this.tableName = "record";
	}

	public Record findByID(int id) {
		logger.debug("Retrieving record for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Record) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Record.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Record> findAll() {
		logger.debug("Retrieving all records");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Record> records = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Record>(Record.class));
		return records;
	}

	/*public boolean add(Record record) {
		logger.debug("Adding new record");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, record.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Record record) {
		logger.debug("Editing existing record");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, record.getTitle(), record.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}*/
}
