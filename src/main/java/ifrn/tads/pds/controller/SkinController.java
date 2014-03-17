package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Skin;
import ifrn.tads.pds.service.SkinService;

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
@RequestMapping("/skin")
public class SkinController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="skinService")
	private SkinService skinService;
	private String alias = "skin";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Skin> skins = skinService.find("all");
    	model.addAttribute("title_for_layout", "Etnias");
    	return new ModelAndView(this.alias + "/index", "skins", skins);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Ofice") Skin skin, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova etnia");
		return new ModelAndView(this.alias + "/form", "skin", new Skin());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Skin skin, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova etnia");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("skin", skin);
			return this.alias + "/form";

		}else{
			if(!skinService.add(skin)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("skin", skin);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Skin skin, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar etnia");

		if(id == null || !skinService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Skin _skin = skinService.findBy("id", id + "");

		return new ModelAndView(this.alias + "/form", "skin", _skin);
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Skin skin, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar etnia");

		if(id == null || !skinService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Skin _skin = skinService.findBy("id", id + "");

		if(result.hasErrors() || skin == null){
			logger.debug("Dados invalidos");
			model.put("skin", _skin);
			return this.alias + "/form";
		}else{
			if(!skinService.edit(skin)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("skin", skin);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Skin skin, ModelMap model) {
		if(id == null || !skinService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		skinService.delete(skinService.findBy("id", id+""));

		return "redirect:/"+ this.alias + "/";
	}
}
