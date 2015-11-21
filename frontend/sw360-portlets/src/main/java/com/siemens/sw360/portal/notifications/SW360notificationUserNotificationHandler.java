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
package com.siemens.sw360.portal.notifications;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import org.apache.log4j.Logger;

/**
 * For notifications/requests in the liferay-dockbar
 *
 * @author birgit.heydenreich@tngtech.com
 */

public class SW360notificationUserNotificationHandler extends
            BaseUserNotificationHandler {

        public static final String PORTLET_ID = "components_WAR_sw360portlet";
        private static final Logger log = Logger.getLogger(SW360notificationUserNotificationHandler.class);

        public SW360notificationUserNotificationHandler() {
            log.error("in SW360notificationUserNotificationHandler  - Constructor");
            setPortletId(SW360notificationUserNotificationHandler.PORTLET_ID);
        }

        @Override
        protected String getBody(UserNotificationEvent userNotificationEvent,
                                 ServiceContext serviceContext) throws Exception {
            log.info("in getBody");
            /*JSONObject jsonObject = JSONFactoryUtil
                    .createJSONObject(userNotificationEvent.getPayload());

            long yourCustomEntityId = jsonObject
                    .getLong("yourCustomEntityId");

            String title = "<strong>Example notification for entity ID "
                    + yourCustomEntityId
                    + "</strong>";

            String bodyText = "Some other text.";

            LiferayPortletResponse liferayPortletResponse = serviceContext
                    .getLiferayPortletResponse();

            PortletURL confirmURL = liferayPortletResponse.createActionURL(SW360notificationUserNotificationHandler.PORTLET_ID);

            confirmURL.setParameter(ActionRequest.ACTION_NAME, "doSomethingGood");
            confirmURL.setParameter("redirect", serviceContext.getLayoutFullURL());
            confirmURL.setParameter("yourCustomEntityId", String.valueOf(yourCustomEntityId));
            confirmURL.setParameter("userNotificationEventId", String.valueOf(userNotificationEvent.getUserNotificationEventId()));
            confirmURL.setWindowState(WindowState.NORMAL);

            PortletURL ignoreURL = liferayPortletResponse.createActionURL(SW360notificationUserNotificationHandler.PORTLET_ID);
            ignoreURL.setParameter(ActionRequest.ACTION_NAME, "cancelForExample");
            ignoreURL.setParameter("redirect", serviceContext.getLayoutFullURL());
            ignoreURL.setParameter("yourCustomEntityId", String.valueOf(yourCustomEntityId));
            ignoreURL.setParameter("userNotificationEventId", String.valueOf(userNotificationEvent.getUserNotificationEventId()));
            ignoreURL.setWindowState(WindowState.NORMAL);

            String body = StringUtil.replace(getBodyTemplate(), new String[] {
                    "[$CONFIRM$]", "[$CONFIRM_URL$]", "[$IGNORE$]",
                    "[$IGNORE_URL$]", "[$TITLE$]", "[$BODY_TEXT$]" }, new String[] {
                    serviceContext.translate("approve"), confirmURL.toString(),
                    serviceContext.translate("reject"), ignoreURL.toString(),
                    title, bodyText });

            return body;*/
            return "";
        }

        @Override
        protected String getLink(UserNotificationEvent userNotificationEvent,
                                 ServiceContext serviceContext) throws Exception {
            log.info("in getLink");
            /*JSONObject jsonObject = JSONFactoryUtil
                    .createJSONObject(userNotificationEvent.getPayload());

            long yourCustomEntityId = jsonObject
                    .getLong("yourCustomEntityId");

            LiferayPortletResponse liferayPortletResponse = serviceContext
                    .getLiferayPortletResponse();

            PortletURL viewURL = liferayPortletResponse.createActionURL(SW360notificationUserNotificationHandler.PORTLET_ID);
            viewURL.setParameter(ActionRequest.ACTION_NAME, "showDetails");
            viewURL.setParameter("redirect", serviceContext.getLayoutFullURL());
            viewURL.setParameter("yourCustomEntityId", String.valueOf(yourCustomEntityId));
            viewURL.setParameter("userNotificationEventId", String.valueOf(userNotificationEvent.getUserNotificationEventId()));
            viewURL.setWindowState(WindowState.NORMAL);

            return viewURL.toString();*/
            return "";
        }

        protected String getBodyTemplate() throws Exception {
            log.info("in getBodyTemplate");
            /*StringBundler sb = new StringBundler(5);
            sb.append("<div class=\"title\">[$TITLE$]</div><div ");
            sb.append("class=\"body\">[$BODY_TEXT$]<a class=\"btn btn-action ");
            sb.append("btn-success\" href=\"[$CONFIRM_URL$]\">[$CONFIRM$]</a>");
            sb.append("<a class=\"btn btn-action btn-warning\" href=\"");
            sb.append("[$IGNORE_URL$]\">[$IGNORE$]</a></div>");
            return sb.toString();*/
            return "";
        }

    }



