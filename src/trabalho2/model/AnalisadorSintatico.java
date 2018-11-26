/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho2.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import trabalho2.model.glc.*;

/**
 *
 * @author nathan
 */
public class AnalisadorSintatico {
    private AnalisadorLexico lex;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private Grammar GRAMMAR;
    private TabelaDeAnalise tabelaDeAnalise;
    private ArrayList<String> pilha;
    
    public AnalisadorSintatico(AnalisadorLexico lex) {
        this.lex = lex;
        this.tabelaDeSimbolos = TabelaDeSimbolos.getInstance();
        pilha = new ArrayList<>();
        GRAMMAR = createGrammar();
        HashMap<String, Set<String>> first = GRAMMAR.getFirst();
        HashMap<String, Set<String>> follow = GRAMMAR.getFollow();
        System.out.println(GRAMMAR.toString());
    }
    
    private ArrayList<String> genGrammarVns() {
        ArrayList<String> Vn = new ArrayList<>();
        
        Vn.add("program".toUpperCase());
        Vn.add("block".toUpperCase());
        Vn.add("decls".toUpperCase());
        Vn.add("decl".toUpperCase());
        Vn.add("type".toUpperCase());
        Vn.add("types".toUpperCase());
        Vn.add("stmts".toUpperCase());
        Vn.add("stmt".toUpperCase());
        //Vn.add("matched_if".toUpperCase());
        //Vn.add("open_if".toUpperCase());
        Vn.add("loc".toUpperCase());
        Vn.add("locs".toUpperCase());
        Vn.add("bool".toUpperCase());
        Vn.add("bool1".toUpperCase());
        Vn.add("join".toUpperCase());
        Vn.add("join1".toUpperCase());
        Vn.add("equality".toUpperCase());
        Vn.add("equality1".toUpperCase());
        Vn.add("rel".toUpperCase());
        Vn.add("rel1".toUpperCase());
        Vn.add("rel2".toUpperCase());
        Vn.add("expr".toUpperCase());
        Vn.add("exprs".toUpperCase());
        Vn.add("term".toUpperCase());
        Vn.add("terms".toUpperCase());
        Vn.add("unary".toUpperCase());
        Vn.add("factor".toUpperCase());
               
        return Vn;
    }
    
    public ArrayList<String> genGrammarVts() {
        ArrayList<String> Vt = new ArrayList<>();
        Vt.add("id");
        Vt.add("basic");
        Vt.add("if");
        Vt.add("then");
        Vt.add("else");
        Vt.add("do");
        Vt.add("while");
        Vt.add("break");
        Vt.add("true");
        Vt.add("false");
        Vt.add("num");
        Vt.add("real");
        Vt.add(";");
        Vt.add("[");
        Vt.add("]");
        Vt.add("{");
        Vt.add("}");
        Vt.add("(");
        Vt.add(")");
        Vt.add("[");
        Vt.add("]");     
        Vt.add("=");
        Vt.add("==");
        Vt.add("!=");
        Vt.add("<");
        Vt.add(">");
        Vt.add("<=");
        Vt.add(">=");
        Vt.add("&&");
        Vt.add("-");
        Vt.add("+");
        Vt.add("*");
        Vt.add("/");
        Vt.add("!");
        Vt.add("&");
        Vt.add("||");
        
        
        return Vt;
    }
    
    public ArrayList<Production> genGrammarProductions() {
        ArrayList<Production> prods = new ArrayList<>();
        prods.add(new Production("PROGRAM", new ArrayList<>(Arrays.asList("BLOCK"))));
        
        prods.add(new Production("BLOCK", new ArrayList<>(Arrays.asList("{","DECLS","STMTS", "}"))));
        
        prods.add(new Production("DECLS", new ArrayList<>(Arrays.asList("DECL", "DECLS"))));
        prods.add(new Production("DECLS", new ArrayList<>(Arrays.asList("&"))));
        
        prods.add(new Production("DECL", new ArrayList<>(Arrays.asList("TYPE", "id"))));
        
        prods.add(new Production("TYPE", new ArrayList<>(Arrays.asList("basic", "TYPES"))));
        
        prods.add(new Production("TYPES", new ArrayList<>(Arrays.asList("[", "num", "]", "TYPES"))));
        prods.add(new Production("TYPES", new ArrayList<>(Arrays.asList("&"))));
        
        prods.add(new Production("STMTS", new ArrayList<>(Arrays.asList("STMT", "STMTS"))));
        prods.add(new Production("STMTS", new ArrayList<>(Arrays.asList("&"))));
        
        prods.add(new Production("STMT", new ArrayList<>(Arrays.asList("LOC", "=", "BOOL", ";"))));
        //prods.add(new Production("STMT", new ArrayList<>(Arrays.asList("MATCHED_IF"))));
        //prods.add(new Production("STMT", new ArrayList<>(Arrays.asList("OPEN_IF"))));
        prods.add(new Production("STMT", new ArrayList<>(Arrays.asList("while", "(", "BOOL", ")","STMT"))));
        prods.add(new Production("STMT", new ArrayList<>(Arrays.asList("do", "STMT", "while", "(", "BOOL", ")", ";"))));
        prods.add(new Production("STMT", new ArrayList<>(Arrays.asList("break", ";"))));
        prods.add(new Production("STMT", new ArrayList<>(Arrays.asList("BLOCK"))));
        
        //prods.add(new Production("MATCHED_IF", new ArrayList<>(Arrays.asList("if", "(", "BOOL",")", "then", "MATCHED_IF", "else", "MATCHED_IF"))));
        
        //prods.add(new Production("OPEN_IF", new ArrayList<>(Arrays.asList("if", "(", "BOOL",")", "then", "STMT"))));
        //prods.add(new Production("OPEN_IF", new ArrayList<>(Arrays.asList("if", "(", "BOOL",")", "then", "MATCHED_IF", "else", "OPEN_IF"))));
        
        prods.add(new Production("LOC", new ArrayList<>(Arrays.asList("id", "LOCS"))));
        
        prods.add(new Production("LOCS", new ArrayList<>(Arrays.asList("[", "BOOL", "]", "LOCS"))));
        prods.add(new Production("LOCS", new ArrayList<>(Arrays.asList("&"))));
        
        prods.add(new Production("BOOL", new ArrayList<>(Arrays.asList("JOIN","BOOL1"))));
        
        prods.add(new Production("BOOL1", new ArrayList<>(Arrays.asList("&"))));
        prods.add(new Production("BOOL1", new ArrayList<>(Arrays.asList( "||", "BOOL"))));
        
        prods.add(new Production("JOIN", new ArrayList<>(Arrays.asList("EQUALITY","JOIN1"))));
        
        prods.add(new Production("JOIN1", new ArrayList<>(Arrays.asList("&"))));
        prods.add(new Production("JOIN1", new ArrayList<>(Arrays.asList( "&&", "JOIN"))));
        
        prods.add(new Production("EQUALITY", new ArrayList<>(Arrays.asList("REL","EQUALITY1"))));
        
        prods.add(new Production("EQUALITY1", new ArrayList<>(Arrays.asList("&"))));
        prods.add(new Production("EQUALITY1", new ArrayList<>(Arrays.asList( "==", "EQUALITY"))));
        prods.add(new Production("EQUALITY1", new ArrayList<>(Arrays.asList( "!=", "EQUALITY"))));
        
        prods.add(new Production("REL", new ArrayList<>(Arrays.asList("EXPR", "REL1"))));
        
        prods.add(new Production("REL1", new ArrayList<>(Arrays.asList( "<", "REL2"))));
        prods.add(new Production("REL1", new ArrayList<>(Arrays.asList(">", "REL2"))));
        prods.add(new Production("REL1", new ArrayList<>(Arrays.asList("&"))));
        
        prods.add(new Production("REL2", new ArrayList<>(Arrays.asList("EXPR"))));
        prods.add(new Production("REL2", new ArrayList<>(Arrays.asList("=","EXPR"))));
        
        prods.add(new Production("EXPR", new ArrayList<>(Arrays.asList("TERM" ,"EXPRS"))));
        
        prods.add(new Production("EXPRS", new ArrayList<>(Arrays.asList("+", "TERM" ,"EXPRS"))));
        prods.add(new Production("EXPRS", new ArrayList<>(Arrays.asList("-", "TERM" ,"EXPRS"))));
        prods.add(new Production("EXPRS", new ArrayList<>(Arrays.asList("&"))));
        
        prods.add(new Production("TERM", new ArrayList<>(Arrays.asList("UNARY","EXPRS"))));
        
        prods.add(new Production("TERMS", new ArrayList<>(Arrays.asList("*","UNARY","TERMS"))));
        prods.add(new Production("TERMS", new ArrayList<>(Arrays.asList("/","UNARY","TERMS"))));
        prods.add(new Production("TERMS", new ArrayList<>(Arrays.asList("&"))));
        
        prods.add(new Production("UNARY", new ArrayList<>(Arrays.asList("!", "UNARY"))));
        prods.add(new Production("UNARY", new ArrayList<>(Arrays.asList("-", "UNARY"))));
        prods.add(new Production("UNARY", new ArrayList<>(Arrays.asList("FACTOR"))));
        
        prods.add(new Production("FACTOR", new ArrayList<>(Arrays.asList("(", "BOOL", ")"))));
        prods.add(new Production("FACTOR", new ArrayList<>(Arrays.asList("LOC"))));
        prods.add(new Production("FACTOR", new ArrayList<>(Arrays.asList("num"))));
        prods.add(new Production("FACTOR", new ArrayList<>(Arrays.asList("real"))));
        prods.add(new Production("FACTOR", new ArrayList<>(Arrays.asList("true"))));
        prods.add(new Production("FACTOR", new ArrayList<>(Arrays.asList("false"))));
        
        return prods;
    }
    private Grammar createGrammar() {
        ArrayList<String> Vn = genGrammarVns(); 
        ArrayList<String> Vt = genGrammarVts();
        ArrayList<Production> productions = genGrammarProductions(); 
        String initialSimbol = Vn.get(0);
       
        
        return new Grammar(Vn, Vt, productions, initialSimbol);
    }
    
    public String parse(String sourceCode) {
        String status = "# Compiling \n\n";
        lex.analise(sourceCode);
        status += "Lexical Errors: " + tabelaDeSimbolos
                .tabela.get(AnalisadorLexico.TokenType.ERROR) + "\n";
        genTabelaDeAnalise();
        pilha.add(GRAMMAR.getInitialSymbol());
        String parserErros = "\nParser Erros: ";
        while (!pilha.isEmpty() || !lex.hasTokens()) {
            parserErros += doSintaticalAnalisis(lex.getNextToken());
        }
        
        status += parserErros;
        
        return status;
    }
    
    
    public String doSintaticalAnalisis(String token) {
    	String s;
    	boolean loop = true;
    	String errors = "";
    	while(loop) {
    		String simboloDePilha = pilha.get(pilha.size()-1);
    		Production p = null;
        	try {
        		 p = tabelaDeAnalise.getM(token, simboloDePilha);
        	} catch (Exception e) {}
    		
        	
	    	if (simboloDePilha.equals(token)) {
	            pilha.remove(pilha.size()-1);
	            loop = false;
	        } else if (GRAMMAR.getVt().contains(simboloDePilha)) {
	            // erro lexico
	        	break;
	        } 
	       else if (p == null) {
	    	 return errors.concat("Error in: " + token);
	      } else {
	//        Anda com a Transição 
	//        Coloca as produções na pilha
	    	  
	    	  pilha.remove(pilha.size()-1);
	    	  
	    	  ArrayList<String> simbols = p.getSentence();
	    	  
	    	  for(int i =simbols.size() -1; i > -1 ; i--) {
	    		  s = simbols.get(i);
	    		  pilha.add(s);
	    	  }  
	      	}
    	}
    	return errors;
    }
    
    private void genTabelaDeAnalise() {
    	GRAMMAR.getFirst();
    	GRAMMAR.getFollow();

    	tabelaDeAnalise = new TabelaDeAnalise(GRAMMAR);
    	String [] aux;
    	String [] aux2;
    	
    	for (Production p : GRAMMAR.getProductions()) {
    		
    		Object[] objectArray = GRAMMAR.getSentenceFirst(p.getSentence()).toArray();
    		aux = Arrays.asList(objectArray).toArray(new String[objectArray.length]);
                Object[] objectArray2 =GRAMMAR.getFollow(p.getNT()).toArray();
    		aux2 =  Arrays.asList(objectArray2).toArray(new String[objectArray2.length]);
    		
    		for(String s : aux) {
    			if(s != "&" ) {
    				tabelaDeAnalise.addM(p.getNT(), s, p);
    			}
    			else {
    				for(String s1 : aux2) {
    					tabelaDeAnalise.addM(p.getNT(), s1, p);
    				}
    			}
    		}
    		
        }
    }
}
