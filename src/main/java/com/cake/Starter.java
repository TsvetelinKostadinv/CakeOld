/*
 * 09/01/2019 16:07:58
 * Starter.java created by Tsvetelin
 */

package com.cake;


import java.io.Console;
import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.cake.compilation.tokenizer.Tokenizator;
import com.cake.compilation.tokenizer.tokenizers.fileTokenizer.FileTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.running.runtime.CakeRuntime;
import com.cake.running.runtime.Runner;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.baseElements.RunnableSyntaxElement;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.parsers.ParsersContainer;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class Starter
{

    static Console console = System.console();
    static Scanner debugInput = new Scanner( System.in );

    /**
     * @param args
     */
    public static void main ( String [] args )
    {
        printLogo( );

        String input = console == null ? debugInput.nextLine() : console.readLine( "cake>" );

        while ( true )
        {
            String [] commands = input.split( "\\s+" );

            switch ( commands[0].toLowerCase() )
                {
                    case "run" :
                        if ( commands.length == 2 )
                        {
                            String path = commands[1];

                            CakeRuntime runningRuntime = Runner.getNewProjectRuntime();
                            
                            Tokenizator< File > fileTokenizer = new FileTokenizer();

                            List< Token > program = fileTokenizer.tokenize( new File( path ) );
                            
                            if(program == null) break;
                            
//                            System.out.println( "Program: " + program );
                            
                            Pair< String , ? extends SyntaxElement > pair = ParsersContainer.INSTANCE
                                    .getParserFor( program ).get( 0 )
                                    .parseWithRuntime( runningRuntime , null , program );
                            
//                            System.out.println( "Parsed type: " + pair.getValue().getClass().getSimpleName() );
                            
//                            System.out.println( "Parsing res: " + pair.getValue().toString() );
                            
                            runningRuntime.addDecalredElement( pair.getKey() , pair.getValue() );
                            
                            Result res = ( (RunnableSyntaxElement) pair.getValue() ).run( runningRuntime );
                            
                            System.out.println( "------------------Resuluts------------------" );
                            
                            System.out.println( "Result:" + res );
                            System.out.println(  );
                        } else
                        {
                            System.out.println( "Insuffitient number of args" );
                        }
                        break;

                    case "test" :
                        CakeRuntime testRuntime = Runner.getNewProjectRuntime();

                        System.out.println( "Base runtime elements: " );
                        testRuntime.printAllContents();

                        break;

                    case "exit" :
                        System.exit( 0 );
                        break;
                    default :
                        System.out.println( "Unrecognised command" + commands[0] );
                        break;
                }
            input = console == null ? debugInput.nextLine() : console.readLine( "cake>" );
        }

    }


    /**
     * @param console
     * 
     */
    private static void printLogo (  )
    {

        String [] asciiLogo = { "_________           __            ", "\\_   ___ \\ _____   |  | __  ____  ",
                    "/    \\  \\/ \\__  \\  |  |/ /_/ __ \\ ", "\\     \\____ / __ \\_|    < \\  ___/ ",
                    " \\______  /(____  /|__|_ \\ \\___  >", "        \\/      \\/      \\/     \\/  ", "",
                    "Developed by Tsvetelin Tsetskov and Deivid Kamenov" };
        for ( String s : asciiLogo )
        {
            System.out.printf( "%s%n" , s );
        }

    }

}
