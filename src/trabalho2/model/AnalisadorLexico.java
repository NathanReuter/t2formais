/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho2.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import trabalho2.model.automato.Automaton;
import trabalho2.model.automato.State;
import trabalho2.model.automato.Transitions;

/**
 *
 * @author nathan
 */
public class AnalisadorLexico {
    private Automaton AUTOMATO;
    public AnalisadorLexico() {
        AUTOMATO = createAnalisisAutomaton();
    }

    public enum TokemType {
        ID, RESERVED, OPERATOR, ERROR;

        public static TokemType get(String typeName) {
            for (TokemType categoria : TokemType.values()) {
                if (typeName.equals(categoria.toString())) {
                    return categoria;
                }
            }

            return ERROR;
        }
    }
    private Automaton createAnalisisAutomaton() {
        ArrayList<State> states = new ArrayList<State>();
        ArrayList<Character> alphabet = new ArrayList<>();
        Transitions transitions = new Transitions();
        State initialState;
        ArrayList<State> finalState = new ArrayList<>();
        String id = "Automato";
        
        State A = new State("A");
        State B = new State("B");
        State C = new State("C");
        states.add(A);
        states.add(B);
        states.add(C);
        alphabet.add('i');
        alphabet.add('f');
        initialState = A;
        finalState.add(C);
        transitions.addTransition(A, 'i', B);
        transitions.addTransition(B, 'f', C);
        return new 
        Automaton(states, alphabet, transitions, initialState, finalState, id);
    }
    

    public void analise(String sourceCode) {
        String[] conjuntoDePalavras = sourceCode.trim().split(" ");
        HashMap<TokemType, HashSet<String>> tabelaDeTokens = new HashMap<TokemType, HashSet<String>>() {
            {
                for (TokemType categoria : TokemType.values()) {
                    put(categoria, new HashSet<String>());
                }
            }
        };
        for (String palavra : conjuntoDePalavras) {
            System.err.println(palavra);
            tabelaDeTokens.get(doLexAnalisis(AUTOMATO.getInitialState(), palavra)).add(palavra);
        }
        System.out.println(tabelaDeTokens);
    }
    
    public TokemType doLexAnalisis (State actual, String word) {
        if (AUTOMATO.getFinalStates().contains(actual)){
            return TokemType.RESERVED;
        }
        
        State estadoDestino = AUTOMATO.getNextStates(actual, word.charAt(0)).get(0);
        System.out.println(estadoDestino);
        if (estadoDestino != null) {
            return doLexAnalisis(estadoDestino, word.substring(1));
        }  
        
        return TokemType.ERROR;
    }

    public String getNextToken() {
        return "";
    }
}
