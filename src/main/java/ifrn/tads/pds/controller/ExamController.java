package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Exam;
import ifrn.tads.pds.domain.Expertise;
import ifrn.tads.pds.service.ExamService;
import ifrn.tads.pds.service.ExpertiseService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
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
public class ExamController extends AppController{

	@Resource(name="examService")
	private ExamService examService;
	private String alias = "exam";
	
	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Exam> exam = examService.find("all");
    	model.addAttribute("title_for_layout", "Exames");
    	return new ModelAndView(this.alias + "/index", "exam", exam);
	}
	
	// TODO: descobrir como listar mais facilmente os dados
	private Map<String, String> expertiseList(){
		//Map referenceData = new HashMap();
		Map<String, String> expertise_map = new LinkedHashMap<String, String>();
		List<Expertise> expertises = expertiseService.find("all");
		for (Expertise expertise : expertises) {
			expertise_map.put(expertise.getId()+"", expertise.getTitle());
		}
		
		return expertise_map;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Doctor") Exam exam, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo exame");
		
		model.addAttribute("expertises", this.expertiseList());
		
		// TODO: carregar os dados associados
		
		return new ModelAndView(this.alias + "/form", "exam", new Exam());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Exam exam, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo exame");
		
		if(result.hasErrors()){
			logger.error("Dados invalidos");
			model.put("exam", exam);
			model.addAttribute("expertises", this.expertiseList());
			
			return this.alias + "/form";
		}else{
			if(!examService.add(exam)){
				logger.warn("Não foi possível salvar");
				model.addAttribute("message", "Não foi possível salvar");
				model.put("exam", exam);
				model.addAttribute("expertises", this.expertiseList());
				return this.alias + "/form";
			}else{
				logger.info("O registro foi salvo com sucesso");
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}
	
	/**
	 * Edit
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Exam exam, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar exame");
		
		if(id == null || !examService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			logger.error("Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}
		
		// TODO: setar os valores de expertises e individual id para o form

		Exam _exam = examService.findBy("id", id+"");
		model.addAttribute("expertises", this.expertiseList());

		return new ModelAndView(this.alias + "/form", "exam", _exam);
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Exam exam, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar exame");

		if(id == null || !examService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			logger.error("Identificador inválido");
			return this.alias + "/index";
		}

		Exam _exam = examService.findBy("id", id+"");

		if(result.hasErrors() || exam == null){
			logger.error("Dados invalidos");
			model.put("exam", _exam);
			model.addAttribute("expertises", this.expertiseList());
			return this.alias + "/form";
		}else{
			if(!examService.edit(exam)){
				logger.warn("Não foi possível salvar");
				model.addAttribute("message", "Não foi possível salvar");
				model.addAttribute("expertises", this.expertiseList());
				model.put("exam", exam);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Exam exam, ModelMap model) {
		if(id == null || !examService.exists(id)){
			logger.error("Identificador inválido");
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		examService.delete(examService.findBy("id", id+""));

		return "redirect:/"+ this.alias + "/";
	}
}
