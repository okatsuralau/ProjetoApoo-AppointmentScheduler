package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Expertise;
import ifrn.tads.pds.service.ExpertiseService;

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
@RequestMapping("/expertise")
public class ExpertiseController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;
	private String alias = "expertise";
	
	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Expertise> expertises = expertiseService.findAll();
    	// Attach persons to the Model
    	//model.addAttribute("expertises", expertises);
		
    	//return "index";
    	return new ModelAndView(this.alias + "/index", "expertises", expertises);
	}
	
	@RequestMapping("/add")
	public ModelAndView add(@ModelAttribute Expertise expertise) {
		return new ModelAndView(this.alias + "/form", "expertise", new Expertise());
	}
	
	@RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Expertise expertise, ModelMap model) { 
		if(id == null || !expertiseService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}
		
		Expertise _expertise = expertiseService.findByID(id);

		return new ModelAndView(this.alias + "/form", "expertise", _expertise);
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Expertise expertise, ModelMap model) { 
		if(id == null || !expertiseService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}
		
		// TODO: adicionar verificação de sucesso
		expertiseService.delete(id);

		return "redirect:/"+ this.alias + "/";
	}
	
	
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public String process(@ModelAttribute(value="Expertise") Expertise expertise, BindingResult result, Model model){
		//public String process(@RequestParam(value="title", required=true) String title, @RequestParam(value="slug", required=true) String slug) {

		if(result.hasErrors() || expertise == null){
			logger.debug("Dados invalidos");
			model.addAttribute("mensagem", "Dados invalidos");

			// TODO: verificar de onde vem o form (add ou edit) e redirecionar para a página equivalente

			return "redirect:/"+ this.alias + "/";
		}
		expertiseService.add(expertise);
		return "redirect:/"+ this.alias + "/";
	}
}