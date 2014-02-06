package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Expertise;
import ifrn.tads.pds.service.ExpertiseService;

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

@Controller
@RequestMapping("/expertise")
public class ExpertiseController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;
	private String alias = "expertise";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Expertise> expertises = expertiseService.findAll();
    	model.addAttribute("title_for_layout", "Especialidades Médicas");
    	return new ModelAndView(this.alias + "/index", "expertises", expertises);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Expertise") Expertise expertise, ModelMap model) {
		model.addAttribute("title_for_layout", "Adicionar nova especialidade");
		model.addAttribute("action", "add");
		return new ModelAndView(this.alias + "/form", "expertise", new Expertise());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Expertise expertise, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova especialidade");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("expertise", expertise);
			return this.alias + "/form";

		}else{
			if(!expertiseService.add(expertise)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("expertise", expertise);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
		//return new ModelAndView(this.alias + "/form", "expertise", new Expertise());
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Expertise expertise, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar especialidade");

		if(id == null || !expertiseService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Expertise _expertise = expertiseService.findByID(id);

		return new ModelAndView(this.alias + "/form", "expertise", _expertise);
	}
	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Expertise expertise, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar especialidade");

		if(id == null || !expertiseService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Expertise _expertise = expertiseService.findByID(id);

		if(result.hasErrors() || expertise == null){
			logger.debug("Dados invalidos");
			model.put("expertise", _expertise);
			return this.alias + "/form";
		}else{
			if(!expertiseService.edit(expertise)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("expertise", expertise);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Expertise expertise, ModelMap model) {
		if(id == null || !expertiseService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "forward:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		expertiseService.delete(id);

		return "forward:/"+ this.alias + "/";
	}
}
