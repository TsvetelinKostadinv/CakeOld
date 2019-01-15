/*
 * 14/12/2018 21:17:50
 * ExpressionEvaluator.java created by Tsvetelin
 */

package com.cake.utils.expressions.evaluation;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.mariuszgromada.math.mxparser.Expression;

import com.cake.compilation.tokens.Token;
import com.cake.compilation.tokens.types.TokenTypesContainer.TokenTypeHolder;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.parsers.checkers.Checker;
import com.cake.syntax.variables.Type;
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
     * Evaluates the given expression with the given runtime. If it contains an
     * identifier and runtime is null it throws an exception
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
    static Value evaluateExpression ( CakeRuntime runtime , List< Token > tokens )
    {
//        System.out.println( "Evaluating:" + tokens );
        if ( tokens.size() == 1 )
        {
//            System.out.println( "Its a single token!" );
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
    static Value doExpression ( CakeRuntime runtime , List< Token > tokens )
    {
        StringBuilder sb = new StringBuilder();

        tokens.stream().map( x -> x.getToken() ).forEach( sb::append );

        String expression = sb.toString();

        if ( runtime == null )
        {
            if ( !hasIdentifiers( tokens ) )
            {

                return new Value( Type.REAL.name() , new Expression( expression ).calculate() );
            } else
            {
                throw new RuntimeException( "Cannot get the values without a runtime!" );
            }
        } else
        {

            if ( !hasIdentifiers( tokens ) )
            {
                return new Value( Type.REAL.name() , new Expression( expression ).calculate() );
            } else
            {
                List< Value > values = getValuesForTokens( runtime , tokens );
                List< Token > identifiers = tokens.stream().filter( x -> x.getTokenType().equals( IDENTIFIER_TYPE ) )
                        .collect( Collectors.toList() );

                List< Value > valuesOfIdentifiers = new ArrayList<>();

                List< Integer > identifiersIndexes = tokens.stream().distinct()
                        .filter( x -> x.getTokenType().equals( IDENTIFIER_TYPE ) ).map( x -> tokens.indexOf( x ) )
                        .collect( Collectors.toList() );
                
                int counter = 0;
                
                for ( int i = 0 ; i < identifiersIndexes.size() ; i++ )
                {
                    valuesOfIdentifiers.add( values.get( counter ) );
                    counter++;
                }

                if ( values.size() < identifiers.size() ) throw new RuntimeException( "Not enouth data!" );

                // TODO this should be converted when I add the operator overloading!!
               
                
                expression = formulateExpression( expression , identifiers , valuesOfIdentifiers );

                return new Value( Type.REAL.name() , new Expression( expression ).calculate() );
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
    static Value getValueFromRuntime ( CakeRuntime runtime , String name )
    {
        if ( runtime == null ) return new EmptyIdentity();
        else return ( (Variable) runtime.getElement( name ) ).getValue();

    }


    /**
     * @param tokens
     * @return
     */
    static boolean hasIdentifiers ( List< Token > tokens )
    {
        return tokens.stream().filter( x -> x.getTokenType().equals( IDENTIFIER_TYPE ) ).count() != 0;
    }


    /**
     * @param runtime
     * @param tokens
     * @return
     */
    static List< Value > getValuesForTokens ( CakeRuntime runtime , List< Token > tokens )
    {
        if ( runtime != null )
        {
            return tokens.stream().filter( x -> !x.getTokenType().equals( OPERATOR_TYPE ) )
                    .sorted( ( x , y ) -> -String.CASE_INSENSITIVE_ORDER.compare( x.getToken() , y.getToken() ) )
                    .map( x -> x.getTokenType().equals( IDENTIFIER_TYPE )
                            ? getVariableFromRuntime( runtime , x )
                            : parseValue( x ) )
                    .collect( Collectors.toList() );
        } else
        {
            if ( !hasIdentifiers( tokens ) )
            {
                return tokens.stream().filter( x -> !x.getTokenType().equals( OPERATOR_TYPE ) )
                        .sorted( ( x , y ) -> -String.CASE_INSENSITIVE_ORDER.compare( x.getToken() , y.getToken() ) )
                        .map( x -> parseValue( x ) ).collect( Collectors.toList() );
            } else
            {
                throw new RuntimeException( "Cannot get the values without a runtime!" );
            }

        }

    }


    /**
     * @param runtime
     * @param x
     * @return
     */
    static Value getVariableFromRuntime ( CakeRuntime runtime , Token x )
    {
//        System.out.println( "In Expression || Getting the value of : " + x.getToken() );
        return ( (Variable) runtime.getElement( x.getToken() ) ).getValue();
    }


    /**
     * @param token
     * @return
     */
    static Value parseValue ( Token token )
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
        } else if ( type.equals( STRING_LITERAL_TYPE ) )
        {
            parser = x -> x;
        }

        return new Value( token.getTokenType().getIdentifier().toUpperCase() , parser.apply( token.getToken() ) );
    }


    /**
     * @param identifiers
     * @param valuesOfIdentifiers
     * @return
     */
    static String formulateExpression ( String expression , List< Token > identifiers ,
            List< Value > valuesOfIdentifiers )
    {

        List< String > names = identifiers.stream().map( x -> x.getToken() ).distinct()
                .sorted( ( x , y ) -> -Integer.compare( x.length() , y.length() ) ).collect( Collectors.toList() );

        int counter = 0;

        for ( String name : names )
        {
            expression = expression.replaceAll( name , valuesOfIdentifiers.get( counter ).getValue().toString() );
            counter++;
        }

        return expression;
    }

}
