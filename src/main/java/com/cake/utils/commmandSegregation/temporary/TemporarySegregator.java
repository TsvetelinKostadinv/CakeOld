/*
 * 12/12/2018 16:38:24
 * TemporarySegregator.java created by Tsvetelin
 */

package com.cake.utils.commmandSegregation.temporary;


import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cake.compilation.tokenizer.tokenizers.StringTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.ParsersContainer;
import com.cake.syntax.parsers.checkers.Checker;
import com.cake.utils.commmandSegregation.Segregator;
import com.cake.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;


/**
 * @author Tsvetelin
 * @deprecated this is just temporary
 */
@Deprecated
public class TemporarySegregator implements Segregator
{

    public static final Token TEMPORARY_SEGREGATOR = new Token( ":" , Checker.OPERATOR_TYPE );

    private static final ParsersContainer parsCont = ParsersContainer.INSTANCE;


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.utils.commmandSegregation.Segregator#segregateCodeWithParsers(java.
     * util.List)
     */
    @Override
    public List< SyntaxElement > segregateCodeIntoSyntaxElements ( Block superBlockOfSequence , List< Token > sequence )
            throws MisplacedConstruct
    {
        sequence = sequence.subList( 1 , sequence.size() );
        System.out.println( "In Segregator || Sequence: " + sequence );
        Map< Parser< ? > , List< Token > > result = new LinkedHashMap<>();

        if ( sequence == null || sequence.size() == 0 ) return new LinkedList<>();

        List< Token > temp = new LinkedList<>();

        for ( int i = 0 ; i < sequence.size() ; i++ )
        {
            if ( sequence.get( i ).equals( TEMPORARY_SEGREGATOR ) )
            {
                System.out.println( "In Segregator || Found a segregator" );
                System.out.println( "In Segregator || Temporary command: " + temp );
                if ( temp.size() > 0 )
                {
                    System.out.println( "In Segregator || Adding sequence: " + temp );
                    
                    Parser< ? > p = parsCont.getParserFor( temp ).get( 0 );
                    
                    System.out.println( "In Segregator || ParserName: " + p.getClass().getSimpleName() );
                    
                    result.put( p , temp );
                    temp = new LinkedList<>();
                }

            } else
            {
                temp.add( sequence.get( i ) );
            }
        }

        return result.entrySet().stream()
                .map( x -> x.getKey().parse( superBlockOfSequence , x.getValue() ).getValue())
                .collect( Collectors.toList() );
    }
    
    public List< SyntaxElement > getElements( Block superblock,  List< Token > sequence )
    {
        sequence = sequence.subList( 1 , sequence.size()-1 );
        
        StringTokenizer t = new StringTokenizer();
        
        String[] commands = sequence.stream().map( x -> x.getToken() ).reduce( "" , (x,y) -> x+" "+y ).split( "\\:" );
        
        List< SyntaxElement > result = new LinkedList<>();
        
        for( int i=1;i<commands.length;i++ )
        {
            List< Token > tokens = t.tokenize( commands[i] );
            Parser< ? > p = parsCont.getParserFor( tokens ).get( 0 );
            result.add( p.parse( superblock , tokens ).getValue() );
        }
        return result;
    }

}
