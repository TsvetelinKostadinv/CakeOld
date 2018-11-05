/*
 * 05/11/2018 at 18:02:22 ÷.
 * Parser.java created by Tsvetelin
 */

package com.cake.syntax.parsers;


import java.util.List;

import com.cake.tokens.Token;


/**
 * A interface to parse a sequence of tokens to a supplied Type
 * 
 * @author Tsvetelin
 *
 * @param <Type> - the type to which the tokens will be parsed.
 */
public interface Parser < Type >
{

    /**
     * 
     * Checks of the parser can parse the sequence.
     * 
     * @param sequence
     *            - the sequence of tokens to be checked
     * @return true of the parser can parse the supplied sequence and false
     *         otherwise
     */
    public boolean canParse ( List< Token > sequence );

    /**
     * 
     * Parses the tokens to the <code>Type</code>
     * 
     * @param tokens - the tokens to be parsed
     * @return - <code>Type</code> representing the parsed tokens
     */
    public Type parse ( List< Token > tokens );
}
