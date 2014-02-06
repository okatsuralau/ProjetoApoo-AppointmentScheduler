package ifrn.tads.pds.service;

import ifrn.tads.pds.domain.Queue;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("queueService")
@Transactional
public class QueueService extends AppService {

	public QueueService() {
		this.tableName = "queue";
	}

	public Queue findByID(int id) {
		logger.debug("Retrieving queue for id: " + id);
		try{
			String sql = "SELECT * FROM "+ this.tableName +" WHERE id = ?";
			return (Queue) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Queue.class));
		}catch (EmptyResultDataAccessException e){
			logger.debug("Nenhum registro encontrado para id = " + id);
            e.printStackTrace();
	    }
	    return null;
	}

	public List<Queue> findAll() {
		logger.debug("Retrieving all queues");

		String sql = "SELECT * FROM "+ this.tableName;
		List<Queue> queues = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Queue>(Queue.class));
		return queues;
	}

	/*public boolean add(Queue queue) {
		logger.debug("Adding new queue");

		String sql = "INSERT INTO "+ this.tableName +" (title) VALUES (?)";
		try{
			getJdbcTemplate().update(sql, queue.getTitle());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean edit(Queue queue) {
		logger.debug("Editing existing queue");

		String sql = "UPDATE "+ this.tableName +" SET title = ? "+ " WHERE id = ?";

		try{
			getJdbcTemplate().update(sql, queue.getTitle(), queue.getId());
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}

		return true;
	}*/
}
