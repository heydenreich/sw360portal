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

package com.siemens.sw360.datahandler.common;


import com.google.common.collect.ImmutableMap;
import com.siemens.sw360.datahandler.thrift.ModerationState;
import com.siemens.sw360.datahandler.thrift.Visibility;
import com.siemens.sw360.datahandler.thrift.attachments.AttachmentType;
import com.siemens.sw360.datahandler.thrift.components.*;
import com.siemens.sw360.datahandler.thrift.projects.ProjectRelationship;
import com.siemens.sw360.datahandler.thrift.projects.ProjectState;
import com.siemens.sw360.datahandler.thrift.projects.ProjectType;
import com.siemens.sw360.datahandler.thrift.users.UserGroup;
import org.apache.thrift.TEnum;

import java.util.Map;

/**
 * @author Cedric.Bodet@tngtech.com
 * @author Gerrit.Grenzebach@tngtech.com
 * @author Johannes.Najjar@tngtech.com
 */

public class ThriftEnumUtils {

    private ThriftEnumUtils() {
        // Utility class with only static functions
    }

    //! Enumeration "fancy name" mappings
    private static final ImmutableMap<ComponentType, String> MAP_COMPONENT_TYPE_STRING = ImmutableMap.of(
            ComponentType.OSS, "Open Source Software" ,
            ComponentType.COTS, "Commercial Off The Shelf" ,
            ComponentType.SIEMENS, "Siemens" ,
            ComponentType.FREESOFTWARE, "Free Software");


    private static final ImmutableMap<ProjectType, String> MAP_PROJECT_TYPE_STRING = ImmutableMap.of(
            ProjectType.CUSTOMER, "Customer Project" ,
            ProjectType.INTERNAL, "Internal Project" ,
            ProjectType.PRODUCT, "Product" ,
            ProjectType.SERVICE, "Service");

    private static final ImmutableMap<AttachmentType, String> MAP_ATTACHMENT_TYPE_STRING = ImmutableMap.<AttachmentType, String>builder()
            .put(AttachmentType.DOCUMENT, "Document")
            .put(AttachmentType.SOURCE, "Source file")
            .put(AttachmentType.DESIGN, "Design document")
            .put(AttachmentType.REQUIREMENT, "Requirement document")
            .put(AttachmentType.CLEARING_REPORT, "Clearing report")
            .put(AttachmentType.COMPONENT_LICENSE_INFO_XML, "Component license information (XML)")
            .put(AttachmentType.COMPONENT_LICENSE_INFO_COMBINED, "Component license information (Combined)")
            .put(AttachmentType.SCAN_RESULT_REPORT, "Scan result report")
            .put(AttachmentType.SCAN_RESULT_REPORT_XML, "Scan result report (XML)")
            .put(AttachmentType.SOURCE_SELF, "Source file (Self-made)")
            .put(AttachmentType.BINARY, "Binaries")
            .put(AttachmentType.BINARY_SELF, "Binaries (Self-made)")
            .put(AttachmentType.DECISION_REPORT, "Decision report")
            .put(AttachmentType.LEGAL_EVALUATION, "Legal evaluation report")
            .put(AttachmentType.LICENSE_AGREEMENT, "License agreement")
            .put(AttachmentType.SCREENSHOT, "Screenshot of website")
            .put(AttachmentType.OTHER, "Other")
            .build();

    private static final ImmutableMap<ClearingState, String> MAP_CLEARING_STATUS_STRING = ImmutableMap.of(
            ClearingState.NEW_CLEARING, "New",
            ClearingState.SENT_TO_FOSSOLOGY, "Sent to Fossology",
            ClearingState.UNDER_CLEARING, "Under clearing",
            ClearingState.REPORT_AVAILABLE, "Report available");

    private static final ImmutableMap<FossologyStatus, String> MAP_FOSSOLOGY_STATE_STRING = ImmutableMap.<FossologyStatus, String>builder()
            .put(FossologyStatus.CONNECTION_FAILED, "Connection to FOSSology failed")
            .put(FossologyStatus.NON_EXISTENT, "Non available or removed on remote server")
            .put(FossologyStatus.INACCESSIBLE, "Read permission to upload is denied")
            .put(FossologyStatus.ERROR, "Error")
            .put(FossologyStatus.NOT_SENT, "Not yet sent")
            .put(FossologyStatus.SENT, "Sent")
            .put(FossologyStatus.SCANNING, "Job scanning")
            .put(FossologyStatus.OPEN, "Open")
            .put(FossologyStatus.IN_PROGRESS, "Assigned in progress")
            .put(FossologyStatus.CLOSED, "Closed by assignee")
            .put(FossologyStatus.REJECTED, "Rejected by assignee")
            .put(FossologyStatus.REPORT_AVAILABLE, "Clearing report is available")
            .build();

    private static final ImmutableMap<ModerationState, String> MAP_MODERATION_STATE_STRING = ImmutableMap.of(
            ModerationState.APPROVED, "Approved",
            ModerationState.PENDING, "Pending",
            ModerationState.REJECTED, "Rejected",
            ModerationState.INPROGRESS, "In progress");

    private static final ImmutableMap<ProjectRelationship, String> MAP_PROJECT_RELATION_STRING = ImmutableMap.of(
            ProjectRelationship.UNKNOWN, "Unknown" ,
            ProjectRelationship.DUPLICATE, "Duplicate" ,
            ProjectRelationship.CONTAINED, "Contained" ,
            ProjectRelationship.REFERRED, "Referred");

    private static final ImmutableMap<ReleaseRelationship, String> MAP_RELEASE_RELATION_STRING = ImmutableMap.of(
            ReleaseRelationship.UNKNOWN, "Unknown" ,
            ReleaseRelationship.CONTAINED, "Contained" ,
            ReleaseRelationship.REFERRED, "Referred");

    private static final ImmutableMap<RepositoryType, String> MAP_REPOSITORY_TYPE_STRING = ImmutableMap.<RepositoryType, String>builder()
            .put(RepositoryType.UNKNOWN, "Unknown")
            .put(RepositoryType.GIT, "Git")
            .put(RepositoryType.CLEARCASE, "ClearCase")
            .put(RepositoryType.SVN, "Subversion (SVN)")
            .put(RepositoryType.CVS, "CVS")
            .put(RepositoryType.MERCURIAL, "Mercurial")
            .put(RepositoryType.PERFORCE, "Perforce")
            .put(RepositoryType.VISUAL_SOURCESAFE, "Visual SourceSafe")
            .put(RepositoryType.BAZAAR, "Bazaar")
            .put(RepositoryType.ALIENBRAIN, "Alienbrain")
            .put(RepositoryType.TEAM_FOUNDATION_SERVER, "Team Foundation Server")
            .put(RepositoryType.RATIONAL_SYNERGY, "IBM Rational Synergy")
            .put(RepositoryType.PTC_INTEGRITY, "PTC Integrity")
            .put(RepositoryType.DTR, "SAP Design Time Repository (DTR)")
            .put(RepositoryType.DARCS, "Darcs")
            .put(RepositoryType.FOSSIL, "Fossil")
            .put(RepositoryType.GNU_ARCH, "GNU arch")
            .put(RepositoryType.MONOTONE, "Monotone")
            .put(RepositoryType.BIT_KEEPER, "BitKeeper")
            .put(RepositoryType.RATIONAL_TEAM_CONCERT, "Rational Team Concert")
            .put(RepositoryType.RCS, "Revision Control System (RCS)")
            .build();

    private static final ImmutableMap<MainlineState, String> MAP_MAINLINE_STATE_STRING = ImmutableMap.of(
            MainlineState.OPEN, "Open",
            MainlineState.MAINLINE, "Mainline",
            MainlineState.SPECIFIC, "Specific",
            MainlineState.PHASEOUT, "Phaseout"
    );

    private static final ImmutableMap<Visibility, String> MAP_VISIBILITY_STRING = ImmutableMap.of(
            Visibility.PRIVATE, "Private" ,
            Visibility.ME_AND_MODERATORS, "Me and Moderators",
            Visibility.BUISNESSUNIT_AND_MODERATORS, "Business Unit and Moderators",
            Visibility.EVERYONE, "Everyone"
    );

    private static final ImmutableMap<ProjectState, String> MAP_PROJECT_STATE_STRING = ImmutableMap.of(
            ProjectState.ACTIVE, "Active" ,
            ProjectState.PHASED_OUT, "Phase out" ,
            ProjectState.UNKNOWN, "Unknown");

    private static final ImmutableMap<UserGroup, String> MAP_USER_GROUP_STRING = ImmutableMap.of(
            UserGroup.USER, "User" ,
            UserGroup.CLEARING_ADMIN, "Clearing Admin" ,
            UserGroup.ADMIN, "Admin"
          );


    public static final ImmutableMap<Class<? extends TEnum>, Map<? extends TEnum, String>>
            MAP_ENUMTYPE_MAP = ImmutableMap.<Class<? extends TEnum>, Map<? extends TEnum, String>>builder()
            .put(ComponentType.class, MAP_COMPONENT_TYPE_STRING)
            .put(ProjectType.class, MAP_PROJECT_TYPE_STRING)
            .put(AttachmentType.class, MAP_ATTACHMENT_TYPE_STRING)
            .put(ClearingState.class, MAP_CLEARING_STATUS_STRING)
            .put(FossologyStatus.class, MAP_FOSSOLOGY_STATE_STRING)
            .put(ModerationState.class, MAP_MODERATION_STATE_STRING)
            .put(ProjectRelationship.class, MAP_PROJECT_RELATION_STRING)
            .put(ReleaseRelationship.class, MAP_RELEASE_RELATION_STRING)
            .put(RepositoryType.class, MAP_REPOSITORY_TYPE_STRING)
            .put(MainlineState.class, MAP_MAINLINE_STATE_STRING)
            .put(UserGroup.class, MAP_USER_GROUP_STRING)
            .put(Visibility.class, MAP_VISIBILITY_STRING)
            .put(ProjectState.class, MAP_PROJECT_STATE_STRING)
            .build();

    public static String enumToString(TEnum value) {

        String out = "";
        if (value != null) {
            out = MAP_ENUMTYPE_MAP.get(value.getClass()).get(value);
        }
        return out;
    }

     public static  <T extends Enum<T>> T  stringToEnum(String in, Class<T> clazz){
         for (T t : clazz.getEnumConstants()) {
             if(t.name().equals(in)) return t;
         }

         return null;
     }

}
