package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Queue;
import ifrn.tads.pds.service.QueueService;

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

// TODO: implementar forms

@Controller
@RequestMapping("/queue")
public class QueueController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="queueService")
	private QueueService queueService;
	private String alias = "queue";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Queue> queue = queueService.findAll();
    	model.addAttribute("title_for_layout", "Médicos");
    	return new ModelAndView(this.alias + "/index", "queue", queue);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Ofice") Queue queue, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo médico");
		return new ModelAndView(this.alias + "/form", "queue", new Queue());
	}

	/*@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Queue queue, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo médico");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("queue", queue);
			return this.alias + "/form";

		}else{
			if(!queueService.add(queue)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("queue", queue);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}*/

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Queue queue, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar médico");

		if(id == null || !queueService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Queue _queue = queueService.findByID(id);

		return new ModelAndView(this.alias + "/form", "queue", _queue);
	}

	/*@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Queue queue, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar médico");

		if(id == null || !queueService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Queue _queue = queueService.findByID(id);

		if(result.hasErrors() || queue == null){
			logger.debug("Dados invalidos");
			model.put("queue", _queue);
			return this.alias + "/form";
		}else{
			if(!queueService.edit(queue)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("queue", queue);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}*/

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Queue queue, ModelMap model) {
		if(id == null || !queueService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "forward:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		queueService.delete(id);

		return "forward:/"+ this.alias + "/";
	}
}
