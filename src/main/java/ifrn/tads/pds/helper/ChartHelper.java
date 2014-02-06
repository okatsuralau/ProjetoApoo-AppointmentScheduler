package ifrn.tads.pds.helper;

import org.json.JSONObject;

/**
 * Classe auxiliar para construção de listas aninhadas de elementos
 */
public class ChartHelper extends AppHelper{

	private int total = 0;

	public JSONObject itemChart(String label, int count, String url){
		this.total += count;

		return new JSONObject().put("label", label).put("count", count).put("url", url);
	}

    /**
     * Personaliza uma lista e retorna um menu formatado especificamente para a aplicação (dashboard)
     * @param  links [lista de itens a exibir no menu]
     * @return       [html formatado de itens]
     */
    public String buildChart(JSONObject links){
		String  out = "";
		if(links.length() > 0){
			for (int i = 0; i < links.length(); i++){
				if( links.has(i + "") ){
	
					JSONObject link = links.getJSONObject(i+"");
					String chart_item = "";
					
					int count = (Integer) link.get("count");
					int countPct = this.total > 0 ? (count * 100) / this.total : 0;
					
					chart_item = "<span class=\"pull-right col-md-4\">"
									+ "<strong class=\"list-group-item-heading pdl5 icon-lg icon-text\"> " + link.get("label") + "</strong>"
								+ "</span>"
								+ "<span class=\"progress no-mb show\">"
									+ "<span aria-valuenow=\"" + countPct + "\" aria-valuemax=\"100\" aria-valuemin=\"0\" role=\"progressbar\" class=\"progress-bar progress-bar-success\" style=\"width:"+  countPct +"%;\">"
						        		+ "<small class=\"badge\">"
						        			+ count
						        		+ "</small>"
					        		+ "</span>"
					        	+ "</span>";
	
					out += link.get("url").equals("") ? "<div class=\"list-group-item chart\"" + chart_item + "</div>" : "<a class=\"list-group-item chart\" href=\"" + link.get("url") + "\">" + chart_item + "</a>";
				}
			}
		}
		
		if(!out.isEmpty())
			out = "<div class=\"list-group chart-progress\">" + out + "</div>";
		
		return out;
	}
}
