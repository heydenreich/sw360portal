/*
 * Copyright Siemens AG, 2013-2015. Part of the SW360 Portal Project.
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
package com.siemens.sw360.datahandler.db;

        import com.siemens.sw360.datahandler.common.DatabaseSettings;
        import com.siemens.sw360.datahandler.couchdb.lucene.LuceneAwareDatabaseConnector;
        import com.siemens.sw360.datahandler.couchdb.lucene.LuceneSearchView;
        import com.siemens.sw360.datahandler.thrift.projects.Project;
        import org.apache.log4j.Logger;

        import java.io.IOException;
        import java.util.List;
        import java.util.Map;
        import java.util.Set;

public class ProjectSearchHandler {

    private static final Logger log = Logger.getLogger(ComponentSearchHandler.class);

    private static final LuceneSearchView luceneSearchView = new LuceneSearchView("lucene", "projects",
            "function(doc) {" +
                    "    var ret = new Document();" +
                    "    if(!doc.type) return ret;" +
                    "    if(doc.type != 'projects') return ret;" +
                    "    function idx(obj) {" +
                    "        for (var key in obj) {" +
                    "            switch (typeof obj[key]) {" +
                    "                case 'object':" +
                    "                    idx(obj[key]);" +
                    "                    break;" +
                    "                case 'function':" +
                    "                    break;" +
                    "                default:" +
                    "                    ret.add(obj[key]);" +
                    "                    break;" +
                    "            }" +
                    "        }" +
                    "    };" +
                    "    idx(doc);" +
                    "    if(doc.buisnessUnit !== undefined && doc.buisnessUnit != null doc.buisnessUnit.length >0) {  "+
                    "         ret.add(doc.businessUnit, {\"field\": \"businessUnit\"} );" +
                    "    }" +
                    "    if(doc.projectType !== undefined && doc.projectType != null doc.projectType.length >0) {  "+
                    "      ret.add(doc.projectType, {\"field\": \"projectType\"} );" +
                    "    }" +
                    "    if(doc.projectResponsible !== undefined && doc.projectResponsible != null doc.projectResponsible.length >0) {  "+
                    "      ret.add(doc.projectResponsible, {\"field\": \"projectResponsible\"} );" +
                    "    }" +
                    "    if(doc.name !== undefined && doc.name != null doc.name.length >0) {  "+
                    "      ret.add(doc.name, {\"field\": \"name\"} );" +
                    "    }" +
                    "    if(doc.state !== undefined && doc.state != null doc.state.length >0) {  "+
                    "      ret.add(doc.state, {\"field\": \"state\"} );" +
                    "    }" +
                    "    if(doc.tag !== undefined && doc.tag != null doc.tag.length >0) {  "+
                    "      ret.add(doc.tag, {\"field\": \"tag\"} );" +
                    "    }" +
                    "    return ret;" +
                    "}");


    private final LuceneAwareDatabaseConnector connector;

    public ProjectSearchHandler(String url, String dbName) throws IOException {
        connector = new LuceneAwareDatabaseConnector(url, dbName);
        connector.addView(luceneSearchView);
        connector.setResultLimit(DatabaseSettings.LUCENE_SEARCH_LIMIT);
    }

    public List<Project> search(String text, final Map<String , Set<String > > subQueryRestrictions ){
        return connector.searchViewWithRestrictions(Project.class, luceneSearchView, text, subQueryRestrictions);
    }

}
