/*
 * 05/01/2019 15:21:05
 * TestOfInvocation.java created by Tsvetelin
 */

package com.cake.running.runtime;

import java.util.List;

import com.cake.compilation.tokenizer.tokenizers.stringTokenizer.StringTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.methods.Method;
import com.cake.syntax.methods.parser.MethodParser;

/**
 * @author Tsvetelin
 *
 */
public class TestOfSegregation
{
    public static final String endl = System.getProperty( "line-separator" );
    
    public static void main ( String [] args )
    {
        CakeRuntime runtime = new CakeRuntime();
        String code = "global void main = () -> { " //+ endl
                                + " :local int i = 5 " //+ endl
                                + " :i = 3*5*2 " //+ endl
                                + " :return i / 2 " //+ endl
                                + "}";
        
        System.out.println( "----------------PARSING STAGE--------------" );
        
        List< Token > methodTokens = new StringTokenizer().tokenize( code );
        
        String address = new MethodParser().parse( null , methodTokens ).getKey();
        Method m = new MethodParser().parse( null , methodTokens ).getValue();
        
        runtime.addDecalredElement( address , m );
        
        System.out.println( "----------------METHOD INFO--------------" );
        
        System.out.println( "Promise of method: " + m.getPromise() );
        System.out.println( "Body of method: " );
        System.out.println( m );
        
        System.out.println( "-----------------RESULTS-----------------" );
        System.out.println( "Running method" );
        
        Result res = m.run( runtime );
        
        System.out.println( res );
        
        System.out.println( "-----------------RUNTIME------------------" );
        
        runtime.forEach( (x,y) -> System.out.printf( "Address: %s and value: %s", x , y.toString() ) );
        
    }
}
