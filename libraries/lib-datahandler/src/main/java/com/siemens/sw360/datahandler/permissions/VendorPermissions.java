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
package com.siemens.sw360.datahandler.permissions;


import com.siemens.sw360.datahandler.thrift.users.RequestedAction;
import com.siemens.sw360.datahandler.thrift.users.User;
import com.siemens.sw360.datahandler.thrift.vendors.Vendor;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class VendorPermissions extends DocumentPermissions<Vendor> {
    private final Set<String> moderators;
    private final Set<String> contributors;

    protected VendorPermissions(Vendor document, User user) {
        super(document, user);

        moderators = Collections.emptySet();
        contributors = Collections.emptySet();

    }

    @Override
    public void fillPermissions(Vendor other, Map<RequestedAction, Boolean> permissions) {
    }

    @Override
    public boolean isActionAllowed(RequestedAction action) {
        return getStandardPermissions(action);
    }

    @Override
    protected Set<String> getContributors() {
        return contributors;
    }

    @Override
    protected Set<String> getModerators() {
        return moderators;
    }
}
