package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Doctor;
import ifrn.tads.pds.service.DoctorService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class DoctorController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="doctorService")
	private DoctorService doctorService;
	private String alias = "doctor";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Doctor> doctor = doctorService.findAll();
    	model.addAttribute("title_for_layout", "Médicos");
    	return new ModelAndView(this.alias + "/index", "doctor", doctor);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Ofice") Doctor doctor, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo médico");
		return new ModelAndView(this.alias + "/form", "doctor", new Doctor());
	}

	/*@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Doctor doctor, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo médico");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("doctor", doctor);
			return this.alias + "/form";

		}else{
			if(!doctorService.add(doctor)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("doctor", doctor);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}*/

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Doctor doctor, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar médico");

		if(id == null || !doctorService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Doctor _doctor = doctorService.findByID(id);

		return new ModelAndView(this.alias + "/form", "doctor", _doctor);
	}

	/*@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Doctor doctor, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar médico");

		if(id == null || !doctorService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Doctor _doctor = doctorService.findByID(id);

		if(result.hasErrors() || doctor == null){
			logger.debug("Dados invalidos");
			model.put("doctor", _doctor);
			return this.alias + "/form";
		}else{
			if(!doctorService.edit(doctor)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("doctor", doctor);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}*/

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Doctor doctor, ModelMap model) {
		if(id == null || !doctorService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "forward:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		doctorService.delete(id);

		return "forward:/"+ this.alias + "/";
	}
}
