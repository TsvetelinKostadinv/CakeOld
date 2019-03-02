/*
 * 22/02/2019 19:24:24
 * Test.java created by Tsvetelin
 */

package com.cake.interpreter;


import java.util.List;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.running.runtime.Runner;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.methods.Method;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.ParsersContainer;
import com.cake.interpreter.syntax.variables.Type;
import com.cake.interpreter.syntax.variables.values.Value;
import com.cake.tokenization.tokenizer.tokenizers.stringTokenizer.StringTokenizer;
import com.cake.tokenization.tokens.Token;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class Test
{

    //@formatter:off
    public static final String source = "global int factorial = (INTEGER n) -> {" + 
            "  local int res = 1" + 
            "  local int newN = n" +
//            + ": root.std.print(res)" + 
            "  while ( newN > 0 )" + 
            "  {" + 
            "    res = res * newN" + 
            "    newN = newN - 1" + 
            "  }" + 
            "  return res" + 
            "}";
    //@formatter:on

    public static final List< Token > program = new StringTokenizer()
            .tokenize( source );


    public static void main ( String [] args )
    {
        CakeRuntime runtime = Runner.getNewProjectRuntime();

        System.out.println( "----------------Tokenization----------------" );

        System.out.println( "Program: " + program );
        System.out.println();

        System.out.println( "-------------------Parsing------------------" );

        Parser< ? extends SyntaxElement > p = ParsersContainer.INSTANCE.getParserFor( program ).get( 0 );

        Pair< String , ? extends SyntaxElement > pair = p.parse( null , program );

        String address = pair.getKey();

        runtime.addDecalredElement( pair.getKey() , pair.getValue() );

        System.out.println( "Parser name: " + p.getClass().getName() );
        System.out.println( "Method promise: " + ( (Method) runtime.getElement( address ) ).getPromise() );
        System.out.println( "Method body: " + ( (Method) runtime.getElement( address ) ).getSubcommands() );
        
        System.out.println();
        
        System.out.println( "-------------------Running------------------" );
        
        Result res = ( (Method) runtime.getElement( address ) ).run( runtime , new Value(Type.INTEGER.name() , 5 ));
        
        System.out.println( "Result of method: " + res.getExitVariables() );
        
    }

}
