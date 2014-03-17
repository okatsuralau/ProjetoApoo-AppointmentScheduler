package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Patient;
import ifrn.tads.pds.service.CivilstatusService;
import ifrn.tads.pds.service.PatientService;
import ifrn.tads.pds.service.EducationlevelService;
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
@RequestMapping("/patient")
public class PatientController extends AppController{

	@Resource(name="patientService")
	private PatientService patientService;
	private String alias = "patient";
	
	@Resource(name="skinService")
	private SkinService skinService;
	
	@Resource(name="educationlevelService")
	private EducationlevelService educationlevelService;
	
	@Resource(name="civilstatusService")
	private CivilstatusService civilstatusService;

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	model.addAttribute("title_for_layout", "Pacientes");
    	return new ModelAndView(this.alias + "/index", "patients", patientService.find("all"));
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Patient") Patient patient, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo paciente");
		
		model.addAttribute("skins", this.skinService.findList());
		model.addAttribute("educationlevels", this.educationlevelService.findList());
		model.addAttribute("civilstatus", this.civilstatusService.findList());
		
		return new ModelAndView(this.alias + "/form", "patient", new Patient());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Patient patient, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo paciente");
		
		if(result.hasErrors()){
			logger.error("Dados invalidos");
			logger.error( result.getAllErrors().toString() );
			
			model.put("patient", patient);
			
			model.addAttribute("skins", this.skinService.findList());
			model.addAttribute("educationlevels", this.educationlevelService.findList());
			model.addAttribute("civilstatus", this.civilstatusService.findList());
			
			return this.alias + "/form";
		}else{
			if(!patientService.add(patient)){
				logger.warn("Não foi possível salvar");
				model.addAttribute("message", "Não foi possível salvar");
				model.put("patient", patient);
				
				model.addAttribute("skins", this.skinService.findList());
				model.addAttribute("educationlevels", this.educationlevelService.findList());
				model.addAttribute("civilstatus", this.civilstatusService.findList());
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
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Patient patient_form, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar paciente");
		
		if(id == null || !patientService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			logger.error("Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		patient_form = patientService.findBy("id", id+"", null, true);
		model.addAttribute("skins", this.skinService.findList());
		model.addAttribute("educationlevels", this.educationlevelService.findList());
		model.addAttribute("civilstatus", this.civilstatusService.findList());

		return new ModelAndView(this.alias + "/form", "patient", patient_form);
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Patient patient_form, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar paciente");

		if(id == null || !patientService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			logger.error("Identificador inválido");
			return this.alias + "/index";
		}

		Patient patient = patientService.findBy("id", id+"");

		if(result.hasErrors() || patient_form == null){
			// TODO: saber por quê não mostra as mensagens de erro no form
			logger.error("Dados invalidos");
			logger.error( result.getAllErrors().toString() );
			
			model.put("patient", patient);
			
			model.addAttribute("skins", this.skinService.findList());
			model.addAttribute("educationlevels", this.educationlevelService.findList());
			model.addAttribute("civilstatus", this.civilstatusService.findList());
			return this.alias + "/form";
		}else{
			if(!patientService.edit(patient_form)){
				logger.warn("Não foi possível salvar");
				model.addAttribute("message", "Não foi possível salvar");
				model.put("patient", patient_form);
				
				model.addAttribute("skins", this.skinService.findList());
				model.addAttribute("educationlevels", this.educationlevelService.findList());
				model.addAttribute("civilstatus", this.civilstatusService.findList());
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Patient patient, ModelMap model) {
		if(id == null || !patientService.exists(id)){
			logger.error("Identificador inválido");
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		patientService.delete(patientService.findBy("id", id+""));

		return "redirect:/"+ this.alias + "/";
	}
}
