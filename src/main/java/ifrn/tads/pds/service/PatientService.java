package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Patient;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("patientService")
@Transactional
public class PatientService extends AppService {

	public PatientService() {
		this.tableName = "patient";
	}

	public Patient findByID(int id) {
		logger.debug("Retrieving patient for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Patient) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Patient.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Patient> findAll() {
		logger.debug("Retrieving all patients");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Patient> patients = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Patient>(Patient.class));
		return patients;
	}

	/*public boolean add(Patient patient) {
		logger.debug("Adding new patient");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, patient.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Patient patient) {
		logger.debug("Editing existing patient");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, patient.getTitle(), patient.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}*/
}
