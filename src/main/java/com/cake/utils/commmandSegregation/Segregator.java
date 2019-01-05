/*
 * 08/12/2018 16:31:28
 * Segregator.java created by Tsvetelin
 */

package com.cake.utils.commmandSegregation;

import java.util.List;
import com.cake.compilation.tokens.Token;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.blocks.Block;
import com.cake.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;

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
