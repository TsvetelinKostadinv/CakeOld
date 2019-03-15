/*
 * 12/12/2018 16:38:24
 * TemporarySegregator.java created by Tsvetelin
 */

package com.cake.interpreter.utils.commmandSegregation.temporary;


import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.ParsersContainer;
import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.interpreter.utils.commmandSegregation.Segregator;
import com.cake.interpreter.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;
import com.cake.tokenization.tokenizer.Tokenizator;
import com.cake.tokenization.tokenizer.tokenizers.temporary.TempTokenizer;
import com.cake.tokenization.tokens.Token;
import com.cake.tokenization.tokens.types.BaseTokenTypesIdentificators;
import com.cake.tokenization.tokens.types.TokenTypesContainer;


/**
 * @author Tsvetelin
 * @deprecated this is just temporary
 */
@Deprecated
public class TemporarySegregator implements Segregator
{

    public static final Token TEMPORARY_SEGREGATOR = new Token( ":" , Checker.OPERATOR_TYPE );

    public static final Token OPENING_CURLY_BRACE = new Token( "{" , Checker.OPERATOR_TYPE );

    public static final Token CLOSING_CURLY_BRACE = new Token( "}" , Checker.OPERATOR_TYPE );

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

        Map< Parser< ? > , List< Token > > result = new LinkedHashMap<>();

        if ( sequence == null || sequence.size() == 0 ) return new LinkedList<>();

        List< Token > temp = new LinkedList<>();

        for ( int i = 0 ; i < sequence.size() ; i++ )
        {
            Token current = sequence.get( i );
            if ( current.equals( OPENING_CURLY_BRACE ) )
            {
                while ( !current.equals( CLOSING_CURLY_BRACE ) )
                {
                    temp.add( current );
                    i++;
                    current = sequence.get( i );
                }
            } else if ( current.equals( TEMPORARY_SEGREGATOR ) )
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
                temp.add( current );
            }
        }

        return result.entrySet().stream().map( x -> x.getKey().parse( superBlockOfSequence , x.getValue() ).getValue() )
                .collect( Collectors.toList() );
    }


    public List< SyntaxElement > getElements ( Block superblock , List< Token > sequence )
    {
//        sequence = sequence.subList( 1 , sequence.size() - 1 );

        // System.out.println( "In Segregator || Segregating sequence: " + sequence );

        TempTokenizer t = new TempTokenizer();

        String [] commands = sequence.stream().map( x -> x.getToken() ).reduce( "" , ( x , y ) -> x + " " + y )
                .split( "\\:" );

        List< SyntaxElement > result = new LinkedList<>();

        int indentedBlocks = 0;

        for ( int i = commands.length == 1 ? 0 : 1 ; i < commands.length ; i++ )
        {
            List< Token > tokens = t.tokenize( commands[i] );
            // System.out.println( "In segregator || currentTokens : " + tokens );
            if ( tokens.get( tokens.size() - 1 ).equals( OPENING_CURLY_BRACE ) )
            {
                indentedBlocks++;
                while ( indentedBlocks > 0 )
                {
                    i++;
                    List< Token > newTokens = t.tokenize( commands[i] );
                    tokens.addAll( newTokens );
                    int inBlocks = (int) tokens.stream().filter( x -> x.equals( OPENING_CURLY_BRACE ) ).count();
                    int outBlocks = (int) tokens.stream().filter( x -> x.equals( CLOSING_CURLY_BRACE ) ).count();

                    indentedBlocks = inBlocks - outBlocks;
                }
            }
            System.out.println( "In segreagator || Getting parser for: " + tokens );
            Parser< ? > p = parsCont.getParserFor( tokens ).get( 0 );
             System.out.println( "In segreagator || Parser name: " +
             p.getClass().getSimpleName() );
            // System.out.println( "In segreagator || Result Of Parsing: " + p.parse(
            // superblock , tokens ).getValue() ); DO NOT TURN ON THIS LINE
            result.add( p.parse( superblock , tokens ).getValue() );
        }
        return result;
    }


    public List< SyntaxElement > getElements ( CakeRuntime runtime , Block superblock , List< Token > sequence )
    {
        // System.out.println( "In Segregator || Segregating sequence: " + sequence );

        Tokenizator< String > t = new TempTokenizer(); // new StringTokenizer();

        List< String > commandsList = new LinkedList<>();

        String temp = "";

        for ( Token tok : sequence )
        {
            if ( tok.equals( TEMPORARY_SEGREGATOR ) )
            {
                commandsList.add( temp );
                temp = "";
            } else if ( tok.getTokenType().equals( TokenTypesContainer.INSTANCE
                    .getTypeForIdentifier( BaseTokenTypesIdentificators.STRING_LITERAL.getValue() ) ) )
            {
                temp += ( "\"" + tok.getToken() + "\" " );
            } else
            {
                temp += tok.getToken() + " ";
            }
        }
        
        if( !temp.isEmpty() ) commandsList.add( temp );
        
        String [] commands = commandsList.toArray( new String[0] );

        List< SyntaxElement > result = new LinkedList<>();

        int indentedBlocks = 0;

        for ( int i = commands.length == 1 ? 0 : 1 ; i < commands.length ; i++ )
        {
            List< Token > tokens = t.tokenize( commands[i] );
            // System.out.println( "In segregator || currentTokens : " + tokens );
            if ( tokens.size() > 0 && tokens.get( tokens.size() - 1 ).equals( OPENING_CURLY_BRACE ) )
            {
                indentedBlocks++;
                while ( indentedBlocks > 0 )
                {
                    i++;
                    List< Token > newTokens = t.tokenize( commands[i] );
                    tokens.addAll( newTokens );
                    int inBlocks = (int) tokens.stream().filter( x -> x.equals( OPENING_CURLY_BRACE ) ).count();
                    int outBlocks = (int) tokens.stream().filter( x -> x.equals( CLOSING_CURLY_BRACE ) ).count();

                    indentedBlocks = inBlocks - outBlocks;
                }
            }
            if ( !tokens.isEmpty() )
            {
//                System.out.println( "In segreagator || Getting parser for: " + tokens );
                Parser< ? > p = parsCont.getParserFor( tokens ).get( 0 );
//                 System.out.println( "In segreagator || Parser name: " +
//                 p.getClass().getSimpleName() );
//                 System.out.println( "In segreagator || Result Of Parsing: " + p.parse(
                // superblock , tokens ).getValue() ); DO NOT TURN ON THIS LINE
                result.add( p.parse( superblock , tokens ).getValue() );
                // System.out.println( "Moving on in segregator" );
            }

        }
        return result;
    }

}
