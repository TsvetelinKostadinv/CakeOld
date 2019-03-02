/*
 * 27/10/2018 at 18:11:59
 * Tokenizator.java created by Tsvetelin
 */

package com.cake.tokenization.tokenizer;

import java.util.List;

import com.cake.tokenization.tokens.Token;
import com.cake.tokenization.tokens.patterns.container.TokenPatternContainer;
import com.cake.tokenization.tokens.types.TokenTypesContainer;

/**
 * 
 * @author Tsvetelin
 *
 * @param <Type> - the type of the to be tokenized source
 */
public interface Tokenizator < Type >
{
    

    /**
     * Holder of the patterns
     */
    public final TokenPatternContainer patterns = TokenPatternContainer.INSTANCE;


    /**
     * Holder of the token types
     */
    public final TokenTypesContainer types = TokenTypesContainer.INSTANCE;
    
    /**
     * 
     * Turns the supplied source code into tokens
     * 
     * @param source
     * @return a <code>List</code> of <code>Token</code>s or <code>null</code> if the source was <code>null</code>
     */
    public List<Token> tokenize( Type source );
}
