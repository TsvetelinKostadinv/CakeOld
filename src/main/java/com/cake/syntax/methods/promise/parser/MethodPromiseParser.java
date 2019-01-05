/*
 * 31/12/2019 14:44:19
 * MethodPromiseParser.java created by Tsvetelin
 */

package com.cake.syntax.methods.promise.parser;


import java.util.ArrayList;
import java.util.List;
import com.cake.compilation.tokens.Token;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.methods.Parameter;
import com.cake.syntax.methods.promise.MethodPromise;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.checkers.Checker;
import com.cake.syntax.parsers.checkers.identifierChecker.DeclarationChecker;
import com.cake.syntax.parsers.checkers.lambdaChecker.LambdaChecker;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class MethodPromiseParser extends Parser< MethodPromise > implements Checker
{

    private static final Token EQUALS_TOKEN = new Token( "=" , OPERATOR_TYPE );


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        boolean identifierDeclarationCorrect = DeclarationChecker.checkMethodPromisedeclaration( sequence );
        
        int equalsIndex = sequence.indexOf( EQUALS_TOKEN );

        boolean equalsExists = equalsIndex != -1;
        if ( !equalsExists ) return false;

        List< Token > params = sequence.subList( equalsIndex + 1 , sequence.size() );

        boolean paramsCorrect = LambdaChecker.correctParameterDeclaration( params );

        return identifierDeclarationCorrect && paramsCorrect;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , MethodPromise > parse ( Block superblock , List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {
            // local int test = ()

            AccessModifier mod = AccessModifier.valueOf( tokens.get( 0 ).getToken().toUpperCase() );
            String returnType = tokens.get( 1 ).getToken();
            String identifier = tokens.get( 2 ).getToken();
            List< Token > paramList = tokens.subList( 5 , tokens.size() - 1 ); // without the braces

            List< Parameter > parameters = new ArrayList<>();

            for ( int i = 0 ; i < paramList.size() ; i += 3 )
            {
                String type = paramList.get( i ).getToken();
                String name = paramList.get( i + 1 ).getToken();
                parameters.add( new Parameter( name , type ) );
            }

            MethodPromise promise = new MethodPromise( mod , returnType , identifier ,
                    parameters.toArray( new Parameter[0] ) );

            String address = Block.joinNames( superblock , promise );
            return new Pair< String , MethodPromise >( address , promise );
        }

        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.syntax.parsers.Parser#parseWithRuntime(com.cake.running.runtime.
     * CakeRuntime, com.cake.syntax.blocks.Block, java.util.List)
     */
    @Override
    public Pair< String , MethodPromise > parseWithRuntime ( CakeRuntime runtime , Block superblock ,
            List< Token > tokens )
    {
        Pair< String , MethodPromise > pair = this.parse( superblock , tokens );

        runtime.addDecalredElement( pair.getKey() , pair.getValue() );

        return pair;
    }
}
