package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Doctor;
import ifrn.tads.pds.service.CivilstatusService;
import ifrn.tads.pds.service.DoctorService;
import ifrn.tads.pds.service.EducationlevelService;
import ifrn.tads.pds.service.ExpertiseService;
import ifrn.tads.pds.service.SkinService;

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
@RequestMapping("/doctor")
public class DoctorController extends AppController{

	@Resource(name="doctorService")
	private DoctorService doctorService;
	private String alias = "doctor";	
	
	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;
	
	@Resource(name="skinService")
	private SkinService skinService;
	
	@Resource(name="educationlevelService")
	private EducationlevelService educationlevelService;
	
	@Resource(name="civilstatusService")
	private CivilstatusService civilstatusService;

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	model.addAttribute("title_for_layout", "Médicos");
    	return new ModelAndView(this.alias + "/index", "doctors", doctorService.find("all"));
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Doctor") Doctor doctor, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo médico");
		
		model.addAttribute("skins", this.skinService.findList());
		model.addAttribute("educationlevels", this.educationlevelService.findList());
		model.addAttribute("civilstatus", this.civilstatusService.findList());
		model.addAttribute("expertises", this.expertiseService.findList());
		
		return new ModelAndView(this.alias + "/form", "doctor", new Doctor());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Doctor doctor, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo médico");
		
		if(result.hasErrors()){
			logger.error("Dados invalidos");
			logger.error( result.getAllErrors().toString() );
			
			model.put("doctor", doctor);
			
			model.addAttribute("skins", this.skinService.findList());
			model.addAttribute("educationlevels", this.educationlevelService.findList());
			model.addAttribute("civilstatus", this.civilstatusService.findList());
			model.addAttribute("expertises", this.expertiseService.findList());
			
			return this.alias + "/form";
		}else{
			if(!doctorService.add(doctor)){
				logger.warn("Não foi possível salvar");
				model.addAttribute("message", "Não foi possível salvar");
				model.put("doctor", doctor);
				
				model.addAttribute("skins", this.skinService.findList());
				model.addAttribute("educationlevels", this.educationlevelService.findList());
				model.addAttribute("civilstatus", this.civilstatusService.findList());
				model.addAttribute("expertises", this.expertiseService.findList());
				return this.alias + "/form";
				//return "redirect:/" + this.alias + "/add";
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
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Doctor doctor_form, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar médico");
		
		if(id == null || !doctorService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			logger.error("Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		doctor_form = doctorService.findBy("id", id+"", null, true);
		model.addAttribute("skins", this.skinService.findList());
		model.addAttribute("educationlevels", this.educationlevelService.findList());
		model.addAttribute("civilstatus", this.civilstatusService.findList());
		model.addAttribute("expertises", this.expertiseService.findList());

		return new ModelAndView(this.alias + "/form", "doctor", doctor_form);
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Doctor doctor_form, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar médico");

		if(id == null || !doctorService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			logger.error("Identificador inválido");
			return this.alias + "/index";
		}

		Doctor doctor = doctorService.findBy("id", id+"", null, true);

		if(result.hasErrors() || doctor_form == null){
			// TODO: saber por quê não mostra as mensagens de erro no form
			logger.error("Dados invalidos");
			logger.error( result.getAllErrors().toString() );
			
			model.put("doctor", doctor);
			
			model.addAttribute("skins", this.skinService.findList());
			model.addAttribute("educationlevels", this.educationlevelService.findList());
			model.addAttribute("civilstatus", this.civilstatusService.findList());
			model.addAttribute("expertises", this.expertiseService.findList());
			return this.alias + "/form";
		}else{
			if(!doctorService.edit(doctor_form)){
				logger.warn("Não foi possível salvar");
				model.addAttribute("message", "Não foi possível salvar");
				model.put("doctor", doctor_form);
				
				model.addAttribute("skins", this.skinService.findList());
				model.addAttribute("educationlevels", this.educationlevelService.findList());
				model.addAttribute("civilstatus", this.civilstatusService.findList());
				model.addAttribute("expertises", this.expertiseService.findList());
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Doctor doctor, ModelMap model) {
		if(id == null || !doctorService.exists(id)){
			logger.error("Identificador inválido");
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		doctorService.delete(doctorService.findBy("id", id+""));

		return "redirect:/"+ this.alias + "/";
	}
}
