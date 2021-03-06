/*
 * Copyright (c) Bosch Software Innovations GmbH 2015.
 * Part of the SW360 Portal Project.
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
package com.bosch.osmi.sw360.bdp.datasink.thrift;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.siemens.sw360.datahandler.TestUtils;
import com.siemens.sw360.datahandler.common.DatabaseSettings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.siemens.sw360.datahandler.thrift.ThriftClients;
import com.siemens.sw360.datahandler.thrift.components.ComponentService;
import com.siemens.sw360.datahandler.thrift.users.UserService;

public class ThriftApiSimpleTest {

	private static final String url = DatabaseSettings.COUCH_DB_URL;
	private static final String dbName = DatabaseSettings.COUCH_DB_DATABASE;
	
	private ThriftApi thriftApi;

	@Before
	public void setUp() throws Exception {
		TestUtils.createDatabase(url, dbName);

		thriftApi = new ThriftApiSimple();
	}

	@After
	public void tearDown() throws Exception {
		TestUtils.deleteDatabase(url, dbName);
	}
	
	@Test
	public void testUserClient() {
		UserService.Iface userClient = thriftApi.getUserClient();
		assertThat(userClient, is(notNullValue()));
	}
	
	@Test
	public void testComponentClient() {
		ComponentService.Iface componentClient = thriftApi.getComponentClient();
		assertThat(componentClient, is(notNullValue()));
	}
	
	@Test
	public void testThriftClients() {
		ThriftClients thriftClients = thriftApi.getThriftClients();
		assertThat(thriftClients, is(notNullValue()));
	}
	
	@Test
	public void testProjectClient() {
		com.siemens.sw360.datahandler.thrift.projects.ProjectService.Iface projectClient = thriftApi.getProjectClient();
		assertThat(projectClient, is(notNullValue()));
	}
	
	@Test
	public void testVendorClient() {
		com.siemens.sw360.datahandler.thrift.vendors.VendorService.Iface vendorClient = thriftApi.getVendorClient();
		assertThat(vendorClient, is(notNullValue()));
	}

}
