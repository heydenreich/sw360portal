/*
 * Copyright Siemens AG, 2015. Part of the SW360 Portal Project.
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
package com.siemens.sw360.fossology;

import com.siemens.sw360.datahandler.TestUtils;
import com.siemens.sw360.datahandler.thrift.RequestStatus;
import com.siemens.sw360.datahandler.thrift.components.Release;
import com.siemens.sw360.datahandler.thrift.fossology.FossologyHostFingerPrint;
import com.siemens.sw360.datahandler.thrift.users.User;
import com.siemens.sw360.fossology.config.FossologySettings;
import com.siemens.sw360.fossology.handler.FossologyFileHandler;
import com.siemens.sw360.fossology.handler.FossologyHostKeyHandler;
import com.siemens.sw360.fossology.handler.FossologyScriptsHandler;
import com.siemens.sw360.fossology.ssh.FossologySshConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FossologyHandlerTest {

    FossologyHandler fossologyHandler;

    @Mock
    FossologyFileHandler fossologyFileHandler;
    @Mock
    FossologyHostKeyHandler fossologyHostKeyHandler;
    @Mock
    FossologySshConnector fossologySshConnector;
    @Mock
    FossologyScriptsHandler fossologyScriptsHandler;
    @Mock
    FossologySettings fossologySettings;

    private String pubkey = "pubkey";
    private User user;

    @Before
    public void setUp() {
        when(fossologySettings.getFossologyPublicKey()).thenReturn(pubkey.getBytes());
        fossologyHandler = new FossologyHandler(fossologyFileHandler, fossologyHostKeyHandler, fossologySshConnector, fossologyScriptsHandler, fossologySettings);
        user = TestUtils.getAdminUser(getClass());
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(fossologyFileHandler, fossologyHostKeyHandler, fossologySshConnector, fossologyScriptsHandler);
    }

    @Test
    public void testSendToFossology() throws Exception {
        final String releaseId = "a";
        final String clearingTeam = "b";

        final RequestStatus requestStatus = RequestStatus.FAILURE;

        doReturn(requestStatus).when(fossologyFileHandler).sendToFossology(releaseId, user,clearingTeam);

        assertThat(fossologyHandler.sendToFossology(releaseId, user, clearingTeam), sameInstance(requestStatus));

        verify(fossologyFileHandler).sendToFossology(releaseId, user, clearingTeam);
    }

    @Test
    public void testGetStatusInFossology() throws Exception {
        final String releaseId = "a";
        final String clearingTeam = "b";

        final Release status = mock(Release.class);

        doReturn(status).when(fossologyFileHandler).getStatusInFossology(releaseId, user, clearingTeam);

        assertThat(fossologyHandler.getStatusInFossology(releaseId, user, clearingTeam), sameInstance(status));

        verify(fossologyFileHandler).getStatusInFossology(releaseId, user, clearingTeam);
    }


    @Test
    public void testGetFingerPrints() throws Exception {
        final List status = mock(List.class);

        doReturn(status).when(fossologyHostKeyHandler).getFingerPrints();

        assertThat(fossologyHandler.getFingerPrints(), sameInstance(status));

        verify(fossologyHostKeyHandler).getFingerPrints();
    }

    @Test
    public void testSetFingerPrints() throws Exception {
        @SuppressWarnings("unchecked")
        final List<FossologyHostFingerPrint> newFingerPrints = mock(List.class);
        final RequestStatus requestStatus = RequestStatus.FAILURE;

        doReturn(requestStatus).when(fossologyHostKeyHandler).setFingerPrints(newFingerPrints);

        assertThat(fossologyHandler.setFingerPrints(newFingerPrints), sameInstance(requestStatus));

        verify(fossologyHostKeyHandler).setFingerPrints(newFingerPrints);
    }

    @Test
    public void testDeployScripts() throws Exception {
        final RequestStatus requestStatus = RequestStatus.FAILURE;

        doReturn(requestStatus).when(fossologyScriptsHandler).deployScripts();

        assertThat(fossologyHandler.deployScripts(), sameInstance(requestStatus));

        verify(fossologyScriptsHandler).deployScripts();
    }

    @Test
    public void testCheckConnection() throws Exception {
        final RequestStatus requestStatus = RequestStatus.SUCCESS;

        doReturn(2).when(fossologySshConnector).runInFossologyViaSsh(anyString());
        assertThat(fossologyHandler.checkConnection(), is(requestStatus));

        verify(fossologySshConnector).runInFossologyViaSsh(anyString());
    }

    @Test
    public void testCheckConnectionFailure() throws Exception {
        final RequestStatus requestStatus = RequestStatus.FAILURE;

        doReturn(127).when(fossologySshConnector).runInFossologyViaSsh(anyString());
        assertThat(fossologyHandler.checkConnection(), is(requestStatus));

        verify(fossologySshConnector).runInFossologyViaSsh(anyString());
    }

    @Test
    public void testGetPublicKey() throws Exception {
        assertThat(fossologyHandler.getPublicKey(), is(pubkey));
    }
}
