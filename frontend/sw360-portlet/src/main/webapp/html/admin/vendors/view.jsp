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

<%@include file="/html/init.jsp" %>

<%@ page import="com.siemens.sw360.portal.common.PortalConstants" %>
<%@ page import="javax.portlet.PortletRequest" %>
<%@ page import="com.liferay.portlet.PortletURLFactoryUtil" %>

<portlet:defineObjects/>
<liferay-theme:defineObjects/>

<portlet:resourceURL var="exportVendorsURL">
    <portlet:param name="<%=PortalConstants.ACTION%>" value="<%=PortalConstants.EXPORT_TO_EXCEL%>"/>
</portlet:resourceURL>


<portlet:resourceURL var="deleteAjaxURL">
    <portlet:param name="<%=PortalConstants.ACTION%>" value='<%=PortalConstants.REMOVE_VENDOR%>'/>
</portlet:resourceURL>

<portlet:renderURL var="addVendorURL">
    <portlet:param name="<%=PortalConstants.PAGENAME%>" value="<%=PortalConstants.PAGENAME_EDIT%>" />
</portlet:renderURL>


<jsp:useBean id="vendorList" type="java.util.List<com.siemens.sw360.datahandler.thrift.vendors.Vendor>"  scope="request"/>

<div id="header"></div>
<p class="pageHeader">
    <span class="pageHeaderBigSpan">Vendors</span> <span class="pageHeaderSmallSpan">(${vendorList.size()})</span>
    <span class="pull-right">
        <input type="button" class="addButton" onclick="window.location.href='<%=exportVendorsURL%>'" value="Export Vendors">
        <input type="button" class="addButton" onclick="window.location.href='<%=addVendorURL%>'" value="Add Vendor">
    </span>
</p>


<div id="searchInput" class="content1">
    <table style="width: 90%; margin-left:3%;border:1px solid #cccccc;">
        <thead>
        <tr>
            <th class="infoheading">
                Keyword Search
            </th>
        </tr>
        </thead>
        <tbody style="background-color: #f8f7f7; border: none;">
        <tr>
            <td>
                <input type="text" style="width: 90%; padding: 5px; color: gray;height:20px;"
                       id="keywordsearchinput" value="" onkeyup="useSearch('keywordsearchinput')">
                <br/>
                <input style="padding: 5px 20px 5px 20px; border: none; font-weight:bold;" type="button"
                       name="searchBtn" value="Search" onclick="useSearch('keywordsearchinput')">
            </td>
        </tr>
        </tbody>
    </table>
</div>
<div id="vendorsTableDiv" class="content2">
    <table id="vendorsTable" cellpadding="0" cellspacing="0" border="0" class="display">
        <tfoot>
        <tr>
            <th colspan="4"></th>
        </tr>
        </tfoot>
    </table>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/external/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/external/jquery.dataTables.js"></script>

<script>

    vendorIdInURL = '<%=PortalConstants.VENDOR_ID%>';
    pageName = '<%=PortalConstants.PAGENAME%>';
    pageEdit = '<%=PortalConstants.PAGENAME_EDIT%>';
    baseUrl = '<%= PortletURLFactoryUtil.create(request, portletDisplay.getId(), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>';

    var oTable;

    var PortletURL;
    AUI().use('liferay-portlet-url', function (A) {
        PortletURL = Liferay.PortletURL;
        createVendorsTable()
    });

    function createUrl_comp( paramId, paramVal) {
        var portletURL = PortletURL.createURL( baseUrl ).setParameter(pageName,pageEdit).setParameter(paramId,paramVal);
        return portletURL.toString();
    }

    function createDetailURLfromVendorId (paramVal) {
        return createUrl_comp(vendorIdInURL,paramVal );
    }

    function useSearch( buttonId) {
        oTable.fnFilter( $('#'+buttonId).val());
    }

    function createVendorsTable() {
        var result = [];
        <core_rt:forEach items="${vendorList}" var="vendor">
        result.push({
            "DT_RowId": "${vendor.id}",
            "0": "<a href='"+createDetailURLfromVendorId('${vendor.id}')+"' target='_self'><sw360:out value="${vendor.fullname}"/></a>",
            "1": "<sw360:out value="${vendor.shortname}"/>",
            "2": "<sw360:out value="${vendor.url}"/>",
            "3": "<a href='"+createDetailURLfromVendorId('${vendor.id}')+"' target='_self'><img src='<%=request.getContextPath()%>/images/edit.png' alt='Edit' title='Edit'></a>"
            +"<img src='<%=request.getContextPath()%>/images/Trash.png' onclick=\"deleteVendor('${vendor.id}', '<sw360:out value="${vendor.fullname}"/>')\"  alt='Delete' title='Delete'>"
        });
        </core_rt:forEach>

        oTable = $('#vendorsTable').dataTable({
            pagingType: "full_numbers",
            data: result,
            columns: [
                { "title": "Full Name" },
                { "title": "Short Name" },
                { "title": "URL" },
                { "title": "Actions"}
            ]
        });
        $('#vendorsTable_filter').hide();
        $('#vendorsTable_first').hide();
        $('#vendorsTable_last').hide();
    }

    function deleteVendor( id, name ) {
        if(confirm("Do you want to delete vendor " + name + " ?")) {
            jQuery.ajax({
                type: 'POST',
                url: '<%=deleteAjaxURL%>',
                cache: false,
                data: {
                <portlet:namespace/>vendorId: id
                },
                success: function (data) {
                    if(data.result == 'SUCCESS')
                        oTable.fnDeleteRow($('#' + id));
                    else {
                        alert("I could not delete the vendor!");
                    }
                },
                error: function () {
                    alert("I could not delete the vendor!");
                }
            });
        }
    }
</script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/dataTable_Siemens.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/sw360.css">
