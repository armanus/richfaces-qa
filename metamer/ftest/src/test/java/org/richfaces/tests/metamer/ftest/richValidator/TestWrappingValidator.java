/*******************************************************************************
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
 *******************************************************************************/
package org.richfaces.tests.metamer.ftest.richValidator;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;
import static org.richfaces.tests.metamer.ftest.webdriver.AttributeList.validatorAttributes;

import java.net.URL;

import org.jboss.arquillian.graphene.Graphene;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.richfaces.tests.metamer.ftest.annotations.Templates;
import org.testng.annotations.Test;

/**
 * Selenium tests for page faces/components/richValidator/wrapping.xhtml
 *
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision$
 */
public class TestWrappingValidator extends AbstractValidatorsTest {

    /** component page locator */
    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richValidator/wrapping.xhtml");
    }

    @Test
    public void testAllWrong() {
        verifyAllWrongWithAjaxSubmit();
    }

    @Test(groups = { "Future" })
    @IssueTracking(value = "https://issues.jboss.org/browse/RF-11035")
    public void testAllWrongJSF() {
        verifyAllWrongWithJSFSubmit();
    }

    @Test
    public void testBooleanTrue() {
        verifyBooleanTrue();
    }

    @Test
    public void testBooleanFalse() {
        verifyBooleanFalse();
    }

    @Test
    public void testDecimalMinMax() {
        verifyDecimalMinMax();
    }

    @Test
    public void testIntegerMax() {
        verifyMax();
    }

    @Test
    public void testIntegerMin() {
        verifyMin();
    }

    @Test
    public void testIntegerMinMax() {
        verifyMinMax();
    }

    @Test
    public void testTextNotEmpty() {
        verifyNotEmpty();
    }

    @Test
    public void testTextNotNull() {
        verifyNotNull();
    }

    @Test
    public void testTextPattern() {
        verifyPattern();
    }

    @Test
    public void testTextCustomPattern() {
        verifyCustom();
    }

    @Test
    public void testDatePast() {
        verifyDatePast();
    }

    @Test
    public void testDateFuture() {
        verifyDateFuture();
    }

    @Test
    public void testStringSize() {
        verifyStringSize();
    }

    /**
     * Test of attribute @disabled. Testing of client side validation on one input field.
     */
    @Test
    @RegressionTest(value = "https://issues.jboss.org/browse/RF-12154")
    @Templates(exclude = { "richPopupPanel" })
    public void testDisabled() {

        String invalidValue = "11";
        // set disabled to false
        validatorAttributes.set(ValidatorAttributes.disabled, Boolean.FALSE);
        // put in an invalid value
        page.inputMax.clear();
        Graphene.guardNoRequest(page.inputMax).sendKeys(invalidValue);
        page.a4jCommandBtn.click();
        // check error message from validator
        assertPresent(page.msgMax, "Element page.msgMax should be present!");

        // set disabled to true
        validatorAttributes.set(ValidatorAttributes.disabled, Boolean.TRUE);
        // put in an invalid value
        Graphene.guardNoRequest(page.inputMax).sendKeys(invalidValue);
        page.a4jCommandBtn.click();
        // check error message from validator
        assertNotPresent(page.msgMax, "Element page.msgMax should be present!");
    }

    @Test(groups = { "Future" })
    @IssueTracking(value = "https://issues.jboss.org/browse/RF-11035")
    public void testSelectionSize() {
        verifySelectionSize();
    }

}
