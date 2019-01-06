/*
 * 06/01/2019 12:18:21
 * TestIfs.java created by Tsvetelin
 */

package com.cake.running.runtime;

import java.util.List;

import com.cake.compilation.tokenizer.tokenizers.StringTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.syntax.baseElements.Result;
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
                + " :local int i = 5 " //+ endl
                + " :local int a = 3 " //+ endl
                + " :i = 3*5*2 " //+ endl
                + " :return i / 2 " //+ endl
                + "}";
        
        System.out.println( "---------------------STARTING INTERPRETATION---------------------" );
        
        List< Token > tokens = new StringTokenizer().tokenize( code );
        System.out.println( "In test || Tokenized sequence: " + tokens );
        
        System.out.println( "----------------------STARTING PARSING----------------------" );
        
        System.out.println( "In test || Can prase: " + ParsersContainer.IF_STATEMENT_PARSER.canParse( tokens ) );
        
        Pair< String , IfStatement > pair =  ParsersContainer.IF_STATEMENT_PARSER.parse( null , tokens );
        
        runtime.addDecalredElement( pair.getKey() , pair.getValue() );
        
        IfStatement ifst = pair.getValue();
        
        System.out.println( "In test || Parsed: " + ifst );
        
        System.out.println( "---------------------STARTING EXECUTION---------------------" );
        
        Result res = ifst.run( runtime );
        
        System.out.println( "In test || Results:" );
        System.out.println( "In test || Returned: " + res.getReturned() );
        System.out.println( "In test || Exit vars: " + res.getExitVariables() );
        
        System.out.println( "--------------------------RESULTS---------------------------" );
        
        runtime.printAllContents();
    }

}
