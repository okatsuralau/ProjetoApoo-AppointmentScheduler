package ifrn.tads.pds.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import ifrn.tads.pds.domain.Schedule;
import ifrn.tads.pds.service.OfficeService;
import ifrn.tads.pds.service.ScheduleService;
import ifrn.tads.pds.service.DoctorService;
import ifrn.tads.pds.service.ExpertiseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// TODO: implementar forms

@Controller
@RequestMapping("/schedule")
public class ScheduleController extends AppController{

	private String alias = "schedule";
	
	@Resource(name="scheduleService")
	private ScheduleService scheduleService;
	
	@Resource(name="doctorService")
	private DoctorService doctorService;
	
	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;
	
	@Resource(name="officeService")
	private OfficeService officeService;
	
	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	model.addAttribute("title_for_layout", "Configuração de agendas médicas");
    	return new ModelAndView(this.alias + "/index", "schedule", scheduleService.find("all"));
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Schedule") Schedule schedule, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo schedule");
		model.addAttribute("expertises", this.expertiseService.findList());
		model.addAttribute("doctors", this.doctorService.findList());
		model.addAttribute("offices", this.officeService.findList());
		
		// Quantidade
		Map<String,String> quantidades = new LinkedHashMap<String,String>();
		for (int i = 0; i < 30; i++) {
			quantidades.put(i+"", i+" pessoas");
		}
		model.addAttribute("quantidades", quantidades);
		
		// Horários
		Map<String,String> horarios = new LinkedHashMap<String,String>();
		for (int i = 0; i < 12; i++) {
			String hora = i + ":";
			horarios.put(hora+"00", hora+"00");
			horarios.put(hora+"30", hora+"30");
		}
		model.addAttribute("horarios", horarios);
		
		return new ModelAndView(this.alias + "/form", "schedule", new Schedule());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Schedule schedule, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo schedule");
		
		if(result.hasErrors()){
			logger.error("Dados invalidos");
			model.addAttribute("expertises", this.expertiseService.findList());
			model.addAttribute("doctors", this.doctorService.findList());
			model.addAttribute("offices", this.officeService.findList());
			
			// Quantidade
			Map<String,String> quantidades = new LinkedHashMap<String,String>();
			for (int i = 0; i < 30; i++) {
				quantidades.put(i+"", i+" pessoas");
			}
			model.addAttribute("quantidades", quantidades);
			
			// Horários
			Map<String,String> horarios = new LinkedHashMap<String,String>();
			for (int i = 0; i < 12; i++) {
				String hora = i + ":";
				horarios.put(hora+"00", hora+"00");
				horarios.put(hora+"30", hora+"30");
			}
			model.addAttribute("horarios", horarios);
			
			return this.alias + "/form";
		}else{
			if(!scheduleService.add(schedule)){
				logger.warn("Não foi possível salvar");
				model.addAttribute("message", "Não foi possível salvar");
				model.addAttribute("expertises", this.expertiseService.findList());
				model.addAttribute("doctors", this.doctorService.findList());
				model.addAttribute("offices", this.officeService.findList());
				
				// Quantidade
				Map<String,String> quantidades = new LinkedHashMap<String,String>();
				for (int i = 0; i < 30; i++) {
					quantidades.put(i+"", i+" pessoas");
				}
				model.addAttribute("quantidades", quantidades);
				
				// Horários
				Map<String,String> horarios = new LinkedHashMap<String,String>();
				for (int i = 0; i < 12; i++) {
					String hora = i + ":";
					horarios.put(hora+"00", hora+"00");
					horarios.put(hora+"30", hora+"30");
				}
				model.addAttribute("horarios", horarios);
				
				return this.alias + "/form";
			}else{
				logger.info("O registro foi salvo com sucesso");
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}
	
	/**
	 * Edit
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Schedule schedule, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar schedulee");
		
		if(id == null || !scheduleService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			logger.error("Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}
		
		// TODO: setar os valores de expertises e individual id para o form

		Schedule _schedule = scheduleService.findBy("id", id+"");
		model.addAttribute("doctors", this.doctorService.findList());

		return new ModelAndView(this.alias + "/form", "schedule", _schedule);
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Schedule schedule, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar schedulee");

		if(id == null || !scheduleService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			logger.error("Identificador inválido");
			return this.alias + "/index";
		}

		Schedule _schedule = scheduleService.findBy("id", id+"");

		if(result.hasErrors() || schedule == null){
			logger.error("Dados invalidos");
			model.put("schedule", _schedule);
			model.addAttribute("doctors", this.doctorService.findList());
			return this.alias + "/form";
		}else{
			if(!scheduleService.edit(schedule)){
				logger.warn("Não foi possível salvar");
				model.addAttribute("message", "Não foi possível salvar");
				model.addAttribute("doctors", this.doctorService.findList());
				model.put("schedule", schedule);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(fmt, true));
//	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Schedule schedule, ModelMap model) {
		if(id == null || !scheduleService.exists(id)){
			logger.error("Identificador inválido");
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		scheduleService.delete(scheduleService.findBy("id", id+""));

		return "redirect:/"+ this.alias + "/";
	}
}
