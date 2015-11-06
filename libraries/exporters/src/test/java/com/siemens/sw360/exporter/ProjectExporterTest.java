package com.siemens.sw360.exporter;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by heydenrb on 06.11.15.
 */
public class ProjectExporterTest {

    @Test
    public void testEveryRenderedFieldHasAHeader() throws Exception {
        assertThat(ProjectExporter.RENDERED_FIELDS.size(), is(ProjectExporter.HEADERS.size()));

    }
}