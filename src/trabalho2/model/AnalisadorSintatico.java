/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho2.model;

/**
 *
 * @author nathan
 */
public class AnalisadorSintatico {
    private AnalisadorLexico lex;
    private TabelaDeSimbolos tabelaDeSimbolos;
    
    public AnalisadorSintatico(AnalisadorLexico lex) {
        this.lex = lex;
        this.tabelaDeSimbolos = TabelaDeSimbolos.getInstance();
    }
    
    public String parse(String sourceCode) {
        String status = "# Compiling \n\n";
        lex.analise(sourceCode);
        status += "Lexical Errors: " + tabelaDeSimbolos
                .tabela.get(AnalisadorLexico.TokenType.ERROR);
        
        
        return status;
    }
}
