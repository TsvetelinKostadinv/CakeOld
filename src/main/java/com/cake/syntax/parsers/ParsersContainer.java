/*
 * 05/11/2018 at 18:15:03 ÷.
 * ParsersContainer.java created by Tsvetelin
 */

package com.cake.syntax.parsers;


import java.util.List;
import com.cake.compilation.tokens.Token;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.parser.VariableDeclarationParser;
import com.cake.utils.container.Container;


/**
 * 
 * Container for all the parsers. They should be included here in order to be
 * used. <br>
 * It searches for parsers that extend
 * <code>com.cake.syntax.parsers.Parser</code> <br>
 * If they extend it they will be added here
 * 
 * @see com.cake.syntax.parsers.Parser
 * 
 * @author Tsvetelin
 *
 */
public class ParsersContainer extends Container< Parser< ? > >
{

    public static ParsersContainer INSTANCE = new ParsersContainer();

    public static final Parser< Variable > VARIABLE_PARSER = new VariableDeclarationParser();


    private ParsersContainer ()
    {
    }


    /**
     * Adds the supplied parser
     * 
     * @param parser
     */
    public void addParser ( Parser< ? > parser )
    {

        if ( !elements.contains( parser ) ) super.addElement( parser );
    }


    /**
     * Removes the supplied parser
     * 
     * @param parser
     */
    public void removeParser ( Parser< ? > parser )
    {

        super.removeElement( parser );
    }


    /**
     * 
     * Determines the parser for the supplied code
     * 
     * @param code
     * @return the parser for this sequence
     */
    public Parser< ? > getParserFor ( List< Token > code )
    {
        for ( Parser< ? > parser : this )
        {
            if ( parser.canParse( code ) ) return parser;
        }
        return null;
    }

}
