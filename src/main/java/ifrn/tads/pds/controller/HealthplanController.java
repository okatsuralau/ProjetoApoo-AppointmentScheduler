package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Healthplan;
import ifrn.tads.pds.service.HealthplanService;

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
@RequestMapping("/healthplan")
public class HealthplanController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="healthplanService")
	private HealthplanService healthplanService;
	private String alias = "healthplan";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Healthplan> healthplan = healthplanService.findAll();
    	model.addAttribute("title_for_layout", "Médicos");
    	return new ModelAndView(this.alias + "/index", "healthplan", healthplan);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Ofice") Healthplan healthplan, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo healthplane");
		return new ModelAndView(this.alias + "/form", "healthplan", new Healthplan());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Healthplan healthplan, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo healthplane");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("healthplan", healthplan);
			return this.alias + "/form";

		}else{
			if(!healthplanService.add(healthplan)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("healthplan", healthplan);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Healthplan healthplan, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar healthplane");

		if(id == null || !healthplanService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Healthplan _healthplan = healthplanService.findByID(id);

		return new ModelAndView(this.alias + "/form", "healthplan", _healthplan);
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Healthplan healthplan, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar healthplane");

		if(id == null || !healthplanService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Healthplan _healthplan = healthplanService.findByID(id);

		if(result.hasErrors() || healthplan == null){
			logger.debug("Dados invalidos");
			model.put("healthplan", _healthplan);
			return this.alias + "/form";
		}else{
			if(!healthplanService.edit(healthplan)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("healthplan", healthplan);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Healthplan healthplan, ModelMap model) {
		if(id == null || !healthplanService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "forward:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		healthplanService.delete(id);

		return "forward:/"+ this.alias + "/";
	}
}
