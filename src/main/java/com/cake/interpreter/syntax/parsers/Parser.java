/*
 * 05/11/2018 at 18:02:22 ÷.
 * Parser.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.parsers;


import java.util.List;

import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.tokenization.tokens.Token;

import javafx.util.Pair;


/**
 * A interface to parse a sequence of tokens to a supplied Type
 * 
 * @author Tsvetelin
 *
 * @param <Type>
 *            - the type to which the tokens will be parsed.
 */
public abstract class Parser < Type extends SyntaxElement >
{

    /**
     * 
     */
    public Parser ()
    {
        ParsersContainer.INSTANCE.addParser( this );
    }


    /**
     * 
     * Checks of the parser can parse the sequence.
     * 
     * @param sequence
     *            - the sequence of tokens to be checked
     * @return true of the parser can parse the supplied sequence and false
     *         otherwise
     */
    public abstract boolean canParse ( final List< Token > sequence );


    /**
     * 
     * Parses the tokens to the <code>Type</code>
     * 
     * @param sequence
     *            - the tokens to be parsed
     * @param superblock
     *            - the scope of the sequence of tokens
     * @return - a pair of values, the key being the fully-qualified name of the
     *         variable and a <code>Type</code> representing the parsed tokens
     * @throws UnsupportedOperationException
     *             - if the sequence cannot be parsed
     */
    public abstract Pair< String , Type > parse ( final Block superblock , final List< Token > sequence );

    
    /**
     * 
     * Checks the list of its null and if its size is less than OR EQUAL to the minSize variable
     * 
     * @param sequence
     * @param minSize
     * @return
     */
    protected final boolean checkList(final List< Token > sequence , final int minSize)
    {
        return sequence != null && sequence.size() >= minSize;
    }
    
}
