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
package org.richfaces.tests.metamer.ftest.richProgressBar;

import static org.jboss.arquillian.ajocado.utils.URLUtils.buildUrl;
import static org.richfaces.tests.metamer.ftest.webdriver.AttributeList.progressBarAttributes;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.arquillian.graphene.spi.annotations.Page;
import org.jboss.test.selenium.support.ui.ElementNotPresent;
import org.jboss.test.selenium.support.ui.ElementPresent;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.BasicAttributes;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage.WaitRequestType;
import org.testng.annotations.Test;

/**
 * Test case for page /faces/components/richProgressBar/static.xhtml
 *
 * @author <a href="https://community.jboss.org/people/ppitonak">Pavol Pitonak</a>
 * @since 4.3.0.M3
 */
public class TestProgressBarStatic extends AbstractWebDriverTest {

    @Page
    private ProgressBarPage page;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richProgressBar/static.xhtml");
    }

    @Test
    public void testInitialFacet() {
        assertTrue(ElementPresent.getInstance().element(page.progressBar).apply(driver),
            "Progress bar is not present on the page.");
        assertTrue(page.progressBar.isDisplayed(), "Progress bar should be visible on the page.");
        assertTrue(page.initialOutput.isDisplayed(), "Initial output should be present on the page.");
        assertFalse(page.finishOutput.isDisplayed(), "Finish output should not be present on the page.");
        assertEquals(page.initialOutput.getText(), "Initial", "Content of initial facet.");

        assertFalse(page.remain.isDisplayed(), "Progress bar should not show progress.");
        assertFalse(page.progress.isDisplayed(), "Progress bar should not show progress.");
        assertFalse(page.label.isDisplayed(), "Progress bar should not show progress.");

        MetamerPage.waitRequest(page.initialFacetRenderedCheckbox, WaitRequestType.HTTP).click();

        assertTrue(ElementPresent.getInstance().element(page.progressBar).apply(driver),
            "Progress bar is not present on the page.");
        assertTrue(page.progressBar.isDisplayed(), "Progress bar should be visible on the page.");
        assertTrue(ElementNotPresent.getInstance().element(page.initialOutput).apply(driver),
            "Initial output should not be present on the page.");
        assertFalse(page.finishOutput.isDisplayed(), "Finish output should not be present on the page.");

        assertTrue(ElementPresent.getInstance().element(page.remain).apply(driver),
            "Progress bar should show progress.");
        assertTrue(ElementPresent.getInstance().element(page.progress).apply(driver),
            "Progress bar should show progress.");
        assertTrue(ElementPresent.getInstance().element(page.label).apply(driver), "Progress bar should show progress.");
    }

    @Test
    public void testFinishFacet() {
        progressBarAttributes.set(ProgressBarAttributes.value, 100);

        assertTrue(ElementPresent.getInstance().element(page.progressBar).apply(driver),
            "Progress bar is not present on the page.");
        assertTrue(page.progressBar.isDisplayed(), "Progress bar should be visible on the page.");
        assertFalse(page.initialOutput.isDisplayed(), "Initial output should not be present on the page.");
        assertTrue(page.finishOutput.isDisplayed(), "Finish output should be present on the page.");
        assertEquals(page.finishOutput.getText(), "Finish", "Content of finish facet.");

        assertFalse(page.remain.isDisplayed(), "Progress bar should not show progress.");
        assertFalse(page.progress.isDisplayed(), "Progress bar should not show progress.");
        assertFalse(page.label.isDisplayed(), "Progress bar should not show progress.");

        MetamerPage.waitRequest(page.finishFacetRenderedCheckbox, WaitRequestType.HTTP).click();

        assertTrue(ElementPresent.getInstance().element(page.progressBar).apply(driver),
            "Progress bar is not present on the page.");
        assertTrue(page.progressBar.isDisplayed(), "Progress bar should be visible on the page.");
        assertFalse(page.initialOutput.isDisplayed(), "Initial output should not be present on the page.");
        assertFalse(ElementPresent.getInstance().element(page.finishOutput).apply(driver),
            "Finish output should not be present on the page.");

        assertTrue(page.remain.isDisplayed(), "Progress bar should show progress.");
        assertTrue(page.progress.isDisplayed(), "Progress bar should show progress.");
        assertFalse(page.label.isDisplayed(), "Progress bar should not show label.");
    }

    @Test
    public void testFinishClass() {
        testStyleClass(page.finish, BasicAttributes.finishClass);
    }

    @Test
    public void testInitialClass() {
        testStyleClass(page.init, BasicAttributes.initialClass);
    }

    @Test
    public void testLabel() {
        MetamerPage.waitRequest(page.initialFacetRenderedCheckbox, WaitRequestType.HTTP).click();

        assertEquals(page.label.getText(), "", "Label when not set.");

        progressBarAttributes.set(ProgressBarAttributes.label, "metamer");
        assertEquals(page.label.getText(), "metamer", "Label when set to metamer.");

        MetamerPage.waitRequest(page.childrenRenderedCheckbox, WaitRequestType.HTTP).click();
        assertEquals(page.label.getText(), "child + metamer",
            "Label when set to metamer and children are rendered too.");
    }

    @Test
    public void testMaxValue() {
        progressBarAttributes.set(ProgressBarAttributes.maxValue, 1000);
        progressBarAttributes.set(ProgressBarAttributes.value, 100);
        assertEquals(getProgress(), 10, "Progress when value=100 and maxValue=1000.");
    }

    @Test
    public void testMinValue() {
        progressBarAttributes.set(ProgressBarAttributes.minValue, 90);
        progressBarAttributes.set(ProgressBarAttributes.value, 95);
        assertEquals(getProgress(), 50, "Progress when value=95 and minValue=90.");
    }

    @Test
    public void testProgressClass() {
        testStyleClass(page.progress, BasicAttributes.progressClass);
    }

    @Test
    public void testRemainingClass() {
        testStyleClass(page.remain, BasicAttributes.remainingClass);
    }

    @Test
    public void testStyle() {
        testStyle(page.progressBar);
    }

    @Test
    public void testStyleClass() {
        testStyleClass(page.progressBar);
    }

    @Test
    public void testValue() {
        progressBarAttributes.set(ProgressBarAttributes.value, 0);
        assertEquals(getProgress(), 0, "Progress when value=0.");

        progressBarAttributes.set(ProgressBarAttributes.value, 37);
        assertEquals(getProgress(), 37, "Progress when value=37.");

        progressBarAttributes.set(ProgressBarAttributes.value, 100);
        assertEquals(getProgress(), 100, "Progress when value=100.");

        progressBarAttributes.set(ProgressBarAttributes.value, -345);
        assertEquals(getProgress(), 0, "Progress when value=-345.");
        assertTrue(page.initialOutput.isDisplayed(), "Initial output should be visible on the page.");
        assertFalse(page.finishOutput.isDisplayed(), "Finish output should not be visible on the page.");

        progressBarAttributes.set(ProgressBarAttributes.value, 456);
        assertEquals(getProgress(), 100, "Progress when value=456.");
        assertFalse(page.initialOutput.isDisplayed(), "Initial output should not be visible on the page.");
        assertTrue(page.finishOutput.isDisplayed(), "Finish output should be visible on the page.");
    }

    /**
     * @return progress size in %
     */
    private int getProgress() {
        String width = page.progress.getCssValue("width");
        if (width.contains("%")) {
            return Integer.parseInt(width.replace("%", ""));
        } else {
            return Integer.parseInt(width.replace("px", "")) / 2; // progress bar width is 200px
        }
    }
}
