package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.HealthPlan;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("healthPlanService")
@Transactional
public class HealthPlanService extends AppService {

	public HealthPlanService() {
		this.tableName = "health_plan";
	}

	public HealthPlan findByID(int id) {
		logger.debug("Retrieving healthPlan for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (HealthPlan) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(HealthPlan.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<HealthPlan> findAll() {
		logger.debug("Retrieving all healthPlans");

		String sql = "SELECT * FROM "+ this.tableName;
		List<HealthPlan> healthPlans = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<HealthPlan>(HealthPlan.class));
		return healthPlans;
	}

	/*public boolean add(HealthPlan healthPlan) {
		logger.debug("Adding new healthPlan");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, healthPlan.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(HealthPlan healthPlan) {
		logger.debug("Editing existing healthPlan");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, healthPlan.getTitle(), healthPlan.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}*/
}
