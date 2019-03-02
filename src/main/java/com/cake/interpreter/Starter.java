/*
 * 09/01/2019 16:07:58
 * Starter.java created by Tsvetelin
 */

package com.cake.interpreter;


import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.running.runtime.Runner;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.baseElements.RunnableSyntaxElement;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.parsers.ParsersContainer;
import com.cake.tokenization.tokenizer.Tokenizator;
import com.cake.tokenization.tokenizer.tokenizers.fileTokenizer.FileTokenizer;
import com.cake.tokenization.tokens.Token;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class Starter
{

    static Console console = System.console();

    static Scanner debugInput = new Scanner( System.in );

    static String workingPath = getFolder();

    static Map< String , CakeRuntime > activeRuntimes = new HashMap<>();

    public static final String PROMPT = "(" + workingPath + ") : cake>";


    /**
     * @param args
     */
    public static void main ( String [] args )
    {
        printLogo();

        String input = console == null ? debugInput.nextLine() : console.readLine( PROMPT );

        while ( true )
        {
            String [] commands = input.split( "\\s+" );

            switch ( commands[0].toLowerCase() )
                {
                    case "run" :
                        if ( commands.length == 2 )
                        {
                            runProgramFromFile( commands );
                        } else
                        {
                            System.out.println( "Insuffitient number of args" );
                        }
                        break;

                    case "test" :
                        testRuntime();
                        break;

                    case "creater" :
                        startNewRuntime( commands );
                        break;

                    case "addr" :
                        addFileToRuntime( commands );
                        break;

                    case "printr" :
                        printRuntime( commands );
                        break;

                    case "lr" :
                        printActiveRuntimes();
                        break;
                        
                    case "cls" : 
                        clearConsole();
                        printLogo();
                        break;

                    case "exit" :
                        System.exit( 0 );
                        break;
                    default :
                        System.out.println( "Unrecognised command: " + String.join( " " , commands ) );
                        break;
                }
            input = console == null ? debugInput.nextLine() : console.readLine( PROMPT );
        }

    }


    /**
     * 
     */
    private static void clearConsole ()
    {
        try
        {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch ( InterruptedException e )
        {
            e.printStackTrace();
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }


    /**
     * 
     */
    private static void printActiveRuntimes ()
    {
        System.out.println( "Active runtimes:" );
        activeRuntimes.forEach( ( x , y ) -> System.out.println( "\t" + x ) );
    }


    /**
     * @param commands
     */
    private static void printRuntime ( String [] commands )
    {
        if ( commands.length != 2 )
        {
            System.err.println( "Insufficient number of args. Expected \"printr <identifier>\"" );
            return;
        }

        String runtimeName = commands[1];

        if ( activeRuntimes.containsKey( runtimeName ) )
        {
            System.out.println( "Runtime with name " + runtimeName + " contains:" );
            activeRuntimes.get( runtimeName ).printAllContents();
        } else
        {
            System.err.println( "The runtime does not exist!" );
        }
    }


    /**
     * @param commands
     */
    private static void addFileToRuntime ( String [] commands )
    {
        if ( commands.length != 3 )
        {
            System.err.println( "Insufficient number of args. Expected \"addr <identifier> <path_to_file>\"" );
            return;
        }

        String path = commands[2];
        String runtimeName = commands[1];

        if ( activeRuntimes.containsKey( runtimeName ) )
        {
            CakeRuntime runtime = activeRuntimes.get( runtimeName );

            try
            {
                Pair< String , ? extends SyntaxElement > pair = parseMethodFromFile( path , runtime );
                
                if( pair == null )
                {
                    System.err.println( "The program does not exist!" );
                    return;
                }
                
                runtime.addDecalredElement( pair.getKey() , pair.getValue() );

                activeRuntimes.put( runtimeName , runtime );
                System.out.println( "Successfilly added file to the runtime with name " + runtimeName );

            } catch ( Exception e )
            {
                System.err.println( "The file cannot be added!" );
            }

        } else
        {
            System.err.println( "The runtime does not exist!" );
        }

    }


    /**
     * @param commands
     */
    private static void startNewRuntime ( String [] commands )
    {
        if ( commands.length != 2 )
        {
            System.err.println( "Insufficient number of args. Expected \"creater <identifier>\"" );
            return;
        }
        String name = commands[1];

        if ( !activeRuntimes.containsKey( name ) )
        {
            activeRuntimes.put( name , Runner.getNewProjectRuntime() );
            System.out.println( "Successfully started a new runtime" );
        } else
        {
            System.err.printf( "Runtime with name \"%s\" exists!%n" , name );
        }

    }


    /**
     * @param commands
     * @throws Exception
     */
    private static void runProgramFromFile ( String [] commands ) 
    {
        if ( commands.length != 2 )
        {
            System.err.println( "Insufficient number of args. Expected \"run <path>\"" );
            return;
        }

        String path = commands[1];

        CakeRuntime runningRuntime = Runner.getNewProjectRuntime();

        Pair< String , ? extends SyntaxElement > pair = parseMethodFromFile( path , runningRuntime );
        
        if( pair == null )
        {
            System.err.println( "The program does not exist!" );
            return;
        }
        
        runningRuntime.addDecalredElement( pair.getKey() , pair.getValue() );

        Result res = null;

        try
        {
            res = ( (RunnableSyntaxElement) pair.getValue() ).run( runningRuntime );
        } catch ( StackOverflowError e )
        {
            System.err.println( "Stack overflow!!" );
        }

        System.out.println( "------------------Resuluts------------------" );

        System.out.println( "Result:" + res == null ? "No results!" : res );
        System.out.println();

    }


    /**
     * @param path
     * @param runningRuntime
     * @return
     * @throws Exception
     */
    private static Pair< String , ? extends SyntaxElement > parseMethodFromFile ( String path ,
            CakeRuntime runningRuntime ) //throws Exception
    {
        Tokenizator< File > fileTokenizer = new FileTokenizer();

        List< Token > program = fileTokenizer.tokenize( new File( path ) );
        
        //if ( program == null ) { throw new Exception( "The program is null" ); }

//        System.out.println( "Program: " + program );
        
        if( program != null)
        {
            Pair< String , ? extends SyntaxElement > pair = ParsersContainer.INSTANCE.getParserFor( program ).get( 0 )
                    .parse( null , program );
            
            return pair;
        }else {
            return null;
        }
        
        
    }


    /**
     * 
     */
    private static void testRuntime ()
    {
        CakeRuntime testRuntime = Runner.getNewProjectRuntime();

        System.out.println( "Base runtime elements: " );
        testRuntime.printAllContents();
    }


    /**
     * @return
     * @throws Exception
     * 
     */
    private static String getFolder ()
    {
        return System.getProperty( "user.dir" );
    }


    /**
     * @param console
     * 
     */
    private static void printLogo ()
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
