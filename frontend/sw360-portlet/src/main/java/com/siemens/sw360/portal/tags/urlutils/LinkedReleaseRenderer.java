/*
 * Copyright Siemens AG, 2013-2016. Part of the SW360 Portal Project.
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

package com.siemens.sw360.portal.tags.urlutils;

import com.siemens.sw360.datahandler.thrift.ThriftClients;
import com.siemens.sw360.datahandler.thrift.components.ComponentService;
import com.siemens.sw360.datahandler.thrift.components.Release;
import com.siemens.sw360.datahandler.thrift.users.User;
import com.siemens.sw360.datahandler.thrift.users.UserService;
import org.apache.thrift.TException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Helper class to render linked Releases
 *
 * @author birgit.heydenreich@tngtech.com
 */
public class LinkedReleaseRenderer {

    private StringBuilder display;
    private String tableClasses;
    private String idPrefix;
    private String userEmail;

    public LinkedReleaseRenderer(StringBuilder display, String tableClasses, String idPrefix, String userEmail) {
        this.display = display;
        this.tableClasses = tableClasses;
        this.idPrefix = idPrefix;
        this.userEmail = userEmail;
    }


    public <T> void renderReleaseLinkList(StringBuilder display, Map<String, T> releaseRelationshipMap, Set<String> releaseIds, String msg) {
        if (releaseIds.isEmpty()) return;


        StringBuilder candidate = new StringBuilder();
        try {
            UserService.Iface userClient = new ThriftClients().makeUserClient();
            User user = userClient.getByEmail(userEmail);
            ComponentService.Iface componentClient = new ThriftClients().makeComponentClient();

            for (Release release : componentClient.getReleasesById(releaseIds, user)) {
                candidate.append(String.format("<tr><td>%s</td><td>%s</td></tr>", release.getName(), releaseRelationshipMap.get(release.getId())));
            }

        } catch (TException ignored) {
        }

        String tableContent = candidate.toString();
        if (!tableContent.isEmpty()) {

            display.append(String.format("<table class=\"%s\" id=\"%s%s\" >", tableClasses, idPrefix, msg));
            display.append(String.format("<thead><tr><th colspan=\"2\">%s</th></tr><tr><th>Release name</th><th>Release relationship</th></tr></thead><tbody>", msg));
            display.append(tableContent);
            display.append("</tbody></table>");
        }
    }

    public <T> void renderReleaseLinkListCompare(StringBuilder display, Map<String,T> oldReleaseRelationshipMap, Map<String, T> deleteReleaseRelationshipMap, Map<String, T> updateReleaseRelationshipMap, Set<String> releaseIds) {
        if (releaseIds.isEmpty()) return;


        StringBuilder candidate = new StringBuilder();
        try {
            UserService.Iface userClient = new ThriftClients().makeUserClient();
            User user = userClient.getByEmail(userEmail);
            ComponentService.Iface componentClient = new ThriftClients().makeComponentClient();

            final HashSet<String> changedIds = new HashSet<>();

            for (String releaseId : releaseIds) {
                T oldReleaseRelationship = oldReleaseRelationshipMap.get(releaseId);
                T updateReleaseRelationship = updateReleaseRelationshipMap.get(releaseId);

                if (!oldReleaseRelationship.equals(updateReleaseRelationship)) {
                    changedIds.add(releaseId);
                }
            }

            for (Release release : componentClient.getReleasesById(changedIds, user)) {
                String releaseId = release.getId();
                T oldReleaseRelationship = oldReleaseRelationshipMap.get(releaseId);
                T deleteReleaseRelationship = deleteReleaseRelationshipMap.get(releaseId);
                T updateReleaseRelationship = updateReleaseRelationshipMap.get(releaseId);
                candidate.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", release.getName(), oldReleaseRelationship, deleteReleaseRelationship, updateReleaseRelationship));
            }

        } catch (TException ignored) {
        }

        String tableContent = candidate.toString();
        if (!tableContent.isEmpty()) {
            display.append(String.format("<table class=\"%s\" id=\"%sUpdated\" >", tableClasses, idPrefix));
            display.append("<thead><tr><th colspan=\"4\">Updated Release Links</th></tr><tr><th>Release name</th><th>Current Release relationship</th><th>Deleted Release relationship</th><th>Suggested release relationship</th></tr></thead><tbody>");
            display.append(tableContent);
            display.append("</tbody></table>");
        }
    }
}
