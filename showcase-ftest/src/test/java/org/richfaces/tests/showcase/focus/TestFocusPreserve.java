/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2012, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *******************************************************************************/
package org.richfaces.tests.showcase.focus;

import static org.jboss.arquillian.graphene.Graphene.guardXhr;
import static org.testng.Assert.assertEquals;

import org.jboss.arquillian.ajocado.utils.URLUtils;
import org.richfaces.tests.showcase.AbstractWebDriverTest;
import org.richfaces.tests.showcase.focus.page.FocusPage;
import org.richfaces.tests.showcase.focus.page.FocusPreservePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 * @version $Revision$
 */
public class TestFocusPreserve extends AbstractWebDriverTest<FocusPreservePage> {

    private static final String EXPECTED_OUTCOME = "RichFaces";

    // workaround till the sample will not be renamed to comply camel case convention
    @Override
    @BeforeMethod
    public void loadPage() {

        this.contextRoot = getContextRoot();

        webDriver.get(URLUtils.buildUrl(contextRoot, "/showcase/",
            "richfaces/component-sample.jsf?demo=focus&sample=focus-preserve&skin=blueSky").toExternalForm());
    }

    @Test
    public void testFocusIsPreserved() {
        page.waitTillFirstInputIsFocused();

        FocusPage.typeSomethingAndDoNotCareAboutFocus(EXPECTED_OUTCOME);

        guardXhr(page.button).click();

        page.waitTillFirstInputIsFocused();

        String actual = page.output.getText();
        assertEquals(actual, EXPECTED_OUTCOME, "The output was not updated correctly after form submission!");
    }
}
