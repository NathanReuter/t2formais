package trabalho2.model;

import java.util.HashMap;
import java.util.HashSet;
import trabalho2.model.glc.*;

public class TabelaDeAnalise {
    public HashMap<String, HashMap<String,Production>> tabela;
    
    public TabelaDeAnalise(Grammar g) {
    	tabela = new HashMap<String, HashMap<String,Production>>() {
            {
                for (String nt : g.getVn()) {
                    put(nt, new HashMap<String,Production>());
                }
            }
        };
    }
   
    public HashMap<String, HashMap<String,Production>> getTable () {
        return tabela;
    }
    
    public void addM(String nt, String t, Production p) {
        tabela.get(nt).put(t, p);
    }
    
    public Production getM(String x, String a ){
    	return tabela.get(x).get(a);
    }
}
