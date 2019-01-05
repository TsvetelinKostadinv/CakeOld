/*
 * 01/01/2019 17:47:59
 * MethodParser.java created by Tsvetelin
 */

package com.cake.syntax.methods.parser;


import java.util.List;

import com.cake.compilation.tokens.Token;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.blocks.parser.BlockParser;
import com.cake.syntax.methods.Method;
import com.cake.syntax.methods.promise.MethodPromise;
import com.cake.syntax.methods.promise.parser.MethodPromiseParser;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.checkers.Checker;
import com.cake.syntax.parsers.checkers.lambdaChecker.LambdaChecker;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class MethodParser extends Parser< Method > implements Checker
{

    private static final Token POINTER_OPERATOR = LambdaChecker.POINTER_OPERATOR;


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        
        int pointerIndex = sequence.indexOf( POINTER_OPERATOR );

        if ( pointerIndex == -1 ) return false;

        List< Token > declaration = sequence.subList( 0 , pointerIndex );
        
        boolean correctDeclaration = new MethodPromiseParser().canParse( declaration );
        
        
        List< Token > body = sequence.subList( pointerIndex + 1 , sequence.size() );
        
        boolean correctBody = new BlockParser().canParse( body );
        
        return correctDeclaration && correctBody;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , Method > parse ( Block superblock , List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {

            List< Token > promiseDeclr = tokens.subList( 0 , tokens.indexOf( POINTER_OPERATOR ) );
            List< Token > bodyDeclr = tokens.subList( tokens.indexOf( POINTER_OPERATOR ) + 1 , tokens.size() );
            
            
            MethodPromise promise = new MethodPromiseParser().parse( superblock , promiseDeclr ).getValue();
            
            
            Block body = new BlockParser().parse( superblock , bodyDeclr ).getValue();
            
            String address = Block.joinNames( superblock , promise );
            Method method = new Method( promise , body , superblock );

            return new Pair< String , Method >( address , method );
        }

        throw new UnsupportedOperationException( "Cannot parse sequence" );
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.syntax.parsers.Parser#parseWithRuntime(com.cake.running.runtime.
     * CakeRuntime, com.cake.syntax.blocks.Block, java.util.List)
     */
    @Override
    public Pair< String , Method > parseWithRuntime ( CakeRuntime runtime , Block superblock , List< Token > tokens )
    {
        Pair< String , Method > pair = this.parse( superblock , tokens );

        runtime.addDecalredElement( pair.getKey() , pair.getValue() );

        return pair;
    }

}
