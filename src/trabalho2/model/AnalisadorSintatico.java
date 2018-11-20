/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho2.model;

import java.util.ArrayList;
import trabalho2.model.glc.*;

/**
 *
 * @author nathan
 */
public class AnalisadorSintatico {
    private AnalisadorLexico lex;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private Grammar GRAMMAR;
    
    public AnalisadorSintatico(AnalisadorLexico lex) {
        this.lex = lex;
        this.tabelaDeSimbolos = TabelaDeSimbolos.getInstance();
        GRAMMAR = createGrammar();
       
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
        Vn.add("matched_if".toUpperCase());
        Vn.add("open_if".toUpperCase());
        Vn.add("loc".toUpperCase());
        Vn.add("locs".toUpperCase());
        Vn.add("bool".toUpperCase());
        Vn.add("join".toUpperCase());
        Vn.add("equality".toUpperCase());
        Vn.add("rel".toUpperCase());
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
//final Pattern reservedpattern = Pattern.compile("if|then|else|while|break|do|true|false|basic");
//    final Pattern operatorsPattern = Pattern.compile("\\{|\\}|\\[|\\]|;|={1,2}|\\(|\\)|\\|{1,2}|&{2}|<|>|\\+|-|\\/|\\*");
        Vt.add("id");
        Vt.add("basic");
        Vt.add("if");
        Vt.add("then");
        Vt.add("else");
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
        
        
        return Vt;
    }
    
    private Grammar createGrammar() {
        ArrayList<String> Vn = genGrammarVns(); 
        ArrayList<String> Vt = genGrammarVts();
        ArrayList<Production> productions = new ArrayList<Production>(); 
        String initialSimbol = Vn.get(0);
       
        
        return Grammar.parseGrammarInput("parserGrammar", "");
    }
    
    public String parse(String sourceCode) {
        String status = "# Compiling \n\n";
        lex.analise(sourceCode);
        status += "Lexical Errors: " + tabelaDeSimbolos
                .tabela.get(AnalisadorLexico.TokenType.ERROR);
        
        while (lex.hasTokens()) {
            doSintaticalAnalisis(lex.getNextToken());
        }
        
        return status;
    }
    
    
    public void doSintaticalAnalisis(String token) {
        System.out.println(token);
    }
}
