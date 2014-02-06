package ifrn.tads.pds.helper;

import org.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Classe principal com funções auxiliares
 */
public class AppHelper{

	private JSONObject itenslist;
	protected static Logger logger = Logger.getLogger("helper");
	
	public AppHelper(){
		this.itenslist = new JSONObject();
	}

	public JSONObject getItensList(){
		if(this.itenslist == null){
			logger.debug("nenhum item na lista");
			return new JSONObject();
		}else{
			return this.itenslist;
		}
	}

    public void populateList(JSONObject item){
    	this.itenslist.put((this.itenslist.length()) + "", item);
    }

    public JSONObject populateSubList(JSONObject...subList){
    	JSONObject itens_sublist = new JSONObject();
    	for(int i = 0; i < subList.length; i++){
            itens_sublist.put(i+"", subList[i]);
        }
    	return itens_sublist;
    }
}
