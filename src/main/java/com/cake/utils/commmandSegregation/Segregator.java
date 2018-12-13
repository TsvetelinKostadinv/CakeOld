/*
 * 08/12/2018 16:31:28
 * Segregator.java created by Tsvetelin
 */

package com.cake.utils.commmandSegregation;

import java.util.List;

import com.cake.compilation.tokens.Token;
import com.cake.syntax.parsers.Parser;
import com.cake.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;

import javafx.util.Pair;

/**
 * @author Tsvetelin
 *
 */
public interface Segregator
{
    /**
     * 
     * Segregates the code into manageable pieces.
     * 
     * @param sequence - the sequence to be sliced up
     * @return - a list of pairs containing the parsers for a piece of code and said piece of code or null if the sequence is null or empty
     * @throws MisplacedConstruct - if the segregator found a misplacedConstruct
     */
    public List< Pair< Parser< ? > , List< Token > > > segregateCodeWithParsers ( List< Token > sequence ) throws MisplacedConstruct;
}
