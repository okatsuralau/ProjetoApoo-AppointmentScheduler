package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Datebook;
import ifrn.tads.pds.service.DatebookService;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

// TODO: implementar forms

@Controller
@RequestMapping("/datebook")
public class DatebookController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="datebookService")
	private DatebookService datebookService;
	private String alias = "datebook";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Datebook> datebook = datebookService.find("all");
    	model.addAttribute("title_for_layout", "Agenda de consultas");
    	return new ModelAndView(this.alias + "/index", "datebook", datebook);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Ofice") Datebook datebook, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova consulta");
		return new ModelAndView(this.alias + "/form", "datebook", new Datebook());
	}

	/*@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Datebook datebook, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova consulta");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("datebook", datebook);
			return this.alias + "/form";

		}else{
			if(!datebookService.add(datebook)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("datebook", datebook);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}*/

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Datebook datebook, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar registro de consulta");

		if(id == null || !datebookService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Datebook _datebook = datebookService.findBy("id", id+"");

		return new ModelAndView(this.alias + "/form", "datebook", _datebook);
	}

	/*@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Datebook datebook, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar registro de consulta");

		if(id == null || !datebookService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Datebook _datebook = datebookService.findByID(id);

		if(result.hasErrors() || datebook == null){
			logger.debug("Dados invalidos");
			model.put("datebook", _datebook);
			return this.alias + "/form";
		}else{
			if(!datebookService.edit(datebook)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("datebook", datebook);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}*/

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Datebook datebook, ModelMap model) {
		if(id == null || !datebookService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		datebookService.delete(datebookService.findBy("id", id+""));

		return "redirect:/"+ this.alias + "/";
	}
}
