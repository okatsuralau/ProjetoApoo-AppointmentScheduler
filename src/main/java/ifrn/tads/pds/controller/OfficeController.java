package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Office;
import ifrn.tads.pds.service.OfficeService;

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
@RequestMapping("/office")
public class OfficeController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="officeService")
	private OfficeService officeService;
	private String alias = "office";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Office> offices = officeService.findAll();
    	model.addAttribute("title_for_layout", "Salas");
    	return new ModelAndView(this.alias + "/index", "offices", offices);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Ofice") Office office, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova sala");
		return new ModelAndView(this.alias + "/form", "office", new Office());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Office office, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova sala");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("office", office);
			return this.alias + "/form";

		}else{
			if(!officeService.add(office)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("office", office);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Office office, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar sala");

		if(id == null || !officeService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Office _office = officeService.findByID(id);

		return new ModelAndView(this.alias + "/form", "office", _office);
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Office office, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar sala");

		if(id == null || !officeService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Office _office = officeService.findByID(id);

		if(result.hasErrors() || office == null){
			logger.debug("Dados invalidos");
			model.put("office", _office);
			return this.alias + "/form";
		}else{
			if(!officeService.edit(office)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("office", office);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Office office, ModelMap model) {
		if(id == null || !officeService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "forward:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		officeService.delete(id);

		return "forward:/"+ this.alias + "/";
	}
}
