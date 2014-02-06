package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Educationlevel;
import ifrn.tads.pds.service.EducationlevelService;

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
@RequestMapping("/educationlevel")
public class EducationlevelController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="educationlevelService")
	private EducationlevelService educationlevelService;
	private String alias = "educationlevel";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Educationlevel> educationlevel = educationlevelService.findAll();
    	model.addAttribute("title_for_layout", "Médicos");
    	return new ModelAndView(this.alias + "/index", "educationlevel", educationlevel);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Ofice") Educationlevel educationlevel, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo nível de escolaridade");
		return new ModelAndView(this.alias + "/form", "educationlevel", new Educationlevel());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Educationlevel educationlevel, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo nível de escolaridade");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("educationlevel", educationlevel);
			return this.alias + "/form";

		}else{
			if(!educationlevelService.add(educationlevel)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("educationlevel", educationlevel);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Educationlevel educationlevel, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar nível de escolaridade");

		if(id == null || !educationlevelService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Educationlevel _educationlevel = educationlevelService.findByID(id);

		return new ModelAndView(this.alias + "/form", "educationlevel", _educationlevel);
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Educationlevel educationlevel, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar nível de escolaridade");

		if(id == null || !educationlevelService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Educationlevel _educationlevel = educationlevelService.findByID(id);

		if(result.hasErrors() || educationlevel == null){
			logger.debug("Dados invalidos");
			model.put("educationlevel", _educationlevel);
			return this.alias + "/form";
		}else{
			if(!educationlevelService.edit(educationlevel)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("educationlevel", educationlevel);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Educationlevel educationlevel, ModelMap model) {
		if(id == null || !educationlevelService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "forward:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		educationlevelService.delete(id);

		return "forward:/"+ this.alias + "/";
	}
}
