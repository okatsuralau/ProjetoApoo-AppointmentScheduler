<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="root" value='/' />
<c:set var="root" value='${root}' />

<nav id="mp-menu" class="mp-menu">
    <div class="mp-level">
        <h2>Navegação</h2>
        <%@ page import="org.json.JSONException, org.json.JSONObject" %>
        <%!
			// Scriptlet Helpers
        	static JSONObject itemMenu(String label, String icon, String url, JSONObject submenu){
        		return new JSONObject().put("label", label).put("icon", icon).put("url", url).put("children", submenu);
        	}

        	static JSONObject itemMenu(String label, String icon, String url){
        		return new JSONObject().put("label", label).put("icon", icon).put("url", url);
        	}

		    static void populateMenu(JSONObject menu, JSONObject item){
		    	menu.put((menu.length()) + "", item);
		    }

		    static JSONObject populateSubMenu(JSONObject...subMenu){
		    	JSONObject itens_submenu = new JSONObject();
		    	for(int i = 0; i < subMenu.length; i++){
		            itens_submenu.put(i+"", subMenu[i]);
		        }
		    	return itens_submenu;
		    }

		    String buildSidebarMenu(JSONObject links){
				String  out = "";
				for (int i = 0; i < links.length(); i++) {

					if( links.has(i + "") ){

						String class_li = "";
						JSONObject link = links.getJSONObject(i+"");

						if( link.has("children") ){
							class_li = " icon-left ";
						}

						if(!class_li.isEmpty()){
							class_li = " class=\"+class_li+\"";
						}

						out += "<li" + class_li + ">";
							out += "<a href=\"" + link.get("url") + "\"><i class=\"glyphicon " + link.get("icon") + "\"></i>" + link.get("label") + "</a>";

							if(link.has("children")){
								out += "<div class=\"mp-level\"> " +
											"<h2><i class=\"glyphicon " + link.get("icon") + "\"></i>" + link.get("label") + "</h2>";
	                            	out += "<a class=\"mp-back\" href=\"#\">voltar <i class=\"glyphicon glyphicon-share-alt\"></i></a>";
	                            	out += buildSidebarMenu((JSONObject)link.get("children"));
	                            out += "</div>";
							}
						out += "</li>";
					}
				}

				out = "<ul>" + out + "</ul>";
				return out;
			}
		%>
		<%
			// TODO: Exibir somente os links que o usuário logado tem acesso

			String root = pageContext.getAttribute("root").toString();
		    JSONObject menu_sidebar = new JSONObject();

		    //
		    // Definição dos ítens do menu
		    //
		    populateMenu(menu_sidebar, itemMenu("Painel Inicial", "glyphicon-home", root));

		    // Médicos
		    populateMenu(menu_sidebar, itemMenu("Médicos", "glyphicon-minus", root+"doctor/index",
		    		populateSubMenu(
		    			itemMenu("Listar", "glyphicon-list", root+"doctor/index"),
		    			itemMenu("Add", "glyphicon-plus", root+"doctor/add"),

		    			// Especialidades Médicas
		    			itemMenu("Especialidades Médicas", "glyphicon-minus", root+"expertise/add",
			    			populateSubMenu(
				    			itemMenu("Listar", "glyphicon-list", root+"expertise/index"),
				    			itemMenu("Add", "glyphicon-plus", root+"expertise/add")
			    			)
			    		)
		    		)
		    	)
		    );

		    // Pacientes
		    populateMenu(menu_sidebar, itemMenu("Pacientes", "glyphicon-minus", root+"patient/index",
		    		populateSubMenu(
		    			itemMenu("Listar", "glyphicon-list", root+"patient/index"),
		    			itemMenu("Add", "glyphicon-plus", root+"patient/add")
		    		)
		    	)
		    );

		    // Exames
		    populateMenu(menu_sidebar, itemMenu("Exames", "glyphicon-minus", root+"exam/index",
		    		populateSubMenu(
			    		itemMenu("Listar", "glyphicon-list", root+"exam/index"),
			    		itemMenu("Add", "glyphicon-plus", root+"exam/add")
		    		)
		    	)
		    );

		    // Outros
		    populateMenu(menu_sidebar, itemMenu("Outros", "glyphicon-minus", "#",
		    		populateSubMenu(

		    			// Planos de saúde
			    		itemMenu("Planos de saúde", "glyphicon-minus", root+"health_plan/index",
				    		populateSubMenu(
					    		itemMenu("Listar", "glyphicon-list", root+"health_plan/index"),
					    		itemMenu("Add", "glyphicon-plus", root+"health_plan/add")
				    		)
				    	),

				    	// Estado Civil
			    		itemMenu("Estado Civil", "glyphicon-minus", root+"civilstatus/index",
				    		populateSubMenu(
					    		itemMenu("Listar", "glyphicon-list", root+"civilstatus/index"),
					    		itemMenu("Add", "glyphicon-plus", root+"civilstatus/add")
				    		)
				    	),

				    	// Pessoa Física
				    	itemMenu("Pessoa Física", "glyphicon-minus", root+"individual/index",
				    		populateSubMenu(
					    		itemMenu("Listar", "glyphicon-list", root+"individual/index"),
					    		itemMenu("Add", "glyphicon-plus", root+"individual/add")
				    		)
				    	),

				    	// Escolaridade
				    	itemMenu("Escolaridade", "glyphicon-minus", root+"educationlevel/index",
				    		populateSubMenu(
					    		itemMenu("Listar", "glyphicon-list", root+"educationlevel/index"),
					    		itemMenu("Add", "glyphicon-plus", root+"educationlevel/add")
				    		)
				    	),

				    	// Salas
				    	itemMenu("Salas", "glyphicon-minus", root+"office/index",
				    		populateSubMenu(
					    		itemMenu("Listar", "glyphicon-list", root+"office/index"),
					    		itemMenu("Add", "glyphicon-plus", root+"office/add")
				    		)
				    	),

				    	// Etnias
				    	itemMenu("Etnias", "glyphicon-minus", root+"skin/index",
				    		populateSubMenu(
					    		itemMenu("Listar", "glyphicon-list", root+"skin/index"),
					    		itemMenu("Add", "glyphicon-plus", root+"skin/add")
					    	)
				    	)
		    		)
		    	)
		    );


		    // User
		    populateMenu(menu_sidebar, itemMenu("Usuários", "glyphicon-minus", root+"skin/index",
		    		populateSubMenu(
			    		itemMenu("Listar", "glyphicon-list", root+"skin/index"),
			    		itemMenu("Add", "glyphicon-plus", root+"skin/add"),

			    		// Role
		    			itemMenu("Categoria de Usuários", "glyphicon-minus", root+"role/index",
				    		populateSubMenu(
					    		itemMenu("Listar", "glyphicon-list", root+"role/index"),
					    		itemMenu("Add", "glyphicon-plus", root+"role/add")
				    		)
				    	)
		    		)
		    	)
		    );
        %>
        <%= buildSidebarMenu(menu_sidebar) %>
    </div>
</nav>
