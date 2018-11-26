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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nathan
 */
public class AnalisadorLexico {

    private final Automaton AUTOMATO;
    final Pattern reservedpattern = Pattern.compile("if|then|else|while|break|do|true|false|basic");
    final Pattern operatorsPattern = Pattern.compile("\\{|\\}|\\[|\\]|;|={1,2}|\\(|\\)|\\|{1,2}|&{2}|<|>|\\+|-|\\/|\\*");
    final Pattern numbersPattern = Pattern.compile("[0-9]+");
    final Pattern realPattern = Pattern.compile("-?[0-9]+.[0-9]+");
    private ArrayList<String> tokenList;
    private final TabelaDeSimbolos tabelaDeSimbolos;
    
    public AnalisadorLexico() {
        AUTOMATO = createAnalisisAutomaton();
        this.tabelaDeSimbolos = TabelaDeSimbolos.getInstance();
    }

    public enum TokenType {
        ID, RESERVED, OPERATOR, ERROR, NUMBER, REAL;

        public static TokenType get(String typeName) {
            for (TokenType categoria : TokenType.values()) {
                if (typeName.equals(categoria.toString())) {
                    return categoria;
                }
            }

            return ERROR;
        }
    }
    
    private TokenType checkTokenType(String token) {
        Matcher reservedMatcher = reservedpattern.matcher(token);
        Matcher operatorsMatcher = operatorsPattern.matcher(token);
        Matcher numbersMatcher = numbersPattern.matcher(token);
        Matcher realMatcher = realPattern.matcher(token);
        
        if (reservedMatcher.find()) { 
            tokenList.add(token);
            
            return TokenType.RESERVED;
        } else if (operatorsMatcher.find()) {
            tokenList.add(token);
            
            return TokenType.OPERATOR;
        } else if (realMatcher.find()){
            tokenList.add("real");
            
            return TokenType.REAL; 
        } else if (numbersMatcher.find()) {
            tokenList.add("num");
            
            return TokenType.NUMBER;
        } 
        
        tokenList.add("id");
        
        return TokenType.ID;
    }

    private Automaton createAnalisisAutomaton() {
//        PARA REGEX if|=|==|(a|b|c|d)+
// https://cyberzhg.github.io/toolbox/min_dfa?regex=aWZ8PXw9PXwoYWJjZCkr
//a ordem quem eu os states são inseridos é relevante.
        ArrayList<State> states = new ArrayList<>();
        ArrayList<Character> alphabet = new ArrayList<>();
        Transitions transitions = new Transitions();
        State initialState;
        ArrayList<State> finalState = new ArrayList<>();
        String id = "Automato";
        
        State[] reservada_id = new State[23];
        for (int i = 0; i < reservada_id.length; i++) {
            reservada_id[i] = new State("Q" + (i + 1));
            states.add(reservada_id[i]);
        }
        initialState = reservada_id[0];
        //final reservada
        finalState.add(reservada_id[7]);
        //final id
        finalState.add(reservada_id[8]);
        /*
        reservadas e id Q1-Q23
        */
        State real = new State("Q24");
        states.add(real);
        State num = new State("Q25");
        states.add(num);
        states.add(new State("Q26"));
        //final num
        finalState.add(num);
        //final real
        finalState.add(real);
        /*
        numeros/reais Q24-26
        */
        states.add(new State("Q27"));
        State and = new State("Q28");
        states.add(and);
        //final and
        finalState.add(and);
        /*
        && Q27-Q28
        */
        states.add(new State("Q29"));
        State or = new State("Q30");
        states.add(or);
        //final or
        finalState.add(or);
        /*
        || Q29-Q30
        */
        State op_block = new State("Q31");
        states.add(op_block);
        State cl_block = new State("Q32");
        states.add(cl_block);
        //final op-cl-block
        finalState.add(op_block);
        finalState.add(cl_block);
        /*
        {,} Q31-Q32
        */
        State op_bracket = new State("Q33");
        states.add(op_bracket);
        State cl_bracket  = new State("Q34");
        states.add(cl_bracket);
        //final op-cl-bracket
        finalState.add(op_bracket);
        finalState.add(cl_bracket);
        /*
        [,] Q33-Q34
        */
        State op_parentheses = new State("Q35");
        states.add(op_parentheses);
        State cl_parentheses  = new State("Q36");
        states.add(cl_parentheses);
        //final op-cl-parentheses
        finalState.add(op_parentheses);
        finalState.add(cl_parentheses);
        /*
        (,) Q35-Q36
        */
        State plus = new State("Q37");
        states.add(plus);
        //final plus
        finalState.add(plus);
        /*
        + Q37
        */
        State minus = new State("Q38");
        states.add(minus);
        //final minus
        finalState.add(minus);
        /*
        - Q38
        */
        State mult = new State("Q39");
        states.add(mult);
        //final plus
        finalState.add(mult);
        /*
        * Q39
        */
        State div = new State("Q40");
        states.add(div);
        //final plus
        finalState.add(div);
        /*
        / Q40
        */
        State semicolon = new State("Q41");
        states.add(semicolon);
        //final plus
        finalState.add(semicolon);
        /*
        ; Q41
        */
        State unitary = new State("Q42");
        states.add(unitary);
        State binary = new State("Q43");
        states.add(binary);
        //final unitary
        finalState.add(unitary);
        //final binary
        finalState.add(binary);
        /*
        ops Q42-Q43
        */
        alphabet = genAlphabet();
        transitions= genTransitions(states);

        return new Automaton(states, alphabet, transitions, initialState, finalState, id);
    }

    private ArrayList<Character> genAlphabet() {
        ArrayList<Character> alphabet = new ArrayList<>();
        // reservados e id
        alphabet.add('a');
        alphabet.add('b');
        alphabet.add('c');
        alphabet.add('d');
        alphabet.add('e');
        alphabet.add('f');
        alphabet.add('h');
        alphabet.add('i');
        alphabet.add('k');
        alphabet.add('l');
        alphabet.add('n');
        alphabet.add('o');
        alphabet.add('r');
        alphabet.add('s');
        alphabet.add('t');
        alphabet.add('u');
        alphabet.add('w');
        //
        //real/num
        alphabet.add('0');
        alphabet.add('1');
        alphabet.add('2');
        alphabet.add('3');
        alphabet.add('4');
        alphabet.add('5');
        alphabet.add('6');
        alphabet.add('7');
        alphabet.add('8');
        alphabet.add('9');
        alphabet.add('.');
        //
        //and
        alphabet.add('&');
        //
        //or
        alphabet.add('|');
        //
        //op-cl-block
        alphabet.add('{');
        alphabet.add('}');
        //
        //op-cl-bracket
        alphabet.add('[');
        alphabet.add(']');
        //
        //op-cl-parentheses
        alphabet.add('(');
        alphabet.add(')');
        //
        //plus
        alphabet.add('+');
        //
        //minus
        alphabet.add('-');
        //
        //mult
        alphabet.add('*');
        //
        //div
        alphabet.add('/');
        //
        //semicolon
        alphabet.add(';');
        //
        //ops
        alphabet.add('=');
        alphabet.add('!');
        alphabet.add('>');
        alphabet.add('<');
        //
        return alphabet;
    }

    public Transitions genTransitions(ArrayList<State> states) {
        Transitions transitions = new Transitions();
        //id
        transitions.addTransition(states.get(0), 'a', states.get(8));
        transitions.addTransition(states.get(0), 'o', states.get(8));
        transitions.addTransition(states.get(0), 'u', states.get(8));
        //end id
        //reservadas
        transitions.addTransition(states.get(0), 'b', states.get(9));
        transitions.addTransition(states.get(0), 'd', states.get(10));
        transitions.addTransition(states.get(0), 'e', states.get(11));
        transitions.addTransition(states.get(0), 'f', states.get(12));
        transitions.addTransition(states.get(0), 'i', states.get(13));
        transitions.addTransition(states.get(0), 't', states.get(14));
        transitions.addTransition(states.get(0), 'w', states.get(15));
        
        transitions.addTransition(states.get(1), 's', states.get(3));
        
        transitions.addTransition(states.get(2), 'n', states.get(7));
        
        transitions.addTransition(states.get(3), 'e', states.get(7));
        
        transitions.addTransition(states.get(4), 'l', states.get(3));
        
        transitions.addTransition(states.get(5), 'c', states.get(7));
        
        transitions.addTransition(states.get(6), 'k', states.get(7));
        
        transitions.addTransition(states.get(8), 'a', states.get(8));
        transitions.addTransition(states.get(8), 'o', states.get(8));
        transitions.addTransition(states.get(8), 'u', states.get(8));
        
        transitions.addTransition(states.get(9), 'a', states.get(16));
        transitions.addTransition(states.get(9), 'r', states.get(17));
        
        transitions.addTransition(states.get(10), 'o', states.get(7));
        
        transitions.addTransition(states.get(11), 'l', states.get(1));
        
        transitions.addTransition(states.get(12), 'a', states.get(11));
        
        transitions.addTransition(states.get(13), 'f', states.get(7));
        
        transitions.addTransition(states.get(14), 'h', states.get(18));
        transitions.addTransition(states.get(14), 'r', states.get(19));
        
        transitions.addTransition(states.get(15), 'h', states.get(20));
        
        transitions.addTransition(states.get(16), 's', states.get(21));
        
        transitions.addTransition(states.get(17), 'e', states.get(22));
        
        transitions.addTransition(states.get(18), 'e', states.get(2));
        
        transitions.addTransition(states.get(19), 'u', states.get(3));
        transitions.addTransition(states.get(20), 'i', states.get(4));
        
        transitions.addTransition(states.get(21), 'i', states.get(5));
        
        transitions.addTransition(states.get(22), 'a', states.get(6));
        //end reservados
        //numeros/ reais
        transitions.addTransition(states.get(0), '0', states.get(24));
        transitions.addTransition(states.get(0), '1', states.get(24));
        transitions.addTransition(states.get(0), '2', states.get(24));
        transitions.addTransition(states.get(0), '3', states.get(24));
        transitions.addTransition(states.get(0), '4', states.get(24));
        transitions.addTransition(states.get(0), '5', states.get(24));
        transitions.addTransition(states.get(0), '6', states.get(24));
        transitions.addTransition(states.get(0), '7', states.get(24));
        transitions.addTransition(states.get(0), '8', states.get(24));
        transitions.addTransition(states.get(0), '9', states.get(24));
        
        transitions.addTransition(states.get(24), '0', states.get(24));
        transitions.addTransition(states.get(24), '1', states.get(24));
        transitions.addTransition(states.get(24), '2', states.get(24));
        transitions.addTransition(states.get(24), '3', states.get(24));
        transitions.addTransition(states.get(24), '4', states.get(24));
        transitions.addTransition(states.get(24), '5', states.get(24));
        transitions.addTransition(states.get(24), '6', states.get(24));
        transitions.addTransition(states.get(24), '7', states.get(24));
        transitions.addTransition(states.get(24), '8', states.get(24));
        transitions.addTransition(states.get(24), '9', states.get(24));
        transitions.addTransition(states.get(24), '.', states.get(25));
        
        transitions.addTransition(states.get(25), '0', states.get(23));
        transitions.addTransition(states.get(25), '1', states.get(23));
        transitions.addTransition(states.get(25), '2', states.get(23));
        transitions.addTransition(states.get(25), '3', states.get(23));
        transitions.addTransition(states.get(25), '4', states.get(23));
        transitions.addTransition(states.get(25), '5', states.get(23));
        transitions.addTransition(states.get(25), '6', states.get(23));
        transitions.addTransition(states.get(25), '7', states.get(23));
        transitions.addTransition(states.get(25), '8', states.get(23));
        transitions.addTransition(states.get(25), '9', states.get(23));
        
        transitions.addTransition(states.get(23), '0', states.get(23));
        transitions.addTransition(states.get(23), '1', states.get(23));
        transitions.addTransition(states.get(23), '2', states.get(23));
        transitions.addTransition(states.get(23), '3', states.get(23));
        transitions.addTransition(states.get(23), '4', states.get(23));
        transitions.addTransition(states.get(23), '5', states.get(23));
        transitions.addTransition(states.get(23), '6', states.get(23));
        transitions.addTransition(states.get(23), '7', states.get(23));
        transitions.addTransition(states.get(23), '8', states.get(23));
        transitions.addTransition(states.get(23), '9', states.get(23));
        //
        //and
        transitions.addTransition(states.get(0), '&', states.get(26));
        transitions.addTransition(states.get(26), '&', states.get(27));
        //
        
        //or
        transitions.addTransition(states.get(0), '|', states.get(28));
        transitions.addTransition(states.get(28), '|', states.get(29));
        //
        
        //op-cl-block
        transitions.addTransition(states.get(0), '{', states.get(30));
        transitions.addTransition(states.get(0), '}', states.get(31));
        //
        //op-cl-bracket
        transitions.addTransition(states.get(0), '[', states.get(32));
        transitions.addTransition(states.get(0), ']', states.get(33));
        //
        //op-cl-parentheses
        transitions.addTransition(states.get(0), '(', states.get(34));
        transitions.addTransition(states.get(0), ')', states.get(35));
        //
        //plus
        transitions.addTransition(states.get(0), '+', states.get(36));
        //
        //minus
        transitions.addTransition(states.get(0), '-', states.get(37));
        //
        //mult
        transitions.addTransition(states.get(0), '*', states.get(38));
        //
        //div
        transitions.addTransition(states.get(0), '/', states.get(39));
        //
        //semicolon
        transitions.addTransition(states.get(0), ';', states.get(40));
        //
        //ops
        transitions.addTransition(states.get(0), '!', states.get(41));
        transitions.addTransition(states.get(0), '=', states.get(41));
        transitions.addTransition(states.get(0), '<', states.get(41));
        transitions.addTransition(states.get(0), '>', states.get(41));
        
        transitions.addTransition(states.get(41), '=', states.get(42));
        //
        return transitions;
    }
    
    public String[] cleanWord (String word) {
        word = word.replace("\n", " ");
        return word.trim().split(" ");
    }

    public void analise(String sourceCode) {
        tabelaDeSimbolos.clean();
        String[] conjuntoDePalavras = cleanWord(sourceCode);
        tokenList = new ArrayList<>();
       
        for (String palavra : conjuntoDePalavras) {
            tabelaDeSimbolos.addToken(doLexAnalisis(AUTOMATO.getInitialState(), palavra, palavra), palavra);
        }
        
        System.out.println(tokenList.toString());
        System.out.println(tabelaDeSimbolos.getTable());
    }

    public TokenType doLexAnalisis(State actual, String word, String initialWord) {
        if (AUTOMATO.getFinalStates().contains(actual) && "".equals(word)) {            
            return checkTokenType(initialWord);
        }
        try {
            State estadoDestino = AUTOMATO.getNextStates(actual, word.charAt(0)).get(0);

            if (estadoDestino != null) {
                return doLexAnalisis(estadoDestino, word.substring(1), initialWord);
            }
        } catch (Exception e) {
        }
        return TokenType.ERROR;
    }

    public String getNextToken() {
    	if (hasTokens()) {
    		return tokenList.remove(0);
    	}
    	
    	return null;
    }
    
    public boolean hasTokens() {
        return !tokenList.isEmpty();
    }
}
