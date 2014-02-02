package ifrn.tads.pds.controller;

import ifrn.tads.pds.service.ExpertiseService;
import ifrn.tads.pds.service.OfficeService;
import ifrn.tads.pds.service.RoleService;
import ifrn.tads.pds.service.SkinService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class principal do site
*/
// Todo: tentar aplciar todos os métodos importantes aqui e herdar nos demais controllers
@Controller
@RequestMapping("/")
public class AppController {
	
	@Resource(name="roleService")
	private RoleService roleService;
	
	@Resource(name="skinService")
	private SkinService skinService;
	
	@Resource(name="officeService")
	private OfficeService officeService;
	
	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;
	
	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
		
		// TODO: criar objeto genérico (uma espécie de array) com dados:
		// domain_name, Titulo, count, % value
		
		int role_count = roleService.findCount();
		int skin_count = skinService.findCount();
		int office_count = officeService.findCount();
		int expertise_count = expertiseService.findCount();
    	
		model.addAttribute("title_for_layout", "Página");
		model.addAttribute("role_count", role_count);
		model.addAttribute("skin_count", skin_count);
		model.addAttribute("office_count", office_count);
		model.addAttribute("expertise_count", expertise_count);
    	
    	return new ModelAndView("index");
	}
}
