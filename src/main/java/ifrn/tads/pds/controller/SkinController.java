package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Skin;
import ifrn.tads.pds.service.SkinService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

@Controller
@RequestMapping("/skin")
public class SkinController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="skinService")
	private SkinService skinService;
	private String alias = "skin";
	
	public void SkinController(Model model){
		model.addAttribute("title_for_layout", "Skins");
	}
	
	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Skin> skins = skinService.findAll();
    	return new ModelAndView(this.alias + "/index", "skins", skins);
	}
	
	@RequestMapping("/add")
	public ModelAndView add(@ModelAttribute Skin skin) {
		return new ModelAndView(this.alias + "/form", "skin", new Skin());
	}
	
	@RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Skin skin, ModelMap model) { 
		if(id == null || !skinService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}
		
		Skin _skin = skinService.findByID(id);

		return new ModelAndView(this.alias + "/form", "skin", _skin);
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Skin skin, ModelMap model) { 
		if(id == null || !skinService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}
		
		// TODO: adicionar verificação de sucesso
		skinService.delete(id);

		return "redirect:/"+ this.alias + "/";
	}
	
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public String process(@ModelAttribute(value="Skin") Skin skin, BindingResult result, HttpServletRequest request, Model model){
		if(result.hasErrors() || skin == null){
			logger.debug("Dados invalidos");
			model.addAttribute("mensagem", "Dados invalidos");
		}else{
			if(request.getParameter("action").equalsIgnoreCase("add")){
				skinService.add(skin);
			}else if(request.getParameter("action").equalsIgnoreCase("edit")){
				skinService.edit(skin);
			}
		}
		return "redirect:/"+ this.alias + "/";
	}
}
