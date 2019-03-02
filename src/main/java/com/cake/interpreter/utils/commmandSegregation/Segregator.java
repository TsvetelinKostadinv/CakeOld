/*
 * 08/12/2018 16:31:28
 * Segregator.java created by Tsvetelin
 */

package com.cake.interpreter.utils.commmandSegregation;

import java.util.List;

import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;
import com.cake.tokenization.tokens.Token;

/**
 * @author Tsvetelin
 *
 */
public interface Segregator
{

    /**
     * Segregates the code into manageable pieces.
     * 
     * @param superBlockOfSequence
     * @param sequence - the sequence to be sliced up
     * @return - a list of pairs containing the parsers for a piece of code and said piece of code or null if the sequence is null or empty
     * @throws MisplacedConstruct - if the segregator found a misplacedConstruct
     */
    List< SyntaxElement > segregateCodeIntoSyntaxElements ( Block superBlockOfSequence , List< Token > sequence )
            throws MisplacedConstruct;
}
