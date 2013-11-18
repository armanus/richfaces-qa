/**
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2013, Red Hat, Inc. and individual contributors
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
 */
package org.richfaces.tests.metamer.ftest.richPlaceholder;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.arquillian.graphene.page.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.message.RichFacesMessage;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;
import org.testng.annotations.Test;

/**
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestPlaceholderCSV extends AbstractWebDriverTest {

    @Page
    private MetamerPage page;
    @FindBy(css = "[id$=input]")
    private WebElement input;
    @FindBy(css = "[id$=msg]")
    private RichFacesMessage msg;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richPlaceholder/csv.xhtml");
    }

    private void blur() {
        page.getRequestTimeElement().click();
    }

    private void typeTextAndBlur(String text) {
        input.click();
        input.clear();
        input.sendKeys(text);
        blur();
    }

    @Test
    public void testValidation() {
        String goodText = "1234";
        String longText = "1234567";
        assertEquals(input.getAttribute("value"), AbstractPlaceholderJSFTest.DEFAULT_PLACEHOLDER_TEXT);
        assertFalse(msg.advanced().isVisible(), "Validation message should not be visible");

        typeTextAndBlur(goodText);
        assertEquals(input.getAttribute("value"), goodText);
        assertFalse(msg.advanced().isVisible(), "Validation message should not be visible");

        typeTextAndBlur("");
        msg.advanced().waitUntilMessageIsVisible().perform();
        assertEquals(input.getAttribute("value"), AbstractPlaceholderJSFTest.DEFAULT_PLACEHOLDER_TEXT);
        assertTrue(msg.advanced().isVisible(), "Validation message should  be visible");

        typeTextAndBlur(goodText);
        msg.advanced().waitUntilMessageIsNotVisible().perform();

        typeTextAndBlur(longText);
        msg.advanced().waitUntilMessageIsVisible().perform();
        assertEquals(input.getAttribute("value"), longText);
        assertTrue(msg.advanced().isVisible(), "Validation message should  be visible");
    }
}
