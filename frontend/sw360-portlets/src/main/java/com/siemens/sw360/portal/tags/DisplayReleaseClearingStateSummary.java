
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
package com.siemens.sw360.portal.tags;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.siemens.sw360.datahandler.thrift.components.ReleaseClearingStateSummary;
import com.siemens.sw360.datahandler.thrift.users.User;
import com.siemens.sw360.portal.common.JsonHelpers;
import com.siemens.sw360.portal.common.ThriftJsonSerializer;
import com.siemens.sw360.portal.users.UserUtils;
import org.apache.taglibs.standard.tag.common.core.OutSupport;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * This wraps the ReleaseClearingStateSummary
 *
 * @author birgit.heydenreich@tngtech.com
 */
/*public class DisplayReleaseClearingStateSummary extends SimpleTagSupport {

    private DisplayReleaseClearingStateSummary releaseClearingStateSummary;

    public void setClearing(ReleaseClearingStateSummary releaseClearingStateSummary) {
        this.releaseClearingStateSummary = releaseClearingStateSummary;
    }

    public void doTag() throws JspException, IOException {
        JSONObject json = null;
        try {
            json = JsonHelpers.toJson(releaseClearingStateSummary, new ThriftJsonSerializer());
            getJspContext().getOut().print(json);
        } catch (JSONException e) {
            getJspContext().getOut().print("");
        }
    }
}*/

public class DisplayReleaseClearingStateSummary extends SimpleTagSupport {

    private String releaseClearingStateSummary;

    public void setClearing(String releaseClearingStateSummary) {
        this.releaseClearingStateSummary = releaseClearingStateSummary;
    }

    public void doTag() throws JspException, IOException {

        getJspContext().getOut().print("");

    }
}
