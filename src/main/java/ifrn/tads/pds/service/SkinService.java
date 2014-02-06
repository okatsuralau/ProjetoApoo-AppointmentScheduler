package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Skin;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("skinService")
@Transactional
public class SkinService extends AppService {

	public SkinService() {
		this.tableName = "skin";
	}

	public Skin findByID(int id) {
		logger.debug("Retrieving skin for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Skin) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Skin.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Skin> findAll() {
		logger.debug("Retrieving all skins");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Skin> skins = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Skin>(Skin.class));
		return skins;
	}

	public boolean add(Skin skin) {
		logger.debug("Adding new skin");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, skin.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Skin skin) {
		logger.debug("Editing existing skin");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, skin.getTitle(), skin.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}
}
