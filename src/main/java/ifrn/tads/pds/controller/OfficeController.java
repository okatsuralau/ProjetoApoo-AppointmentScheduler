package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Office;
import ifrn.tads.pds.service.OfficeService;

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
@RequestMapping("/office")
public class OfficeController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="officeService")
	private OfficeService officeService;
	private String alias = "office";
	
	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Office> offices = officeService.findAll();
    	// Attach persons to the Model
    	//model.addAttribute("offices", offices);
		
    	//return "index";
    	return new ModelAndView(this.alias + "/index", "offices", offices);
	}
	
	@RequestMapping("/add")
	public ModelAndView add(@ModelAttribute Office office) {
		return new ModelAndView(this.alias + "/form", "office", new Office());
	}
	
	@RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Office office, ModelMap model) { 
		if(id == null || !officeService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}
		
		Office _office = officeService.findByID(id);

		return new ModelAndView(this.alias + "/form", "office", _office);
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Office office, ModelMap model) { 
		if(id == null || !officeService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}
		
		// TODO: adicionar verificação de sucesso
		officeService.delete(id);

		return "redirect:/"+ this.alias + "/";
	}
	
	
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public String process(@ModelAttribute(value="Office") Office office, BindingResult result, Model model){
		//public String process(@RequestParam(value="title", required=true) String title, @RequestParam(value="slug", required=true) String slug) {

		if(result.hasErrors() || office == null){
			logger.debug("Dados invalidos");
			model.addAttribute("mensagem", "Dados invalidos");

			// TODO: verificar de onde vem o form (add ou edit) e redirecionar para a página equivalente

			return "redirect:/"+ this.alias + "/";
		}
		officeService.add(office);
		return "redirect:/"+ this.alias + "/";
	}
}
