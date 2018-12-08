/*
 * 23/10/2018 at 18:23:32
 * TokenPattern.java created by Tsvetelin
 */

package com.cake.compilation.tokens.patterns;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.cake.compilation.tokens.types.BaseTokenTypesIdentificators;
import com.cake.compilation.tokens.types.TokenTypesContainer;
import com.cake.compilation.tokens.types.TokenTypesContainer.TokenTypeHolder;

/**
 * 
 * Defines how a single token looks like
 * 
 * @author Tsvetelin
 *
 */
public class TokenPattern
{
    private static final TokenTypesContainer typesCont = TokenTypesContainer.INSTANCE;
    
    /**
     * Defines the token type that the pattern is for
     */
    private TokenTypesContainer.TokenTypeHolder forToken;
    
    /**
     * This is the pattern of the token
     */
    private Pattern pattern;
    
    /**
     * @param forToken - the token Type identificator object
     * @param pattern - the pattern of the token
     */
    public TokenPattern ( TokenTypeHolder forToken , Pattern pattern )
    {

        super();
        this.forToken = forToken;
        this.pattern = pattern;
    }

    /**
     * 
     * Returns the token name for this pattern
     * 
     * @return
     */
    public TokenTypesContainer.TokenTypeHolder forType ()
    {
        return forToken;
    }

    /**
     * @return the pattern
     */
    public Pattern getPattern ()
    {
        return pattern;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object arg0 )
    {
        return arg0 instanceof TokenPattern
                && ((TokenPattern) arg0).forToken.equals( this.forToken )
                && ((TokenPattern) arg0).pattern.pattern().equals( this.pattern.pattern() );
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
        return "This is a pattern for " + forToken + " with pattern " + pattern.pattern();
    }
    
    public static final TokenPattern EMPTY_PATTERN = 
    new TokenPattern( 
            typesCont.getTypeForIdentifier( BaseTokenTypesIdentificators.EMPTY.getValue() ), 
            Pattern.compile( "" ) );

    public static final TokenPattern OPERATOR_PATTERN = 
    generateOperatorsPattern();

    public static final TokenPattern BOOLEAN_LITERAL =
    new TokenPattern( 
            typesCont.getTypeForIdentifier( BaseTokenTypesIdentificators.BOOLEAN_LITERAL.getValue() ), 
            Pattern.compile( "^(true|false)" ) );

    public static final TokenPattern IDENTIFIER_PATTERN = 
    new TokenPattern( 
            typesCont.getTypeForIdentifier( BaseTokenTypesIdentificators.IDENTIFIER.getValue() ), 
            Pattern.compile( "^([a-zA-z_][a-zA-z_0-9]*)" ) );

    public static final TokenPattern NUMBER_LITERAL_PATTERN =
    new TokenPattern( 
            typesCont.getTypeForIdentifier( BaseTokenTypesIdentificators.NUMBER_LITERAL.getValue() ), 
            Pattern.compile( "((-)?[1-9]?(([0-9])*)(\\.[0-9]*)?){1}" ) );

    public static final TokenPattern REAL_NUMBER_LITERAL_PATTERN =
    new TokenPattern( 
            typesCont.getTypeForIdentifier( BaseTokenTypesIdentificators.REAL_NUMBER_LITERAL.getValue() ), 
            Pattern.compile( "((-)?[1-9]?(([0-9])*)(\\.[0-9]*)?){1}" ) );

    public static final TokenPattern INTEGER_NUMBER_LITERAL_PATTERN =
    new TokenPattern( 
            typesCont.getTypeForIdentifier( BaseTokenTypesIdentificators.INTEGER_NUMBER_LITERAL.getValue() ), 
            Pattern.compile( "^((-)?[1-9][0-9]*)" ) );
    
    public static final TokenPattern STRING_LITERAL_PATTERN = 
    new TokenPattern( 
            typesCont.getTypeForIdentifier( BaseTokenTypesIdentificators.STRING_LITERAL.getValue() ), 
            Pattern.compile( "^(\".*\")" ) );

    public static final TokenPattern ACESS_MODIFIER =
            new TokenPattern( 
                    typesCont.getTypeForIdentifier( BaseTokenTypesIdentificators.ACCESS_MODIFIER.getValue() ), 
                    Pattern.compile( "^(global|public|groupscoped|private)" ) );

    /**
     * @return
     */
    private static TokenPattern generateOperatorsPattern ()
    {
        StringBuilder pattern = new StringBuilder();
        
        String[] operatorTokens = {
                "\\+\\+",
                "\\+\\=",
                "\\+",
                "\\-\\-",
                "\\-\\=",
                "\\-",
                "\\*",
                "\\*\\=",
                "\\/",
                "\\/\\=",
                
                "\\=",
                
                "\\{",
                "\\}",
                "\\[",
                "\\]",
                "\\(",
                "\\)",
                
                "as",
                "\\|\\!\\|", // the |!| symbol to annotate attributes ( or frosting of the Cake I should say )
                "\\:",
                "////",
                
                "\\.",
                "\\,",
                
                "\\>",
                "\\<",
                "\\>\\=",
                "\\>\\=",
                "\\=\\=",
                "\\!\\="
                
        };
        
        Arrays.sort( operatorTokens , ( a , b ) -> a.length() == b.length() ? 0 : a.length() > b.length() ? -1 : 1);
        
        pattern.append( "^(" );
        
        for(String s : operatorTokens)
        {
            pattern.append( "(" + s + ")|" );
        }
        
        pattern.replace( pattern.length()-1 , pattern.length() , "" );
        pattern.append( "){1}" );
        
        return new TokenPattern( 
                typesCont.getTypeForIdentifier( BaseTokenTypesIdentificators.OPERATOR.getValue() ), 
                Pattern.compile( pattern.toString() ) );
    }
}
