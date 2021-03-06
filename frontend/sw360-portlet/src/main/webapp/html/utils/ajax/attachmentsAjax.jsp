<%--
  ~ Copyright Siemens AG, 2013-2015. Part of the SW360 Portal Project.
  ~
  ~ This program is free software; you can redistribute it and/or modify it under
  ~ the terms of the GNU General Public License Version 2.0 as published by the
  ~ Free Software Foundation with classpath exception.
  ~
  ~ This program is distributed in the hope that it will be useful, but WITHOUT
  ~ ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
  ~ FOR A PARTICULAR PURPOSE. See the GNU General Public License version 2.0 for
  ~ more details.
  ~
  ~ You should have received a copy of the GNU General Public License along with
  ~ this program (please see the COPYING file); if not, write to the Free
  ~ Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
  ~ 02110-1301, USA.
  --%>
<%@ page import="com.siemens.sw360.datahandler.common.SW360Constants" %>
<%@ page import="com.siemens.sw360.datahandler.thrift.attachments.CheckStatus" %>
<%@include file="/html/init.jsp" %>

<%@ taglib prefix="sw360" uri="/WEB-INF/customTags.tld" %>

<portlet:defineObjects/>
<liferay-theme:defineObjects/>

<%@ page import="com.siemens.sw360.datahandler.thrift.attachments.Attachment" %>

<%@ page import="com.siemens.sw360.datahandler.thrift.components.Release" %>

<jsp:useBean id="attachments" type="java.util.Set<com.siemens.sw360.datahandler.thrift.attachments.Attachment>"
             scope="request"/>
<jsp:useBean id="documentType" type="java.lang.String" scope="request"/>

<core_rt:if test="${attachments.size()>0}">
    <tr id="noAttachmentsRow" style="display: none">
        <td colspan="5">No attachments yet.</td>
    </tr>
</core_rt:if>
<core_rt:if test="${attachments.size()==0}">
    <tr id="noAttachmentsRow">
        <td colspan="5">No attachments yet.</td>
    </tr>
</core_rt:if>

<core_rt:forEach items="${attachments}" var="attachment" varStatus="loop">
    <tr id="componentattachmentrow${loop.count}" class="tr_clone">
        <td colspan="5">
            <table style="width:100%">
                <tr>
                    <td>
                        <input type="hidden" value="${attachment.attachmentContentId}"
                               name="<portlet:namespace/><%=Release._Fields.ATTACHMENTS%><%=Attachment._Fields.ATTACHMENT_CONTENT_ID%>">
                        <label class="textlabel stackedLabel" for="comp_file${loop.count}">File name</label>
                        <input class="toplabelledInput" id="comp_file${loop.count}" style="margin-left: 10px;"
                               name="<portlet:namespace/><%=Release._Fields.ATTACHMENTS%><%=Attachment._Fields.FILENAME%>"
                               type="text"
                               value="<sw360:out value="${attachment.filename}"/>" readonly/>
                    </td>
                    <td>
                        <label class="textlabel stackedLabel" for="comp_filetype${loop.count}">Attachment type</label>
                        <select class="toplabelledInput" id="comp_filetype${loop.count}"
                                name="<portlet:namespace/><%=Release._Fields.ATTACHMENTS%><%=Attachment._Fields.ATTACHMENT_TYPE%>"
                                style="min-width: 162px; min-height: 28px;">
                            <sw360:DisplayEnumOptions
                                    options="<%=SW360Constants.allowedAttachmentTypes(documentType)%>"
                                    selected="${attachment.attachmentType}"/>
                        </select>
                    </td>
                    <td>
                        <label class="textlabel stackedLabel" for="comp_filecomment${loop.count}">Comments</label>
                        <input class="toplabelledInput" id="comp_filecomment${loop.count}"
                               name="<portlet:namespace/><%=Release._Fields.ATTACHMENTS%><%=Attachment._Fields.CREATED_COMMENT%>"
                               type="text" placeholder="Enter comments"
                               value="<sw360:out value="${attachment.createdComment}"/>"
                        />
                    </td>
                    <td class="downloader">
                        <sw360:DisplayDownloadAttachment id="${attachment.attachmentContentId}"
                                                         name="${attachment.filename}"/>
                    </td>
                    <td class="deletor">
                        <img src="<%=request.getContextPath()%>/images/Trash.png"
                             onclick="deleteAttachment('componentattachmentrow${loop.count}','${attachment.attachmentContentId}')"
                             alt="Delete">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="textlabel stackedLabel" for="approval_status${loop.count}">Approval Status</label>
                        <select class="toplabelledInput" id="approval_status${loop.count}"
                                name="<portlet:namespace/><%=Release._Fields.ATTACHMENTS%><%=Attachment._Fields.CHECK_STATUS%>"
                                style="min-width: 162px; min-height: 28px;">
                            <sw360:DisplayEnumOptions type="<%=CheckStatus.class%>"
                                                      selected="${attachment.checkStatus}"/>
                        </select>
                    </td>
                    <td>
                        <label class="textlabel stackedLabel" for="comp_checkedcomment${loop.count}">Approval
                            Comments</label>
                        <input class="toplabelledInput" id="comp_checkedcomment${loop.count}"
                               name="<portlet:namespace/><%=Release._Fields.ATTACHMENTS%><%=Attachment._Fields.CHECKED_COMMENT%>"
                               type="text" placeholder="Enter comments"
                               value="<sw360:out value="${attachment.checkedComment}"/>"
                        />
                    </td>
                    <td colspan="3"></td>
                </tr>
            </table>
        </td>
    </tr>
</core_rt:forEach>
