package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Educationlevel;

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

@Service("educationlevelService")
@Transactional
public class EducationlevelService extends AppService {

	public EducationlevelService() {
		this.tableName = "educationlevel";
	}

	public Educationlevel findByID(Integer id) {
		logger.debug("Retrieving educationlevel for id: " + id);
		try{
			String sql = "select * from "+ this.tableName +" where id = ?";
			Educationlevel educationlevel = (Educationlevel) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Educationlevel.class));

			return educationlevel;
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Educationlevel> findAll() {
		logger.debug("Retrieving all educationlevels");

		String sql = "select * from "+ this.tableName;
		List<Educationlevel> educationlevels = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Educationlevel>(Educationlevel.class));
		return educationlevels;
	}

	public boolean add(Educationlevel educationlevel) {
		logger.debug("Adding new educationlevel");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";

		try{
			getJdbcTemplate().update(sql, educationlevel.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Educationlevel educationlevel) {
		logger.debug("Editing existing educationlevel");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(
				sql,
				educationlevel.getTitle(),
				educationlevel.getId()
			);
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}
}
