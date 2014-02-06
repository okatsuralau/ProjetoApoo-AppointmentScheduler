package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Exam;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("examService")
@Transactional
public class ExamService extends AppService {

	public ExamService() {
		this.tableName = "exam";
	}

	public Exam findByID(int id) {
		logger.debug("Retrieving exam for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Exam) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Exam.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Exam> findAll() {
		logger.debug("Retrieving all exams");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Exam> exams = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Exam>(Exam.class));
		return exams;
	}

	/*public boolean add(Exam exam) {
		logger.debug("Adding new exam");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, exam.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Exam exam) {
		logger.debug("Editing existing exam");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, exam.getTitle(), exam.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}*/
}
