/*
 * 14/12/2018 21:17:50
 * ExpressionEvaluator.java created by Tsvetelin
 */

package com.cake.syntax.expressions.evaluation;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.mariuszgromada.math.mxparser.Expression;

import com.cake.compilation.tokens.Token;
import com.cake.compilation.tokens.types.TokenTypesContainer.TokenTypeHolder;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.parsers.checkers.Checker;
import com.cake.syntax.variables.BaseType;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.EmptyIdentity;
import com.cake.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public interface ExpressionEvaluator extends Checker
{
    /**
     * 
     * Evaluates the given expression with the given runtime. If it contains an identifier and runtime is null it throws an exception
     * 
     * @param runtime
     * @param tokens
     * @return
     */
    public static Value evaluate ( CakeRuntime runtime , List< Token > tokens )
    {
        if ( tokens == null || tokens.size() < 1 ) return null;

        tokens = tokens.subList( tokens.indexOf( new Token( "=" , OPERATOR_TYPE ) ) + 1 , tokens.size() );

        return evaluateExpression( runtime , tokens );
    }


    /**
     * @param runtime
     * @param tokens
     */
    private static Value evaluateExpression ( CakeRuntime runtime , List< Token > tokens )
    {
        if ( tokens.size() == 1 )
        {
            if ( tokens.get( 0 ).getTokenType().equals( IDENTIFIER_TYPE ) )
            {
                return getValueFromRuntime( runtime , tokens.get( 0 ).getToken() );
            } else
            {
                Token value = tokens.get( 0 );

                return new Value( value.getTokenType().getIdentifier() , value.getToken() );
            }
        } else
        {
            return doExpression( runtime , tokens );
        }

    }


    /**
     * @param runtime
     * @param tokens
     * @return
     */
    private static Value doExpression ( CakeRuntime runtime , List< Token > tokens )
    {
        if ( runtime == null )
        {
            if ( !hasIdentifiers( tokens ) )
            {
                StringBuilder sb = new StringBuilder();
                
                tokens.stream().map( x -> x.getToken() ).forEach( sb::append );
                
                return new Value( BaseType.REAL.name() , new Expression( sb.toString() ).calculate() );
            } else
            {
                throw new RuntimeException( "Cannot get the values without a runtime!" );
            }
        } else
        {
            
            
            if ( !hasIdentifiers( tokens ) )
            {
                StringBuilder sb = new StringBuilder();
                
                tokens.stream().map( x -> x.getToken() ).forEach( sb::append );
                
                return new Value( BaseType.REAL.name() , new Expression( sb.toString() ).calculate() );
            } else
            {
                List< Value > values;
                
                values = getValuesForTokens( runtime , tokens );

                values.forEach( System.out::println );
                // TODO THIS IS WHAT I NEED TO HANDLE MYSLEF
                throw new UnsupportedOperationException( "Not supported yet!" );
            }
        }
    }


    /**
     * 
     * @param runtime
     *            - the runtime to be got from
     * @param name
     *            - the full name of the variable
     * @return - a value if it was found and an empty identity otherwise
     */
    private static Value getValueFromRuntime ( CakeRuntime runtime , String name )
    {
        if ( runtime == null ) return new EmptyIdentity();
        else return ( (Variable) runtime.getElement( name ) ).getValue();

    }


    /**
     * @param tokens
     * @return
     */
    private static boolean hasIdentifiers ( List< Token > tokens )
    {
        return tokens.stream().filter( x -> x.getTokenType().equals( IDENTIFIER_TYPE ) ).count() != 0;
    }


    /**
     * @param runtime
     * @param tokens
     * @return
     */
    private static List< Value > getValuesForTokens ( CakeRuntime runtime , List< Token > tokens )
    {
        if ( runtime != null )
        {
            return tokens.stream().filter( x -> !x.getTokenType().equals( OPERATOR_TYPE ) )
                    .map( x -> x.getTokenType().equals( IDENTIFIER_TYPE )
                            ? ( (Variable) runtime.getElement( x.getToken() ) ).getValue()
                            : parseValue( x ) )
                    .collect( Collectors.toList() );
        } else
        {
            return tokens.stream().filter( x -> !x.getTokenType().equals( OPERATOR_TYPE ) ).map( x -> parseValue( x ) )
                    .collect( Collectors.toList() );
        }

    }


    /**
     * @param token
     * @return
     */
    public static Value parseValue ( Token token )
    {
        TokenTypeHolder type = token.getTokenType();

        Function< String , Object > parser = x -> x.toString();

        if ( type.equals( BOOLEAN_LITERAL_TYPE ) )
        {
            parser = x -> Boolean.parseBoolean( x );
        } else if ( type.equals( INTEGER_NUMBER_LITERAL_TYPE ) )
        {
            parser = x -> Integer.parseInt( x );
        } else if ( type.equals( NUMBER_LITERAL_TYPE ) || type.equals( REAL_NUMBER_LITERAL_TYPE ) )
        {
            parser = x -> Double.parseDouble( x );
        }else if ( type.equals( STRING_LITERAL_TYPE )) {
            parser = x -> x;
        }

        return new Value( token.getTokenType().getIdentifier().toUpperCase() , parser.apply( token.getToken() ) );
    }

}
