package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Exam;
import ifrn.tads.pds.service.ExamService;

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
@RequestMapping("/exam")
public class ExamController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="examService")
	private ExamService examService;
	private String alias = "exam";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Exam> exam = examService.findAll();
    	model.addAttribute("title_for_layout", "Médicos");
    	return new ModelAndView(this.alias + "/index", "exam", exam);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Ofice") Exam exam, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo exame");
		return new ModelAndView(this.alias + "/form", "exam", new Exam());
	}

	/*@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Exam exam, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo exame");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("exam", exam);
			return this.alias + "/form";

		}else{
			if(!examService.add(exam)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("exam", exam);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}*/

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Exam exam, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar exame");

		if(id == null || !examService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Exam _exam = examService.findByID(id);

		return new ModelAndView(this.alias + "/form", "exam", _exam);
	}

	/*@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Exam exam, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar exame");

		if(id == null || !examService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Exam _exam = examService.findByID(id);

		if(result.hasErrors() || exam == null){
			logger.debug("Dados invalidos");
			model.put("exam", _exam);
			return this.alias + "/form";
		}else{
			if(!examService.edit(exam)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("exam", exam);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}*/

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Exam exam, ModelMap model) {
		if(id == null || !examService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "forward:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		examService.delete(id);

		return "forward:/"+ this.alias + "/";
	}
}
