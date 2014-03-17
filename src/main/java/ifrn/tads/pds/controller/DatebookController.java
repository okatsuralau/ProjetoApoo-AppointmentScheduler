package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Availability;
import ifrn.tads.pds.domain.Datebook;
import ifrn.tads.pds.service.AvailabilityService;
import ifrn.tads.pds.service.DatebookService;
import ifrn.tads.pds.service.DoctorService;
import ifrn.tads.pds.service.ExpertiseService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

// TODO: implementar forms

@Controller
@RequestMapping("/datebook")
public class DatebookController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="datebookService")
	private DatebookService datebookService;
	
	@Resource(name="doctorService")
	private DoctorService doctorService;
	
	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;
	
	@Resource(name="availabilityService")
	private AvailabilityService availabilityService;
	
	private String alias = "datebook";
	
	@ResponseBody
	@RequestMapping(value="/ajax/expertise/{expertise_id}", produces="application/json")
	public String BuscarPorEspecialidade(@PathVariable(value="expertise_id") Integer expertise_id){
		List<Availability> availabilities = availabilityService.find("all", new JSONObject().put("conditions", "expertise_id = "+ expertise_id), true, false);
		int size = availabilities == null ? 0 : availabilities.size();
		if(availabilities == null) availabilities = new ArrayList<Availability>();

		return new JSONObject()
			.put("size", size)
			.put("data", new JSONArray(availabilities))
			.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/doctor/{doctor_id}", produces="application/json")
	public String BuscarPorMedico(@PathVariable(value="doctor_id") Integer doctor_id){
		List<Availability> availabilities = availabilityService.find("all", new JSONObject().put("conditions", "doctor_id = "+ doctor_id), true, false);
		int size = availabilities == null ? 0 : availabilities.size();
		if(availabilities == null) availabilities = new ArrayList<Availability>();

		return new JSONObject()
			.put("size", size)
			.put("data", new JSONArray(availabilities))
			.toString();
	}

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Datebook> datebook = datebookService.find("all");
    	model.addAttribute("title_for_layout", "Agenda de consultas");
    	return new ModelAndView(this.alias + "/index", "datebook", datebook);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Datebook") Datebook datebook, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova consulta");
		
		model.addAttribute("expertises", this.expertiseService.findList());
		model.addAttribute("doctors", this.doctorService.findList());
		
		
		
		return new ModelAndView(this.alias + "/form", "datebook", new Datebook());
	}

	/*@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Datebook datebook, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova consulta");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("datebook", datebook);
			return this.alias + "/form";

		}else{
			if(!datebookService.add(datebook)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("datebook", datebook);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}*/

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Datebook datebook, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar registro de consulta");

		if(id == null || !datebookService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Datebook _datebook = datebookService.findBy("id", id+"");

		return new ModelAndView(this.alias + "/form", "datebook", _datebook);
	}


	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Datebook datebook, ModelMap model) {
		if(id == null || !datebookService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		datebookService.delete(datebookService.findBy("id", id+""));

		return "redirect:/"+ this.alias + "/";
	}
}
