/*
 * Copyright Siemens AG, 2014-2015. Part of the SW360 Portal Project.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License Version 2.0 as published by the
 * Free Software Foundation with classpath exception.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License version 2.0 for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program (please see the COPYING file); if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */
package com.siemens.sw360.datahandler.couchdb.lucene;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneQuery;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.util.IndexUploader;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.siemens.sw360.datahandler.couchdb.DatabaseConnector;
import org.apache.log4j.Logger;
import org.ektorp.DbAccessException;

import java.io.IOException;
import java.util.*;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Generic database connector for handling lucene searches
 *
 * @author cedric.bodet@tngtech.com
 */
public class LuceneAwareDatabaseConnector extends LuceneAwareCouchDbConnector {

    private static final Logger log = Logger.getLogger(LuceneAwareDatabaseConnector.class);

    private static final Joiner AND = Joiner.on(" AND ");
    private static final Joiner OR = Joiner.on(" OR ");

    private final DatabaseConnector connector;

    /**
     * Maximum number of results to return
     */
    private int resultLimit = 0;

    /**
     * URL/DbName constructor
     */
    public LuceneAwareDatabaseConnector(String url, String dbName) throws IOException {
        this(new DatabaseConnector(url, dbName));
    }

    /**
     * Constructor using a Database connector
     */
    public LuceneAwareDatabaseConnector(DatabaseConnector connector) throws IOException {
        super(connector.getDbName(), connector.getInstance());
        this.connector = connector;
    }

    public boolean addView(LuceneSearchView function) {
        // make sure that the indexer is up-to-date
        IndexUploader uploader = new IndexUploader();
        return uploader.updateSearchFunctionIfNecessary(this, function.searchView,
                function.searchFunction, function.searchBody);
    }

    /**
     * Search with lucene using the previously declared search function
     */
    public <T> List<T> searchView(Class<T> type, LuceneSearchView function, String queryString) {
        return connector.get(type, searchIds(type,function, queryString));
    }

    /**
     * Search with lucene using the previously declared search function only for ids
     */
    public <T> Set<String> searchIds(Class<T> type, LuceneSearchView function, String queryString) {
        LuceneResult queryLuceneResult = searchView(function, queryString, false);
        return getIdsFromResult(queryLuceneResult);
    }

    /**
     * Search with lucene using the previously declared search function
     */
    public LuceneResult searchView(LuceneSearchView function, String queryString) {
        return searchView(function, queryString, true);
    }

    /**
     * Search with lucene using the previously declared search function
     */
    private LuceneResult searchView(LuceneSearchView function, String queryString, boolean includeDocs) {
        if (isNullOrEmpty(queryString)) {
            return null;
        }

        LuceneQuery query = new LuceneQuery(function.searchView, function.searchFunction);

        query.setQuery(queryString);
        query.setIncludeDocs(includeDocs);
        setQueryLimit(query);

        try {
            return queryLucene(query);
        } catch (DbAccessException e) {
            log.error("Error querying database.", e);
            return null;
        }
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    private void setQueryLimit(LuceneQuery query) {
        if (resultLimit > 0) {
            query.setLimit(resultLimit);
        }
    }

    public void setResultLimit(int limit) {
        if (limit >= 0) {
            resultLimit = limit;
        }
    }

    ////////////////////
    // HELPER METHODS //
    ////////////////////

    private static Set<String> getIdsFromResult(LuceneResult result) {
        Set<String> ids = new HashSet<>();
        if (result != null) {
            for (LuceneResult.Row row : result.getRows()) {
                ids.add(row.getId());
            }
        }
        return ids;
    }


    /**
     * Search the database for a given string and types
     */

    public <T> List<T> searchViewWithRestrictions(Class<T> type,LuceneSearchView luceneSearchView, String text, final Map<String , Set<String > > subQueryRestrictions) {
        List <String> subQueries = new ArrayList<>();
        for (Map.Entry<String, Set<String>> restriction : subQueryRestrictions.entrySet()) {

            final Set<String> filterSet =restriction.getValue();

            if(!filterSet.isEmpty()) {
                final String fieldname = restriction.getKey();
                String subQuery = formatSubquery(filterSet, fieldname);
                subQueries.add(subQuery);
            }
        }

        if(!isNullOrEmpty(text))
            subQueries.add( text + "*");

        String query  = AND.join(subQueries);
        return searchView(type, luceneSearchView, query);
    }

    private static String formatSubquery(Set<String> filterSet, final String fieldname) {
        final Function<String, String> addType =new Function<String, String>() {
            @Override
            public String apply(String input) {
                return fieldname+":\""+input+"\"";
            }
        };

        FluentIterable<String> transform = FluentIterable.from(filterSet).transform(addType);
        return "( " + OR.join(transform) + " ) ";
    }

}
