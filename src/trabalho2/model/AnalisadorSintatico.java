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
    
    public AnalisadorSintatico(AnalisadorLexico lex) {
        this.lex = lex;
    }
    
    public void parse(String sourceCode) {
        lex.analise(sourceCode);
    }
}
