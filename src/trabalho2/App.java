/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho2;

/**
 *
 * @author nathan
 */

import trabalho2.model.*;
import trabalho2.view.View;
public final class App {

    private View view;
    private AnalisadorLexico lex;
    private AnalisadorSintatico parser; 
    public static void main(String[] args) {
       new App();
    }
    
    public App() {
        this.view = new View(this);
        lex = new AnalisadorLexico();
        parser = new AnalisadorSintatico(lex);
        analiseSourceCode();
        
        this.view.show(true);
    }
    
    public void analiseSourceCode() {
        view.updateStatus(parser.parse(view.getSourceCode()));
    }
}
