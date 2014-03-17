package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Civilstatus;
import ifrn.tads.pds.service.CivilstatusService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/civilstatus")
public class CivilstatusController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="civilstatusService")
	private CivilstatusService civilstatusService;
	private String alias = "civilstatus";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Civilstatus> civilstatus = civilstatusService.find("all");
    	model.addAttribute("title_for_layout", "Estado Civil");
    	return new ModelAndView(this.alias + "/index", "civilstatus", civilstatus);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Ofice") Civilstatus civilstatus, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo estado civil");
		return new ModelAndView(this.alias + "/form", "civilstatus", new Civilstatus());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Civilstatus civilstatus, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo estado civil");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("civilstatus", civilstatus);
			return this.alias + "/form";

		}else{
			if(!civilstatusService.add(civilstatus)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("civilstatus", civilstatus);
				return this.alias + "/form";
			}else{
				logger.info("O registro foi salvo com sucesso");
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Civilstatus civilstatus, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar estado civil");

		if(id == null || !civilstatusService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Civilstatus _civilstatus = civilstatusService.findBy("id", id + "");

		return new ModelAndView(this.alias + "/form", "civilstatus", _civilstatus);
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Civilstatus civilstatus, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar estado civil");

		if(id == null || !civilstatusService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Civilstatus _civilstatus = civilstatusService.findBy("id", id+"");

		if(result.hasErrors() || civilstatus == null){
			logger.debug("Dados invalidos");
			model.put("civilstatus", _civilstatus);
			return this.alias + "/form";
		}else{
			if(!civilstatusService.edit(civilstatus)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("civilstatus", civilstatus);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Civilstatus civilstatus, ModelMap model) {
		if(id == null || !civilstatusService.exists(id)){
			logger.error("Identificador inválido");
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		civilstatusService.delete(civilstatusService.findBy("id", id+""));

		return "redirect:/" + this.alias + "/";
	}
}
