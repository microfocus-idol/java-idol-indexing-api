/*
 * $Id$
 *
 * Copyright (c) 2010, Autonomy Systems Ltd.
 *
 * DreReplaceCommandTest.java
 * Created on 05-Jan-2010
 * Last modified by $Author$ on $Date$ 
 */
package com.autonomy.nonaci.indexing.impl;

import com.autonomy.nonaci.indexing.IndexCommand;
import java.util.LinkedHashMap;
import java.util.Map;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This test class is just to check that a sub class of IndexCommandImpl works if changes to that class have taken
 * place...
 *
 * @author boba
 * @version $Revision$ $Date$
 */
public class DreReplaceCommandTest {

    private static String queryString;

    private DreReplaceCommand command;

    @BeforeClass
    public static void createQueryString() {
        final StringBuilder buffer = new StringBuilder(512);
        buffer.append(IndexCommand.PARAM_DATABASE_MATCH).append("=test&");
        buffer.append(IndexCommand.PARAM_MULTIPLE_VALUES).append("=true&");
        buffer.append(IndexCommand.PARAM_REPLACE_ALL_REFS).append("=true");

        queryString = buffer.toString();
    }


    @Before
    public void createCommand() {
        // The actual command doesn't really matter...
        command = new DreReplaceCommand();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testParameterMapMethods() {
        // Parameters should be empty...
        assertThat("parameters", command.getParameters().isEmpty(), (Matcher<Boolean>) is(true));

        // Set some parameters...
        final Map<String, String> parameters = new LinkedHashMap<String, String>();
        parameters.put(IndexCommand.PARAM_DATABASE_MATCH, "test");
        parameters.put(IndexCommand.PARAM_MULTIPLE_VALUES, "true");
        parameters.put(IndexCommand.PARAM_REPLACE_ALL_REFS, "true");
        command.setParameters(parameters);

        // Parameters should no longer be empty and the retruned map should be a different instance...
        assertThat("parameters", command.getParameters().size(), (Matcher<Integer>) is(3));
        assertThat("parameters", command.getParameters(), is(not(sameInstance(parameters))));
        assertThat("queryString", command.getQueryString(), (Matcher<String>) is(equalTo(queryString)));

        // I should be able to modify our copy of the map and the command's copy shouldn't change...
        parameters.put(IndexCommand.PARAM_BLOCKING, "true");
        assertThat("parameters", command.getParameters().size(), (Matcher<Integer>) is(3));
        assertThat("queryString", command.getQueryString(), (Matcher<String>) is(equalTo(queryString)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testParameterMethods() {
        // Parameters should be empty...
        assertThat("parameters", command.getParameters().isEmpty(), (Matcher<Boolean>) is(true));

        // Set some parameters...
        command.setDatabaseMatch("test");
        command.setMultipleValues(true);
        command.setReplaceAllRefs(true);

        // Parameters should no longer be empty...
        assertThat("parameters", command.getParameters().size(), (Matcher<Integer>) is(3));
        assertThat("queryString", command.getQueryString(), (Matcher<String>) is(equalTo(queryString)));
    }

}