/*
 * 05/11/2018 at 20:32:22 ÷.
 * VariableParser.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators.declaration.parser;


import java.util.List;

import com.cake.interpreter.syntax.AccessModifier;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.operators.declaration.VariableDeclaration;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.checkers.declarationChecker.DeclarationChecker;
import com.cake.interpreter.syntax.parsers.checkers.expressionChecker.ExpressionsChecker;
import com.cake.tokenization.tokens.Token;
import com.cake.tokenization.tokens.types.BaseTokenTypesIdentificators;
import com.cake.tokenization.tokens.types.TokenTypesContainer;

import javafx.util.Pair;


/**
 * 
 * Parser for creating variables.
 * 
 * @author Tsvetelin
 *
 */
public class VariableDeclarationParser extends Parser< VariableDeclaration >
{

    private final TokenTypesContainer.TokenTypeHolder identifierType = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.IDENTIFIER.getValue() );

    private final TokenTypesContainer.TokenTypeHolder operatorType = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.OPERATOR.getValue() );

    // @formatter:off
    // <access modifier> | [type] | <identifier> |   =  | <identifier_or_value> |
    //          1        |   2    |      3       |   4  |           5           |
    //          1        | ------ |      2       |   3  |           4           |
    // ################# | ###### | ############ | #### | ##################### |
    //          1        |   2    |      3       | ---- | --------------------- |
    //          1        | ------ |      2       | ---- | --------------------- |
    // @formatter:on


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        if ( !checkList( sequence , 2 ) ) return false;

        boolean correctDeclaration = DeclarationChecker.isCorrectIdentifierDeclaration( sequence );

        int equalsIndex = assignationTokenIndex( sequence );

        if ( equalsIndex != -1 )
        {
            // it IS assigning

            return correctDeclaration && ExpressionsChecker.isCorrectExpression( sequence );
        } else
        {
            // it is NOT assigning
            return correctDeclaration;
        }
    }


    /**
     * 
     * This checks whether the sequence is an assigning(if it contains '=') and
     * returns it's index, otherwise returns -1
     * 
     * @param sequence
     * @return the index or -1 if it does not exist
     */
    private int assignationTokenIndex ( List< Token > sequence )
    {

        Token assignationToken = new Token( "=" , operatorType );

        return sequence.indexOf( assignationToken );
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , VariableDeclaration > parse ( Block superblock , List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {
            AccessModifier accessModifier = AccessModifier.valueOf( tokens.get( 0 ).getToken().toUpperCase() );

            String identificator = getIdentificator( tokens ).getToken();

            String name = superblock != null
                    ? superblock.getFullName() + Block.ADDRESS_SEPARATOR_BETWEEN_BLOCK_AND_VARIABLE + identificator
                    : Block.ROOT_NAME + Block.ADDRESS_SEPARATOR_BETWEEN_BLOCK_AND_VARIABLE + identificator;

            Expression expr = assignationTokenIndex( tokens ) != -1
                    ? new Expression( tokens.subList( assignationTokenIndex( tokens ) + 1 , tokens.size() ) )
                    : null;

            VariableDeclaration variable = new VariableDeclaration( identificator , expr , accessModifier );

            return new Pair< String , VariableDeclaration >( name , variable );
        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }


    /**
     * @param tokens
     * @return
     */
    private Token getIdentificator ( List< Token > tokens )
    {

        if ( tokens.get( 1 ).getTokenType().equals( identifierType ) && tokens.size() > 2 && tokens.get( 2 ) != null
                && tokens.get( 2 ).getTokenType().equals( identifierType ) )
        {
            // this means we have explicit declaration of the type
            // the third one is the name

            return tokens.get( 2 );

        } else
        {
            // this means we do NOT have explicit declaration of the type
            // the second one is the name
            return tokens.get( 1 );
        }
    }

}
