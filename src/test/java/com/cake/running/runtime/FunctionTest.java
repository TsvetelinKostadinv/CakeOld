/*
 * 08/01/2019 17:15:13
 * WhileTest.java created by Tsvetelin
 */

package com.cake.running.runtime;

import java.util.List;

import com.cake.compilation.tokenizer.tokenizers.stringTokenizer.StringTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.methods.Method;
import com.cake.syntax.parsers.ParsersContainer;

import javafx.util.Pair;

/**
 * @author Tsvetelin
 *
 */
public class FunctionTest
{
    public static void main ( String [] args )
    {
        CakeRuntime runtime = new CakeRuntime();
        
        final String code = " global int main = ( ) -> {"
                        + ": local name = \"Test\""
                        + ": local i = 0.1"
                        + ": if( root.main#i < 1 ) {"
                        + ":      root.main#i = 5"
                        + ": }"
                        + ": while( root.main#i > 0 ) {"
                        + ":      root.main#i = root.main#i - 1"
                        + ": }"
                        + ": return root.main#i"
                     + "}";
        
        System.out.println( "---------------------STARTING INTERPRETATION---------------------" );
        
        List< Token > tokens = new StringTokenizer().tokenize( code );
        System.out.println( "In test || Tokenized sequence: " + tokens );
        
        System.out.println( "----------------------STARTING PARSING----------------------" );
        
        System.out.println( "In test || Can prase(explicit): " + ParsersContainer.METHOD_PARSER.canParse( tokens ) );
        
        Pair< String , ? > pair =  ParsersContainer.INSTANCE.getParserFor( tokens ).get( 0 ).parse( null , tokens );
        
        runtime.addDecalredElement( pair.getKey() , (SyntaxElement) pair.getValue() );
        
        //runtime.printAllContents();
        
        Method m = (Method) pair.getValue();
        
        System.out.println( "In test || Parsed: " + m );
        
        System.out.println( "---------------------STARTING EXECUTION---------------------" );
        
        Result res = m.run( runtime );
        
        System.out.println( "In test || Results:" );
        System.out.println( "In test || Returned: " + res.getReturned() );
        System.out.println( "In test || Exit vars: " + res.getExitVariables() );
        
        System.out.println( "--------------------------RESULTS---------------------------" );
        
        runtime.printAllContents();
        
    }
}
