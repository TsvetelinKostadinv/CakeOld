/*
 * 06/01/2019 12:18:21
 * TestIfs.java created by Tsvetelin
 */

package com.cake.running.runtime;

import java.util.List;

import com.cake.compilation.tokenizer.tokenizers.stringTokenizer.StringTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.controlFlowStatements.conditionals.IfStatement;
import com.cake.syntax.parsers.ParsersContainer;

import javafx.util.Pair;

/**
 * @author Tsvetelin
 *
 */
public class TestIfs
{

    /**
     * @param args
     */
    public static void main ( String [] args )
    {
        CakeRuntime runtime = new CakeRuntime();
        String code = "if ( 5 > 3 && 8 > -1 ) {" //+ endl
                    + " : local i = 5 " //+ endl
                    + " : local a = 3.0 "
                    + " : if ( root.IF-STATEMENT2#i == 5 ) {"
                    + "        : root.IF-STATEMENT2#i = root.IF-STATEMENT2#i * 5 * 3 "
                    + " : }"
                    + " : if ( root.IF-STATEMENT2#i > 0 ) {"
                    + "        : root.IF-STATEMENT2#i = root.IF-STATEMENT2#i * 2"
                    + " : }"
                    + " : return root.IF-STATEMENT2#i / root.IF-STATEMENT2#a " //+ endl
                    + "}";
        
        System.out.println( "---------------------STARTING INTERPRETATION---------------------" );
        
        List< Token > tokens = new StringTokenizer().tokenize( code );
        System.out.println( "In test || Tokenized sequence: " + tokens );
        
        System.out.println( "----------------------STARTING PARSING----------------------" );
        
        System.out.println( "In test || Can prase(explicit): " + ParsersContainer.IF_STATEMENT_PARSER.canParse( tokens ) );
        
        Pair< String , ? > pair =  ParsersContainer.INSTANCE.getParserFor( tokens ).get( 0 ).parse( null , tokens );
        
        runtime.addDecalredElement( pair.getKey() , (SyntaxElement) pair.getValue() );
        
        IfStatement ifst = (IfStatement) pair.getValue();
        
        System.out.println( "In test || Parsed: " + ifst );
        
        System.out.println( "---------------------STARTING EXECUTION---------------------" );
        
        //runtime.printAllContents();
        
        Result res = ifst.run( runtime );
        
        System.out.println( "In test || Results:" );
        System.out.println( "In test || Returned: " + res.getReturned() );
        System.out.println( "In test || Exit vars: " + res.getExitVariables() );
        
        System.out.println( "--------------------------RESULTS---------------------------" );
        
        runtime.printAllContents();
    }

}
