package ifrn.tads.pds.service;
import ifrn.tads.pds.domain.Schedule;
import ifrn.tads.pds.domain.Office;
import ifrn.tads.pds.domain.Expertise;
import ifrn.tads.pds.domain.Doctor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("scheduleService")
@Transactional
public class ScheduleService extends AppService<Schedule>{

	// Associates
	@Resource(name="doctorService")
	private DoctorService doctorService;

	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;

	@Resource(name="officeService")
	private OfficeService officeService;

	public ScheduleService() {
		this.tableName = "schedule";
	}

	// Insere no resultado da consulta, os objetos associados a este model
	protected void setAssociates(Schedule schedule){
		Doctor doctor = doctorService.findBy("id", schedule.getDoctor_id()+"", null, true);
		if(doctor != null){
			schedule.setDoctor(doctor);
		}

		Expertise expertise = expertiseService.findBy("id", schedule.getExpertise_id()+"");
		if(expertise != null){
			schedule.setExpertise(expertise);
		}

		Office office = officeService.findBy("id", schedule.getOffice_id()+"");
		if(office != null){
			schedule.setOffice(office);
		}
	}

	public Map<String, String> findList(JSONObject parameters) {
		Map<String, String> map_list = new LinkedHashMap<String, String>();
		List<Schedule> list_schedules = this.find("list", parameters, false);

		if(!list_schedules.isEmpty()){
			// trata a saída dos dados para chavePrimária =>displayField
			for (Schedule schedule : list_schedules) {
				map_list.put(schedule.getPrimaryKey()+"", schedule.getDisplayField());
			}
		}
		return map_list;
	}

	public boolean add(Schedule schedule) {		
		try{
			logger.debug("Adding new schedule");
			
			String sql = this.getSqlUtil().buildInsert(
						"doctor_id, expertise_id, office_id, sunday, monday, tuesday, wednesday, thursday, friday, saturday, schedule_time, amount", 
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?", 
						this.tableName
					);
			
			// Trata a hora
			// Date parsedDate = (Date) new SimpleDateFormat("dd.MM.yyyy HH:mm").parse( schedule.getSchedule_time().toString() );
			// Timestamp schedule_time = new Timestamp(parsedDate.getTime());

			logger.error(" HORA: "+schedule.getSchedule_time());

			if(getJdbcTemplate().update(sql, schedule.getDoctor_id(), schedule.getExpertise_id(),
						schedule.getOffice_id(), schedule.isSunday(), schedule.isMonday(),
						schedule.isTuesday(), schedule.isWednesday(), schedule.isThursday(),
						schedule.isFriday(), schedule.isSaturday(), schedule.getSchedule_time(), schedule.getAmount()) != 1){

				// TODO: tentar exibir detalhes do erro
				logger.error("Não foi possível agendar");
				return false;
			}else{
				List<Schedule> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action == null || cache_by_action.isEmpty()){
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}

				schedule.setId(this.lastInsertId());
				this.putCacheByAction("all", schedule);
			}

			//logService.add(new Log(1, this.getLastInsertId(), this.tableName, "add", ""));
			return true;
		}
		catch (InvalidResultSetAccessException e) {
			logger.trace( "InvalidResultSetAccessException: " + e.getMessage());
			return false;
		}
		catch (DataAccessException e){
			logger.trace( "DataAccessException: " + e.getMessage());
			return false;
		}
//		catch (ParseException e) {
//			logger.trace(e.getMessage());
//			return false;
//        }
	}

	// TODO: implementar 
	// Status: Failing
	public boolean edit(Schedule schedule) {
		try{
			logger.debug("Editing existing schedule");
			
			String sql = this.getSqlUtil().buildUpdate(
						"doctor_id, expertise_id, office_id, sunday, monday, tuesday, wednesday, thursday, friday, saturday, schedule_time, amount", 
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?",
						"id = ?", 
						this.tableName
					);

			if(getJdbcTemplate().update(sql, schedule.getDoctor_id(), schedule.getExpertise_id(),
					schedule.getOffice_id(), schedule.isSunday(), schedule.isMonday(),
					schedule.isTuesday(), schedule.isWednesday(), schedule.isThursday(),
					schedule.isFriday(), schedule.isSaturday(), schedule.getSchedule_time(), schedule.getAmount(), schedule.getId()) != 1){
				logger.error("Não foi possível atualizar o schedule");
				return false;
			}else{
				// atualiza o cache local
				// TODO: atualizar em todos os caches possiveis
				List<Schedule> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(schedule, cache_by_action);
					if(indice > 0){
						cache_by_action.set(indice, schedule);
					}
				}else{
					// Popula o cache local, caso esteja vazio
					this.find("all");
				}
				
				//logService.add(new Log(1, this.getLastInsertId(), this.tableName, "add", ""));
				return true;
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return false;
		}
	}

	public boolean delete(Schedule entry) {
		try{
			logger.info("Deleting existing entry from "+ this.tableName );
			
			String sql = "DELETE FROM "+ this.tableName +" WHERE id = ?";
			Object[] parameters = new Object[] { entry.getId() };
			
			if(getJdbcTemplate().update(sql, parameters) != 1){
				logger.error("Não foi possível deletar o registro");
				return false;
			}else{
				// atualiza o cache local
				// TODO: eliminar de todos os caches possiveis
				List<Schedule> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(entry, cache_by_action);
					if(indice > 0){
						cache_by_action.remove(indice);
					}
				}
			}
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}

	// Assume que os valores não são nulos
	private int getArrayListIndex(Schedule compareTo, List<Schedule> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
