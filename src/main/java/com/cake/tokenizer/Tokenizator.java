/*
 * 27/10/2018 at 18:11:59
 * Tokenizator.java created by Tsvetelin
 */

package com.cake.tokenizer;

import java.util.List;

import com.cake.tokens.Token;

/**
 * 
 * @author Tsvetelin
 *
 * @param <Type> - the type of the to be tokenized source
 */
public interface Tokenizator < Type >
{
    /**
     * 
     * Turns the supplied source code into tokens
     * 
     * @param source
     * @return a <code>List</code> of <code>Token</code>s or <code>null</code> if the source was <code>null</code>
     */
    public List<Token> tokenize( Type source );
}
