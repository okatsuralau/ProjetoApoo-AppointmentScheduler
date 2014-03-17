package ifrn.tads.pds.helper;

import org.json.JSONObject;

/**
 * Classe auxiliar para construção de listas aninhadas de elementos
 */
public class MenuHelper extends AppHelper{

	public JSONObject itemMenu(String label, String icon, String url){
		return new JSONObject().put("label", label).put("icon", icon).put("url", url);
	}

	public JSONObject itemMenu(String label, String icon, String url, JSONObject submenu){
		return new JSONObject().put("label", label).put("icon", icon).put("url", url).put("children", submenu);
	}

    /**
     * Personaliza uma lista aninhada e retorna um menu formatado especificamente para a aplicação
     * @param  links [lista de itens a exibir no menu]
     * @return       [html formatado de itens aninhados]
     */
    public String buildSidebarMenu(JSONObject links){
		String  out = "";
		for (int i = 0; i < links.length(); i++) {

			if( links.has(i + "") ){

				String class_li = "";
				JSONObject link = links.getJSONObject(i+"");

				if( link.has("children") ){
					class_li = " icon-left ";
				}

				if(!class_li.isEmpty()){
					class_li = " class=\""+class_li+"\"";
				}

				out += "<li" + class_li + ">";
					out += "<a href=\"" + link.get("url") + "\">" + link.get("label") + "</a>";

					if(link.has("children")){
						out += "<div class=\"mp-level\"> " +
									"<h2>" + link.get("label") + "</h2>";
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
}
