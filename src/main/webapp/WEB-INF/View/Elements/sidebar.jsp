<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="root" value='/' />
<c:set var="root" value='${root}' />

<nav id="mp-menu" class="mp-menu">
	<div class="mp-level">
		<h2>Navegação</h2>
		<%@ page import="ifrn.tads.pds.helper.MenuHelper" %>
		<%
			// TODO: Exibir somente os links que o usuário logado tem acesso

			MenuHelper menu_sidebar = new MenuHelper();
			String root = pageContext.getAttribute("root").toString();

			//
			// Definição dos ítens do menu
			//
			menu_sidebar.populateList(menu_sidebar.itemMenu("Painel Inicial", "glyphicon-home", root));

			// Médicos
			menu_sidebar.populateList(menu_sidebar.itemMenu("Médicos", "glyphicon-minus", root+"doctor/index",
					menu_sidebar.populateSubList(
						menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"doctor/index"),
						menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"doctor/add"),

						menu_sidebar.itemMenu("Configurar horario", "glyphicon-plus", root+"schedule/index"),
						menu_sidebar.itemMenu("Disponibilidade", "glyphicon-plus", root+"availability/index"),

						// Especialidades Médicas
						menu_sidebar.itemMenu("Especialidades Médicas", "glyphicon-minus", root+"expertise/add",
							menu_sidebar.populateSubList(
								menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"expertise/index"),
								menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"expertise/add")
							)
						)
					)
				)
			);

			// Consultas
			/*menu_sidebar.populateList(menu_sidebar.itemMenu("Pacientes", "glyphicon-minus", root+"patient/index",
					menu_sidebar.populateSubList(
						menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"patient/index"),
						menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"patient/add")
					)
				)
			);*/


			// Pacientes
			menu_sidebar.populateList(menu_sidebar.itemMenu("Pacientes", "glyphicon-minus", root+"patient/index",
					menu_sidebar.populateSubList(
						menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"patient/index"),
						menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"patient/add")
					)
				)
			);

			// Exames
			menu_sidebar.populateList(menu_sidebar.itemMenu("Exames", "glyphicon-minus", root+"exam/index",
					menu_sidebar.populateSubList(
						menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"exam/index"),
						menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"exam/add")
					)
				)
			);

			// Outros
			menu_sidebar.populateList(menu_sidebar.itemMenu("Outros", "glyphicon-minus", "#",
					menu_sidebar.populateSubList(

						// Planos de saúde
						menu_sidebar.itemMenu("Planos de saúde", "glyphicon-minus", root+"healthplan/index",
							menu_sidebar.populateSubList(
								menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"healthplan/index"),
								menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"healthplan/add")
							)
						),

						// Estado Civil
						menu_sidebar.itemMenu("Estado Civil", "glyphicon-minus", root+"civilstatus/index",
							menu_sidebar.populateSubList(
								menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"civilstatus/index"),
								menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"civilstatus/add")
							)
						),

						// Pessoa Física (Exibe nos models associados: Users, Doctors, Patients)
						// menu_sidebar.itemMenu("Pessoa Física", "glyphicon-minus", root+"individual/index",
						// 	menu_sidebar.populateSubList(
						// 		menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"individual/index"),
						// 		menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"individual/add")
						// 	)
						// ),

						// Escolaridade
						menu_sidebar.itemMenu("Escolaridade", "glyphicon-minus", root+"educationlevel/index",
							menu_sidebar.populateSubList(
								menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"educationlevel/index"),
								menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"educationlevel/add")
							)
						),

						// Salas
						menu_sidebar.itemMenu("Salas", "glyphicon-minus", root+"office/index",
							menu_sidebar.populateSubList(
								menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"office/index"),
								menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"office/add")
							)
						),

						// Etnias
						menu_sidebar.itemMenu("Etnias", "glyphicon-minus", root+"skin/index",
							menu_sidebar.populateSubList(
								menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"skin/index"),
								menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"skin/add")
							)
						)
					)
				)
			);


			// User
			menu_sidebar.populateList(menu_sidebar.itemMenu("Usuários", "glyphicon-minus", root+"user/index",
					menu_sidebar.populateSubList(
						menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"user/index"),
						menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"user/add"),

						// Role
						menu_sidebar.itemMenu("Categoria de Usuários", "glyphicon-minus", root+"role/index",
							menu_sidebar.populateSubList(
								menu_sidebar.itemMenu("Listar", "glyphicon-list", root+"role/index"),
								menu_sidebar.itemMenu("Add", "glyphicon-plus", root+"role/add")
							)
						)
					)
				)
			);
		%>
		<%= menu_sidebar.buildSidebarMenu(menu_sidebar.getItensList()) %>
	</div>
</nav>
