package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.User;
import ifrn.tads.pds.service.UserService;

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
@RequestMapping("/user")
public class UserController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="userService")
	private UserService userService;
	private String alias = "user";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<User> user = userService.findAll();
    	model.addAttribute("title_for_layout", "Usuários");
    	return new ModelAndView(this.alias + "/index", "user", user);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Ofice") User user, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo usuário");
		return new ModelAndView(this.alias + "/form", "user", new User());
	}

	/*@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid User user, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo usuário");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("user", user);
			return this.alias + "/form";

		}else{
			if(!userService.add(user)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("user", user);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}*/

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute User user, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar usuário");

		if(id == null || !userService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		User _user = userService.findByID(id);

		return new ModelAndView(this.alias + "/form", "user", _user);
	}

	/*@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid User user, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar usuário");

		if(id == null || !userService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		User _user = userService.findByID(id);

		if(result.hasErrors() || user == null){
			logger.debug("Dados invalidos");
			model.put("user", _user);
			return this.alias + "/form";
		}else{
			if(!userService.edit(user)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("user", user);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}*/

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute User user, ModelMap model) {
		if(id == null || !userService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "forward:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		userService.delete(id);

		return "forward:/"+ this.alias + "/";
	}
}
