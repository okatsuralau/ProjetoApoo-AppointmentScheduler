package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.User;
import ifrn.tads.pds.service.RoleService;
import ifrn.tads.pds.service.UserService;
import ifrn.tads.pds.service.SkinService;
import ifrn.tads.pds.service.EducationlevelService;
import ifrn.tads.pds.service.CivilstatusService;

import java.util.List;
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
@RequestMapping("/user")
public class UserController extends AppController{
	
	private String alias = "user";
	
	@Resource(name="userService")
	private UserService userService;

	@Resource(name="roleService")
	private RoleService roleService;
	
	@Resource(name="skinService")
	private SkinService skinService;
	
	@Resource(name="educationlevelService")
	private EducationlevelService educationlevelService;
	
	@Resource(name="civilstatusService")
	private CivilstatusService civilstatusService;

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
    	List<User> user = userService.find("all");
    	model.addAttribute("title_for_layout", "Usuários");
    	return new ModelAndView(this.alias + "/index", "user", user);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@ModelAttribute("User") User user, ModelMap model) {
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo usuário");
		model.addAttribute("roles", this.roleService.findList());
		model.addAttribute("skins", this.skinService.findList());
		model.addAttribute("educationlevels", this.educationlevelService.findList());
		model.addAttribute("civilstatus", this.civilstatusService.findList());
		return new ModelAndView(this.alias + "/add", "user", new User());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(@Valid User user, BindingResult result, ModelMap model, RedirectAttributes ra){
		model.addAttribute("action", "add");
		model.addAttribute("title_for_layout", "Adicionar novo usuário");

		if(result.hasErrors()){
			/*List<FieldError> teste = result.getFieldErrors();
			for (FieldError fieldError : teste) {
				logger.info( teste.toString() );
			}*/
			
			logger.info("Dados invalidos");
			model.addAttribute("message", "Dados invalidos");
			model.put("user", user);
			model.addAttribute("roles", this.roleService.findList());
			model.addAttribute("skins", this.skinService.findList());
			model.addAttribute("educationlevels", this.educationlevelService.findList());
			model.addAttribute("civilstatus", this.civilstatusService.findList());
			return this.alias + "/add";

		}else{
			if(!userService.add(user)){
				logger.error("Não foi possível salvar");
				model.addAttribute("message", "Não foi possível salvar");
				model.addAttribute("roles", this.roleService.findList());
				model.addAttribute("skins", this.skinService.findList());
				model.addAttribute("educationlevels", this.educationlevelService.findList());
				model.addAttribute("civilstatus", this.civilstatusService.findList());
				model.put("user", user);
				return this.alias + "/add";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/index";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value="id") Integer id, @ModelAttribute User user, ModelMap model) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar usuário");

		if(id == null || !userService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return new ModelAndView(this.alias + "/index");
		}

		user = userService.findBy("id", id+"", null, true);
		model.addAttribute("roles", this.roleService.findList());
		model.addAttribute("skins", this.skinService.findList());
		model.addAttribute("educationlevels", this.educationlevelService.findList());
		model.addAttribute("civilstatus", this.civilstatusService.findList());

		return new ModelAndView(this.alias + "/edit", "user", user);
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public String editPost(@PathVariable(value="id") Integer id, @Valid User user, BindingResult result, ModelMap model, RedirectAttributes ra) {
		model.addAttribute("action", "edit/"+id);
		model.addAttribute("title_for_layout", "Alterar usuário");

		if(id == null || !userService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return this.alias + "/index";
		}

		User _user = userService.findBy("id", id+"", null, true);
		if(result.hasErrors() || user == null){
			logger.debug("Dados invalidos");
			model.put("user", _user);
			model.addAttribute("roles", this.roleService.findList());
			model.addAttribute("skins", this.skinService.findList());
			model.addAttribute("educationlevels", this.educationlevelService.findList());
			model.addAttribute("civilstatus", this.civilstatusService.findList());
			return this.alias + "/edit";
		}else{
			if(!userService.edit(user)){
				model.addAttribute("message", "Não foi possível salvar");
				model.put("user", user);
				model.addAttribute("roles", this.roleService.findList());
				model.addAttribute("skins", this.skinService.findList());
				model.addAttribute("educationlevels", this.educationlevelService.findList());
				model.addAttribute("civilstatus", this.civilstatusService.findList());
				return this.alias + "/edit";
			}else{
				model.addAttribute("message", "O registro foi salvo com sucesso");
			}
		}

		return "redirect:/" + this.alias + "/";
	}

	// TODO: aplicar metodo DELETE
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value="id") Integer id, @ModelAttribute User user, ModelMap model) {
		if(id == null || !userService.exists(id)){
			model.addAttribute("mensagem", "Identificador inválido");
			return "redirect:/" + this.alias + "/";
		}

		// TODO: adicionar verificação de sucesso
		userService.delete(userService.findBy("id", id+""));

		return "redirect:/"+ this.alias + "/";
	}
}
