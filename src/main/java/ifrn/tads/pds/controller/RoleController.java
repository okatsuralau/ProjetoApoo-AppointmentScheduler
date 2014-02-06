package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Role;
import ifrn.tads.pds.service.RoleService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
@RequestMapping("/role")

public class RoleController{

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name="roleService")
	private RoleService roleService;
	private String alias = "role";

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<Role> roles = roleService.findAll();
    	model.addAttribute("title_for_layout", "Categorias de Usuários");
    	return new ModelAndView(this.alias + "/index", "roles", roles);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("Role") Role role, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova categoria");
		return new ModelAndView(this.alias + "/form", "role", new Role());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid Role role, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar nova categoria");

		if(result.hasErrors()){
			logger.debug("Dados invalidos");
			model.put("role", role);
			return this.alias + "/form";

		}else{
			if(!roleService.add(role)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("role", role);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute Role role, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar categoria");

		if(id == null || !roleService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		Role _role = roleService.findByID(id);

		return new ModelAndView(this.alias + "/form", "role", _role);
	}
	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid Role role, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar categoria");

		if(id == null || !roleService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		Role _role = roleService.findByID(id);

		if(result.hasErrors() || role == null){
			logger.debug("Dados invalidos");
			model.put("role", _role);
			return this.alias + "/form";
		}else{
			if(!roleService.edit(role)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("role", role);
				return this.alias + "/form";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute Role role, ModelMap model) {
		if(id == null || !roleService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "forward:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		roleService.delete(id);

		return "forward:/"+ this.alias + "/";
	}
}
