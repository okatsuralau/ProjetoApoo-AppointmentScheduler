package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Doctor;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("doctorService")
@Transactional
public class DoctorService extends AppService {

	public DoctorService() {
		this.tableName = "doctor";
	}

	public Doctor findByID(int id) {
		logger.debug("Retrieving doctor for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Doctor) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Doctor.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Doctor> findAll() {
		logger.debug("Retrieving all doctors");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Doctor> doctors = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Doctor>(Doctor.class));
		return doctors;
	}

	/*public boolean add(Doctor doctor) {
		logger.debug("Adding new doctor");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, doctor.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Doctor doctor) {
		logger.debug("Editing existing doctor");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, doctor.getTitle(), doctor.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}*/
}
