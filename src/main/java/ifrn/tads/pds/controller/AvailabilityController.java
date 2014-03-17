package ifrn.tads.pds.controller;

import ifrn.tads.pds.domain.Availability;
import ifrn.tads.pds.service.AvailabilityService;
import ifrn.tads.pds.service.DoctorService;
import ifrn.tads.pds.service.ExpertiseService;
import ifrn.tads.pds.service.OfficeService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

// TODO: implementar forms

@Controller
@RequestMapping("/availability")
public class AvailabilityController extends AppController{

	@Resource(name="availabilityService")
	private AvailabilityService availabilityService;

	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;

	@Resource(name="doctorService")
	private DoctorService doctorService;

	@Resource(name="officeService")
	private OfficeService officeService;

	private String alias = "availability";

	@ResponseBody
	@RequestMapping(value="/ajax/expertise/{expertise_id}", produces="application/json")
	public String BuscarPorEspecialidade(@PathVariable(value="expertise_id") Integer expertise_id){
		List<Availability> availabilities = availabilityService.find("all", new JSONObject().put("conditions", "expertise_id = "+ expertise_id));
		int size = availabilities == null ? 0 : availabilities.size();
		if(availabilities == null) availabilities = new ArrayList<Availability>();

		return new JSONObject()
			.put("size", size)
			.put("data", new JSONArray(availabilities))
			.toString();
	}

	@ResponseBody
	@RequestMapping(value="/ajax/doctor/{doctor_id}", produces="application/json")
	public String BuscarPorMedico(@PathVariable(value="doctor_id") Integer doctor_id){
		List<Availability> availabilities = availabilityService.find("all", new JSONObject().put("conditions", "doctor_id = "+ doctor_id));
		int size = availabilities == null ? 0 : availabilities.size();
		if(availabilities == null) availabilities = new ArrayList<Availability>();

		return new JSONObject()
			.put("size", size)
			.put("data", new JSONArray(availabilities))
			.toString();
	}

	@ResponseBody
	@RequestMapping(value="/ajax/office/{office_id}", produces="application/json")
	public String BuscarPorSala(@PathVariable(value="office_id") Integer office_id){
		List<Availability> availabilities = availabilityService.find("all", new JSONObject().put("conditions", "office_id = "+ office_id));
		int size = availabilities == null ? 0 : availabilities.size();
		if(availabilities == null) availabilities = new ArrayList<Availability>();

		return new JSONObject()
			.put("size", size)
			.put("data", new JSONArray(availabilities))
			.toString();
	}

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
		model.addAttribute("title_for_layout", "Disponibilidade");
		model.addAttribute("expertises", expertiseService.findList());
		model.addAttribute("doctors", doctorService.findList());
		model.addAttribute("offices", officeService.findList());

		return new ModelAndView(this.alias + "/index", "availabilities", availabilityService.find("all"));
	}

	@RequestMapping(value = {"/{page}", "/index/{page}"}, method = RequestMethod.GET)
	public ModelAndView index(@PathVariable(value="page") Integer page, ModelMap model, HttpServletRequest request){
		List<Availability> availabilities = availabilityService.find("all", new JSONObject().put("offset", page));
		//JSONObject availabilities = availabilityService.find("all", new JSONObject().put("offset", page));
		model.addAttribute("title_for_layout", "Disponibilidade");

		return new ModelAndView(this.alias + "/index", "availabilities", availabilities);
	}
}
