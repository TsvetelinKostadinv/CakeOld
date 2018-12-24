/*
 * 07/12/2018 at 14:14:49 ÷.
 * Test.java created by Tsvetelin
 */

package com.cake.running.runtime;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cake.compilation.tokenizer.Tokenizator;
import com.cake.compilation.tokenizer.tokenizers.StringTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.ParsersContainer;


/**
 * @author Tsvetelin
 *
 */
public class Test
{

    public static void main ( String [] args )
    {
        CakeRuntime runtime = Runner.getNewProjectRuntime();

        List< String > codes = new ArrayList<>();
        char counter = 'a';
        String base = "public %s = %d";
        for ( int i = 0 ; i < 20 ; i++ )
        {
            codes.add( String.format( base , counter , i ) );
            counter++;
        }

        Tokenizator< String > tokenizer = new StringTokenizer();

        List< List< Token > > tokenizedCode = codes.stream().map( x -> tokenizer.tokenize( x ) )
                .collect( Collectors.toList() );

        Map< List< Token > , Parser< ? > > parsers = new LinkedHashMap<>();

        tokenizedCode.forEach( x -> parsers.put( x , ParsersContainer.INSTANCE.getParserFor( x ).get( 0 ) ) );

        System.out.println( "----------Start----------" );

        parsers.forEach( ( x , y ) -> y.parseWithRuntime( runtime , null , x ) );

        runtime.forEach( x -> System.out.println( "Delcared element: " + x.toString() ) );

        System.out.println( "-----------End-----------" );

    }
}
