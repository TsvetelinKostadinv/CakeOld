/*
 * 05/11/2018 at 20:32:22 ÷.
 * VariableParser.java created by Tsvetelin
 */

package com.cake.syntax.variables.parser;


import java.util.List;

import javax.naming.NameNotFoundException;

import com.cake.compilation.tokens.Token;
import com.cake.compilation.tokens.types.BaseTokenTypesIdentificators;
import com.cake.compilation.tokens.types.TokenTypesContainer;
import com.cake.compilation.tokens.types.TokenTypesContainer.TokenTypeHolder;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.variables.BaseType;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.EmptyIdentity;
import com.cake.syntax.variables.values.Value;

import javafx.util.Pair;


/**
 * 
 * Parser for creating variables.
 * 
 * @author Tsvetelin
 *
 */
public class VariableDeclarationParser extends Parser< Variable >
{

    private final TokenTypesContainer.TokenTypeHolder identifierType = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.IDENTIFIER.getValue() );

    private final TokenTypesContainer.TokenTypeHolder operatorType = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.OPERATOR.getValue() );

    private final TokenTypesContainer.TokenTypeHolder stringLiteral = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.STRING_LITERAL.getValue() );

    private final TokenTypesContainer.TokenTypeHolder numberLiteral = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.NUMBER_LITERAL.getValue() );

    private final TokenTypesContainer.TokenTypeHolder integerLiteral = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.INTEGER_NUMBER_LITERAL.getValue() );

    private final TokenTypesContainer.TokenTypeHolder realNumberLiteral = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.REAL_NUMBER_LITERAL.getValue() );

    private final TokenTypesContainer.TokenTypeHolder booleanLiteral = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.BOOLEAN_LITERAL.getValue() );

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

        if ( sequence == null || sequence.size() < 2 ) return false;

        boolean correctDeclaration = isDeclarationCorrect( sequence );

        if ( isAssignation( sequence ) )
        {
            // it IS assigning

            int equalsIndex = sequence.indexOf( new Token( "=" , operatorType ) );

            boolean afterEquals = checkAfterEquals( sequence.subList( equalsIndex + 1 , sequence.size() ) );

            return correctDeclaration && afterEquals;

        } else
        {
            // it is NOT assigning

            if ( sequence.size() > 3 )
            {
                return false;
            } else
            {
                switch ( sequence.size() )
                    {
                        case 2 :
                            Token access = sequence.get( 0 );
                            Token name = sequence.get( 1 );
                            return isAccessModifier( access ) && name.getTokenType().equals( identifierType );
                        case 3 :
                            Token accessMod = sequence.get( 0 );
                            Token type = sequence.get( 1 );
                            Token nameOfVar = sequence.get( 2 );
                            return isAccessModifier( accessMod ) && type.getTokenType().equals( identifierType )
                                    && nameOfVar.getTokenType().equals( identifierType );
                        default :
                            return false;
                    }
            }

        }
    }


    /**
     * 
     * This checks whether the sequence is an assigning(if it contains '=')
     * 
     * @param sequence
     * @return
     */
    private boolean isAssignation ( List< Token > sequence )
    {

        Token assignationToken = new Token( "=" , operatorType );
        return sequence.contains( assignationToken );
    }


    /**
     * 
     * Checks if the supplied <code>Token</code> is an identifier
     * 
     * @param token
     * @return
     */
    private boolean isIdentificator ( Token token )
    {

        return token.getTokenType().equals( identifierType );
    }


    /**
     * 
     * Checks if the supplied <code>Token</code> is a access modifier
     * 
     * @param token
     * @return
     */
    private boolean isAccessModifier ( Token token )
    {

        String accMod = token.getToken().toUpperCase();
        AccessModifier [] accesses = AccessModifier.values();

        for ( AccessModifier mod : accesses )
        {
            if ( mod.name().equals( accMod ) ) return true;
        }

        return false;

    }


    /**
     * 
     * Checks if the declaration of the variable is correct in the beggining
     * 
     * @param sequence
     */
    private boolean isDeclarationCorrect ( List< Token > sequence )
    {

        int accessModifierIndex = 0;
        boolean isAccessModifierInPlace = isAccessModifier( sequence.get( accessModifierIndex ) );
        boolean isThereAnotherIdentifier = isIdentificator( sequence.get( 1 ) );

        return isAccessModifierInPlace && isThereAnotherIdentifier;
    }


    /**
     * 
     * Checks the list is in the correct format to be after an equals sign
     * 
     * @param subList
     * @return
     */
    private boolean checkAfterEquals ( List< Token > afterEquals )
    {

        if ( afterEquals == null || afterEquals.size() < 1 ) return false;

        if ( afterEquals.size() == 1 )
        {
            return afterEquals.get( 0 ).getTokenType().equals( identifierType )
                    || afterEquals.get( 0 ).getTokenType().equals( stringLiteral )
                    || afterEquals.get( 0 ).getTokenType().equals( numberLiteral )
                    || afterEquals.get( 0 ).getTokenType().equals( integerLiteral )
                    || afterEquals.get( 0 ).getTokenType().equals( realNumberLiteral )
                    || afterEquals.get( 0 ).getTokenType().equals( booleanLiteral );
        } else
        {
            // TODO add support for expressions
            throw new UnsupportedOperationException( "Expressions are not yet supported" );
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(java.util.List)
     */
    @Override
    public Pair< String , Variable > parseAndAddToRuntime ( CakeRuntime runtime , Block superblock ,
            List< Token > tokens )
    {
        Pair< String , Variable > pair = parse( superblock , tokens );
        runtime.addDecalredElement( pair.getKey() , pair.getValue() );
        return pair;
    }


    /**
     * @param tokens
     * @return
     * @throws NameNotFoundException
     */
    private Value getValueForToken ( CakeRuntime runtime , String name , List< Token > tokens )
    {
        Variable variable;

        if ( runtime != null && ( variable = (Variable) runtime.getElement( name ) ) != null )
        {
            return variable.getValue();
        } else
        {
            List< Token > afterEquals = tokens.subList( tokens.indexOf( new Token( "=" , operatorType ) ) + 1 ,
                    tokens.size() );

            switch ( afterEquals.size() )
                {
                    case 1 :
                        Token assignee = afterEquals.get( 0 );

                        TokenTypeHolder holder = assignee.getTokenType();
                        if ( !holder.equals( identifierType ) )
                        {
                            return new Value( BaseType.getTypeFor( holder ).toString() , assignee.getToken() );
                        } else
                        {
                            throw new RuntimeException( tokens.get( 0 ).getToken() + " does not exist!" ,
                                    new NameNotFoundException() );
                        }
                    default :
                        break;
                }

        }

        return null;
    }


    /**
     * @param tokens
     * @return
     */
    private Token getIdentificator ( List< Token > tokens )
    {

        if ( tokens.get( 1 ).getTokenType().equals( identifierType )
                && tokens.size() > 2
                && tokens.get( 2 ) != null
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


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , Variable > parse ( Block superblock , List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {
            AccessModifier accessModifier = AccessModifier.valueOf( tokens.get( 0 ).getToken().toUpperCase() );

            String identificator = getIdentificator( tokens ).getToken();
            String name = superblock != null ? superblock.getFullName() + "#" + identificator
                    : Block.ROOT_NAME + "#" + identificator;

            Value value = isAssignation( tokens ) ? getValueForTokenWithoutRuntime( name , tokens )
                    : new EmptyIdentity();

            Variable variable = new Variable( identificator , value , accessModifier );

            return new Pair< String , Variable >( name , variable );
        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }


    /**
     * @param name
     * @param tokens
     * @return
     */
    private Value getValueForTokenWithoutRuntime ( String name , List< Token > tokens )
    {
        return getValueForToken( null , name , tokens );
    }

}
