/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 *
 * 170913 - Dan Shinkai
 * Classe desenvolvida baseada na classe SHERPARMEOJournal para carregar dados 
 * a partir do Tematres.
 */
package org.dspace.content.authority;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;

/**
 *  As informações nos quais serão recuperadas do Tematres deverão ser 
 *  especificadas nas variáveis RESULT, LABEL e AUTHORITY.
 *  O RESULT determina a tag contendo todas as informações de uma determinada busca. 
 *  O LABEL é a tag na qual estará a informação a ser apresentada na página.
 *  O AUTHORITY está relacionado com o authority propriamente dito.
 */
public class TematresSponsorship extends TematresProtocol
{
    private static final String RESULT = "term";
    private static final String LABEL = "string";
    private static final String AUTHORITY = "term_id";
    
    public TematresSponsorship()
    {
        super();
    }

    @Override
    public Choices getMatches(String text, int collection, int start, int limit, String locale)
    {
        // punt if there is no query text
        if (text == null || text.trim().length() == 0)
        {
            return new Choices(true);
        }

        // query args to add to Tematres request URL
        List<BasicNameValuePair> args = new ArrayList<BasicNameValuePair>();
        args.add(new BasicNameValuePair("arg", text));
        args.add(new BasicNameValuePair("task","search")); // OR: starts, exact

        Choices result = query(RESULT, LABEL, AUTHORITY, args, start, limit);
        if (result == null)
        {
            result =  new Choices(true);
        }
        return result;
    }

    @Override
    public Choices getMatches(String field, String text, int collection, int start, int limit, String locale) {
        return getMatches(text, collection, start, limit, locale);
    }
}
