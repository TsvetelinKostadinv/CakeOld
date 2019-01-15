/*
 * 08/12/2018 16:29:26
 * CommandsSegregator.java created by Tsvetelin
 */

package com.cake.utils.commmandSegregation;


import java.util.List;
import com.cake.compilation.tokens.Token;
import com.cake.compilation.tokens.types.BaseTokenTypesIdentificators;
import com.cake.compilation.tokens.types.TokenTypesContainer;
import com.cake.compilation.tokens.types.TokenTypesContainer.TokenTypeHolder;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.blocks.Block;
import com.cake.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;


/**
 * 
 * Separates commands
 * 
 * @author Tsvetelin
 *
 */
@SuppressWarnings ( "all" )
public class CommandsSegregator implements Segregator
{

    private final TokenTypesContainer typesCont = TokenTypesContainer.INSTANCE;

    
    private final TokenTypeHolder accessType = typesCont
            .getTypeForIdentifier( BaseTokenTypesIdentificators.ACCESS_MODIFIER.getValue() );

    private final TokenTypeHolder keywordType = typesCont
            .getTypeForIdentifier( BaseTokenTypesIdentificators.KEYWORD.getValue() );

    private final String useKeyword = "use";

    private final TokenTypeHolder identifierType = typesCont
            .getTypeForIdentifier( BaseTokenTypesIdentificators.IDENTIFIER.getValue() );

    /* (non-Javadoc)
     * @see com.cake.utils.commmandSegregation.Segregator#segregateCodeIntoSyntaxElements(com.cake.syntax.blocks.Block, java.util.List)
     */
    @Override
    public List< SyntaxElement > segregateCodeIntoSyntaxElements ( Block superBlockOfSequence , List< Token > sequence )
            throws MisplacedConstruct
    {
        // TODO implement commands segregation
        return null;
    }

    

}
