package ifrn.tads.pds.controller;

//import ifrn.tads.pds.App;
import ifrn.tads.pds.helper.ChartHelper;
import ifrn.tads.pds.service.CivilstatusService;
import ifrn.tads.pds.service.DatebookService;
import ifrn.tads.pds.service.DoctorService;
import ifrn.tads.pds.service.EducationlevelService;
import ifrn.tads.pds.service.ExamService;
import ifrn.tads.pds.service.ExpertiseService;
import ifrn.tads.pds.service.HealthPlanService;
import ifrn.tads.pds.service.IndividualService;
import ifrn.tads.pds.service.LogService;
import ifrn.tads.pds.service.OfficeService;
import ifrn.tads.pds.service.PatientService;
import ifrn.tads.pds.service.QueueService;
import ifrn.tads.pds.service.RecordService;
import ifrn.tads.pds.service.RoleService;
import ifrn.tads.pds.service.SkinService;
import ifrn.tads.pds.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class principal do site
*/
// Todo: tentar aplciar todos os métodos importantes aqui e herdar nos demais controllers
@Controller
@RequestMapping(value = {"/dashboard"})
public class AppController{

	@Resource(name="civilstatusService")
	private CivilstatusService civilstatusService;

	@Resource(name="datebookService")
	private DatebookService datebookService;

	@Resource(name="doctorService")
	private DoctorService doctorService;

	@Resource(name="educationlevelService")
	private EducationlevelService educationlevelService;

	@Resource(name="examService")
	private ExamService examService;

	@Resource(name="expertiseService")
	private ExpertiseService expertiseService;

	@Resource(name="healthPlanService")
	private HealthPlanService healthPlanService;
	
	@Resource(name="individualService")
	private IndividualService individualService;
	
	@Resource(name="logService")
	private LogService logService;

	@Resource(name="officeService")
	private OfficeService officeService;

	@Resource(name="patientService")
	private PatientService patientService;

	@Resource(name="queueService")
	private QueueService queueService;

	@Resource(name="recordService")
	private RecordService recordService;

	@Resource(name="roleService")
	private RoleService roleService;

	@Resource(name="skinService")
	private SkinService skinService;

	@Resource(name="userService")
	private UserService userService;
	
	protected static Logger logger = LoggerFactory.getLogger(AppController.class);

	@RequestMapping(value = {"", "/", "/index"})
	public ModelAndView index(ModelMap model, HttpServletRequest request){
		ChartHelper chart_list = new ChartHelper();
		// TODO: pegar o baseUrl automaticamente
		String root = String.format("%s://%s:%d/ProjetoApoo-AppointmentScheduler/", request.getScheme(),  request.getServerName(), request.getServerPort());
		
		logger.debug("teste");
		System.out.println("print test");
		
		chart_list.populateList(chart_list.itemChart("Estado civil", civilstatusService.findCount(), root+"civilstatus"));
		chart_list.populateList(chart_list.itemChart("Agenda de consultas", datebookService.findCount(), root+"datebook"));
		chart_list.populateList(chart_list.itemChart("Médicos", doctorService.findCount(), root+"doctor"));
		chart_list.populateList(chart_list.itemChart("Nível de escolaridade", educationlevelService.findCount(), root+"educationlevel"));
		chart_list.populateList(chart_list.itemChart("Exames", examService.findCount(), root+"exam"));
		chart_list.populateList(chart_list.itemChart("Especialidades", expertiseService.findCount(), root+"expertise"));
		chart_list.populateList(chart_list.itemChart("Planos de saúde", healthPlanService.findCount(), root+"health_plan"));
		chart_list.populateList(chart_list.itemChart("Log de atividade", logService.findCount(), root+"log"));
		chart_list.populateList(chart_list.itemChart("Salas", officeService.findCount(), root+"office"));
		chart_list.populateList(chart_list.itemChart("Pacientes", patientService.findCount(), root+"patient"));
		chart_list.populateList(chart_list.itemChart("Fila de atendimento", queueService.findCount(), root+"queue"));
		chart_list.populateList(chart_list.itemChart("Prontuários", recordService.findCount(), root+"record"));
		chart_list.populateList(chart_list.itemChart("Categorias de usuários", roleService.findCount(), root+"role"));
		chart_list.populateList(chart_list.itemChart("Etnias", skinService.findCount(), root+"skin"));
		chart_list.populateList(chart_list.itemChart("Usuários", userService.findCount(), root+"user"));
		
		model.addAttribute("title_for_layout", "Painel inicial");
		model.addAttribute("chart_list", chart_list.buildChart(chart_list.getItensList())); // Envia, para a view, um objeto com a lista de todos os ítens do menu

		logger.debug("Welcome subject!");
    	return new ModelAndView("dashboard");
	}
}
