/*
 * 08/12/2018 16:29:26
 * CommandsSegregator.java created by Tsvetelin
 */

package com.cake.interpreter.utils.commmandSegregation;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.methods.promise.parser.MethodPromiseParser;
import com.cake.interpreter.syntax.operators.declaration.parser.VariableDeclarationParser;
import com.cake.interpreter.syntax.operators.expressions.parser.ExpressionParser;
import com.cake.interpreter.syntax.operators.reassignmentOp.parser.ReassignmentOperationParser;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.ParsersContainer;
import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.interpreter.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;
import com.cake.tokenization.tokenizer.tokenizers.stringTokenizer.StringTokenizer;
import com.cake.tokenization.tokens.Token;


/**
 * 
 * Separates commands
 * 
 * @author Tsvetelin
 *
 */
public class CommandsSegregator implements Segregator , Checker
{

    public static final ParsersContainer PARSERS_CONTAINER = ParsersContainer.INSTANCE;

    private static final Token EQUALS_TOKEN = new Token( "=" , OPERATOR_TYPE );

    private static final Token POINTER_OPERATOR_TOKEN = new Token( "->" , OPERATOR_TYPE );


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.utils.commmandSegregation.Segregator#segregateCodeIntoSyntaxElements
     * (com.cake.syntax.blocks.Block, java.util.List)
     */
    @Override
    public List< SyntaxElement > segregateCodeIntoSyntaxElements ( Block superBlockOfSequence , List< Token > sequence )
            throws MisplacedConstruct
    {
        List< SyntaxElement > res = new LinkedList<>();

        List< Token > currentCommand = new ArrayList<>();
        for ( int i = 0 ; i < sequence.size() ; i++ )
        {
//             System.out.println( );
            Token t = sequence.get( i );
//             System.out.println( this.getClass() + " || Adding token: " + t );
            currentCommand.add( t );
            List< Parser< ? > > parsers = PARSERS_CONTAINER.getParserFor( currentCommand );

//             System.out.println( this.getClass() + " || CurrentCommand: " + currentCommand
//             );
//             System.out.println( this.getClass() + " || Parsers: " + parsers );
//
//             try
//             {
//             Thread.sleep( 500 );
//             } catch ( InterruptedException e )
//             {
//             e.printStackTrace();
//             }

            if ( parsers.size() == 1 )
            {
                if ( parsers.get( 0 ) instanceof VariableDeclarationParser )
                {
                    // it's a variable declaration

                    if ( sequence.size() > i+1 && 
                            sequence.get( i + 1 ).getTokenType().equals( IDENTIFIER_TYPE ) )
                    {
                        // it's variable declaration with identifier
                        continue;
                    } else if ( sequence.size() > i+1 && 
                            sequence.get( i + 1 ).equals( EQUALS_TOKEN ) )
                    {
                        // It's declaration with identifier and an assignation

                        List< Token > expression = new ArrayList<>();

                        for ( int j = 0 ; j < sequence.size() ; j++ )
                        {
                            expression.add( sequence.get( i + j + 1 ) );
                            ExpressionParser exprPars = new ExpressionParser();

                            if ( !exprPars.canParse( expression )
                                    && exprPars.canParse( expression.subList( 0 , expression.size() - 1 ) ) )
                            {
                                break;
                            }
                        }
                        expression.remove( expression.size() - 1 );
                        currentCommand.addAll( expression );

                        i += expression.size();
                        res.add( parsers.get( 0 ).parse( superBlockOfSequence , currentCommand ).getValue() );
                        currentCommand.clear();

                    } else
                    {
                        res.add( parsers.get( 0 ).parse( superBlockOfSequence , currentCommand ).getValue() );
                        currentCommand.clear();
                    }
                } else if ( parsers.get( 0 ) instanceof MethodPromiseParser
                        && sequence.get( i + 1 ).equals( POINTER_OPERATOR_TOKEN ) )
                {
                    // IT's a method
                    continue;
                } else if ( parsers.get( 0 ) instanceof ExpressionParser )
                {
                    if ( sequence.get( i + 1 ).getTokenType().equals( OPERATOR_TYPE ) )
                    {
                        continue;
                    }
                } else if ( parsers.get( 0 ) instanceof ReassignmentOperationParser )
                {
                    List< Token > expression = new ArrayList<>();

                    expression.add( sequence.get( i ) );

                    for ( int j = 0 ; j < sequence.size() ; j++ )
                    {
                        // System.out.println( "PArt of the expression: " + sequence.get( i + j + 1 ) );
                        expression.add( sequence.get( i + j + 1 ) );
                        ExpressionParser exprPars = new ExpressionParser();

                        // System.out.println( "Current Expression: " + expression );

                        // System.out.println( "Can parse: " + exprPars.canParse( expression ) );
                        // System.out.println( "The next Token is: " + sequence.get( i + j + 1 ) );
                        // System.out.println( "Is it operator: " + sequence.get( i + j + 1
                        // ).getTokenType().equals( OPERATOR_TYPE ) );

                        if ( exprPars.canParse( expression )
                                && !sequence.get( i + j + 1 ).getTokenType().equals( OPERATOR_TYPE ) )
                        {
                            break;
                        }
                    }

                    // System.out.println( "Expression: " + expression );
                    // System.out.println( "Old Command: " + currentCommand );

                    currentCommand.remove( currentCommand.size() - 1 );
                    currentCommand.addAll( expression );

                    // System.out.println( "Current Command: " + currentCommand );

                    i += expression.size() - 1;
                    res.add( parsers.get( 0 ).parse( superBlockOfSequence , currentCommand ).getValue() );
                    currentCommand.clear();

                } else
                {
//                    System.out.println( this.getClass() + "|| Adding : "
//                            + parsers.get( 0 ).parse( superBlockOfSequence , currentCommand ).getValue() );
                    res.add( parsers.get( 0 ).parse( superBlockOfSequence , currentCommand ).getValue() );
                    currentCommand.clear();
                }

            }//else 
//                if( parsers.size() == 2)
//            {
//                System.out.println( this.getClass() + " || DUO PARSERS" );
//                System.out.println( this.getClass() + " || CURRENT COMMAND: " + currentCommand );
//                if ( parsers.get( 0 ) instanceof ExpressionParser )
//                {
//                    if( parsers.get( 1 ) instanceof MethodInvokationOperatorParser )
//                    {
//                        res.add( parsers.get( 1 ).parse( superBlockOfSequence , currentCommand ).getValue() );
//                        currentCommand.clear();
//                    }
//                }
//            }
        }
//        System.out.println( this.getClass() + " || Last command:" + currentCommand );

        if ( !currentCommand.isEmpty() )
        {
            res.add( PARSERS_CONTAINER.getParserFor( currentCommand ).get( 0 )
                    .parse( superBlockOfSequence , currentCommand ).getValue() );
        }
        return res;
    }


    public static void main ( String [] args )
    {
        System.out.println( "TESTING IN " + CommandsSegregator.class );

        String code = "root.std.println(res)";

        List< Token > program = new StringTokenizer().tokenize( code );
        System.out.println( "Program" + program );
        
        System.out.println( "Other can parse" + PARSERS_CONTAINER.getParserFor( program ) );
    }

}
