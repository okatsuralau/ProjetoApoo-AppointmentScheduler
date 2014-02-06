package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.User;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserService extends AppService {

	public UserService() {
		this.tableName = "user";
	}

	public User findByID(int id) {
		logger.debug("Retrieving user for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (User) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(User.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<User> findAll() {
		logger.debug("Retrieving all users");

		String sql = "SELECT * FROM "+ this.tableName;
		List<User> users = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	/*public boolean add(User user) {
		logger.debug("Adding new user");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, user.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(User user) {
		logger.debug("Editing existing user");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, user.getTitle(), user.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}*/
}
