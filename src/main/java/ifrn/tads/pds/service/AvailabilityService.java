package ifrn.tads.pds.service;

import java.util.List;

import ifrn.tads.pds.domain.Availability;
import ifrn.tads.pds.domain.Doctor;
import ifrn.tads.pds.domain.Expertise;
import ifrn.tads.pds.domain.Office;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("availabilityService")
@Transactional
public class AvailabilityService extends AppService<Availability> {

	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;

	@Resource(name="doctorService")
	private DoctorService doctorService;

	@Resource(name="officeService")
	private OfficeService officeService;

	public AvailabilityService() {
		this.tableName = "availability";
	}

	// Insere no resultado da consulta, os objetos associados a este model
	protected void setAssociates(Availability availability){
		Expertise expertise = expertiseService.findBy("id", availability.getExpertise_id()+"");
		if(expertise != null){
			availability.setExpertise(expertise);
		}

		Doctor doctor = doctorService.findBy("id", availability.getDoctor_id()+"", null, true);
		if(doctor != null){
			availability.setDoctor(doctor);
		}

		Office office = officeService.findBy("id", availability.getOffice_id()+"");
		if(office != null){
			availability.setOffice(office);
		}
	}
	
	public boolean delete(Availability entry) {
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
				List<Availability> cache_by_action = this.getCacheByAction("all");
				if(cache_by_action != null && !cache_by_action.isEmpty()){
					int indice = getArrayListIndex(entry, cache_by_action);
					if(indice > 0){
						cache_by_action.remove(indice);
					}
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	// Assume que os valores não são nulos
	private int getArrayListIndex(Availability compareTo, List<Availability> compareWith){
		int index = -1;
		for (int i = 0; i < compareWith.size(); i++) {
			if( compareWith.get(i).getId() == compareTo.getId() ){
				return i; // return index
			}
		}
		return index;
	}
}
