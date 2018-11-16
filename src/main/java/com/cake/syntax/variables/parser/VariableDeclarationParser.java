/*
 * 05/11/2018 at 20:32:22 ÷.
 * VariableParser.java created by Tsvetelin
 */

package com.cake.syntax.variables.parser;

import java.util.List;

import com.cake.syntax.AccessModifier;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.variables.Variable;
import com.cake.tokenizer.tokenizers.StringTokenizer;
import com.cake.tokens.Token;
import com.cake.tokens.types.BaseTypesIdentificators;
import com.cake.tokens.types.TokenTypesContainer;



/**
 * 
 * Parser for creating variables.
 * 
 * @author Tsvetelin
 *
 */
public class VariableDeclarationParser implements Parser< Variable >
{
    private final TokenTypesContainer.TokenTypeHolder identifierType = TokenTypesContainer.INSTANCE.getTypeForIdentifier( BaseTypesIdentificators.IDENTIFIER.getValue() );
    private final TokenTypesContainer.TokenTypeHolder operatorType = TokenTypesContainer.INSTANCE.getTypeForIdentifier( BaseTypesIdentificators.OPERATOR.getValue() );
    private final TokenTypesContainer.TokenTypeHolder stringLiteral = TokenTypesContainer.INSTANCE.getTypeForIdentifier( BaseTypesIdentificators.STRING_LITERAL.getValue() );
    private final TokenTypesContainer.TokenTypeHolder numberLiteral = TokenTypesContainer.INSTANCE.getTypeForIdentifier( BaseTypesIdentificators.NUMBER_LITERAL.getValue() );
    private final TokenTypesContainer.TokenTypeHolder integerLiteral = TokenTypesContainer.INSTANCE.getTypeForIdentifier( BaseTypesIdentificators.INTEGER_NUMBER_LITERAL.getValue() );
    private final TokenTypesContainer.TokenTypeHolder realNumberLiteral = TokenTypesContainer.INSTANCE.getTypeForIdentifier( BaseTypesIdentificators.REAL_NUMBER_LITERAL.getValue() );
    
    // <access modifier> | [type] | <identifier> |   =  | <identifier_or_value> |
    //          1        |   2    |      3       |   4  |           5           |
    //          1        | ------ |      2       |   3  |           4           |
    // ################# | ###### | ############ | #### | ##################### |
    //          1        |   2    |      3       | ---- | --------------------- |
    //          1        | ------ |      2       | ---- | --------------------- |
    
    /* (non-Javadoc)
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        if( sequence == null
                || sequence.size() <2 ) return false;
        
        boolean correctDeclaration = isDeclarationCorrect( sequence );
        
        if( isAssignation( sequence ) )
        {
            // it IS assigning
            
            int equalsIndex = sequence.indexOf( new Token( "=" , operatorType ) );
            
            boolean afterEquals = checkAfterEquals( sequence.subList( equalsIndex+1 , sequence.size() ) );
            
            return correctDeclaration && afterEquals;
            
        }else {
            // it is NOT assigning
            
            if(sequence.size() > 3 )
            {
                return false;
            }else
            {
                switch ( sequence.size() )
                    {
                        case 2 :
                            Token access = sequence.get( 0 );
                            Token name = sequence.get( 1 );
                            return isAccessModifier( access ) 
                                    && name.getTokenType().equals( identifierType );
                        case 3 :
                            Token accessMod = sequence.get( 0 );
                            Token type = sequence.get( 1 );
                            Token nameOfVar = sequence.get( 2 );
                            return isAccessModifier( accessMod )
                                    && type.getTokenType().equals( identifierType )
                                    && nameOfVar.getTokenType().equals( identifierType );
                        default:
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
     * Checks if the supplied <code>Token</code> is a access modifier
     * 
     * @param token
     * @return
     */
    private boolean isAccessModifier ( Token token )
    {
        String accMod = token.getToken().toUpperCase();
        AccessModifier [] accesses = AccessModifier.values();
        
        for(AccessModifier mod : accesses )
        {
            if( mod.name().equals( accMod ) ) return true;
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
        
        if( afterEquals == null
                || afterEquals.size() < 1 ) return false;
        
        if(afterEquals.size() == 1)
        {
            return afterEquals.get( 0 ).getTokenType().equals( identifierType )
                    || afterEquals.get( 0 ).getTokenType().equals( stringLiteral )
                    || afterEquals.get( 0 ).getTokenType().equals( numberLiteral )
                    || afterEquals.get( 0 ).getTokenType().equals( integerLiteral )
                    || afterEquals.get( 0 ).getTokenType().equals( realNumberLiteral );
        }else {
            //TODO add support for expressions
            throw new UnsupportedOperationException( "Expressions are not yet supported" );
        }
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

    /* (non-Javadoc)
     * @see com.cake.syntax.parsers.Parser#parse(java.util.List)
     */
    @Override
    public Variable parse ( List< Token > tokens )
    {
        throw new UnsupportedOperationException( "The method is not yet implemented" );
    }
    
}
