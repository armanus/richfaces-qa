/**
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and individual contributors
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

import static org.jboss.arquillian.ajocado.utils.URLUtils.buildUrl;
import static org.richfaces.tests.metamer.ftest.webdriver.AttributeList.placeholderAttributes;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Color;
import java.net.URL;
import org.jboss.arquillian.ajocado.utils.ColorUtils;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.spi.annotations.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage.WaitRequestType;

/**
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
//FIXME should not be generic
public abstract class AbstractPlaceholderTest extends AbstractWebDriverTest {

    public static final String INPUT1_ID = "[id$=input1]";
    public static final String INPUT2_ID = "[id$=input2]";
    public static final String DEFAULT_PLACEHOLDER_TEXT = "Watermark text";
    public static final String DEFAULT_PLACEHOLDER_CLASS = "rf-plhdr";
    //
    private final String componentName;
    //
    @FindBy(css = "[id$=placeholder]")
    WebElement placeholder;
    @FindBy(css = "[id$=a4jButton]")
    WebElement a4jSubmitBtn;
    @FindBy(css = "[id$=hButton]")
    WebElement hSubmitBtn;
    @FindBy(css = "[id$=output1]")
    WebElement output1;
    @FindBy(css = "[id$=output2]")
    WebElement output2;
    @Page
    MetamerPage page;

    public AbstractPlaceholderTest(String componentName) {
        this.componentName = componentName;
    }

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richPlaceholder/" + componentName + ".xhtml");
    }

    abstract WebElement getInput1();

    abstract WebElement getInput2();

    protected String getOutput1Value() {
        return output1.getText();
    }

    protected String getOutput2Value() {
        return output2.getText();
    }

    protected String getInput1Value() {
        return getInput1().getAttribute("value");
    }

    protected String getInput2Value() {
        return getInput2().getAttribute("value");
    }

    protected String getInput1StyleClass() {
        return getInput1().getAttribute("class");
    }

    protected String getInput2StyleClass() {
        return getInput2().getAttribute("class");
    }

    protected void sendKeysToInput1(String keys) {
        getInput1().sendKeys(keys);
    }

    protected void clearInput1() {
        getInput1().clear();
    }

    protected void clickOnInput2() {
        getInput2().click();
    }

    protected void clickOnInput1() {
        getInput1().click();
    }

    protected String getInput1Color() {
        return getInput1().getCssValue("color");
    }

    public void testAjaxSubmit() {
        assertEquals(getInput1Value(), DEFAULT_PLACEHOLDER_TEXT, "Input 1 value");
        assertEquals(getInput2Value(), "", "Input 2 value");
        MetamerPage.waitRequest(a4jSubmitBtn, WaitRequestType.XHR).click();
        assertEquals(getOutput1Value(), "", "Output 1 value");
        assertEquals(getOutput2Value(), "", "Output 2 value");
    }

    public void testHTTPSubmit() {
        assertEquals(getInput1Value(), DEFAULT_PLACEHOLDER_TEXT, "Input 1 value");
        assertEquals(getInput2Value(), "", "Input 2 value");
        MetamerPage.waitRequest(hSubmitBtn, WaitRequestType.HTTP).click();
        assertEquals(getOutput1Value(), "", "Output 1 value");
        assertEquals(getOutput2Value(), "", "Output 2 value");
    }

    public void testClickOnInputWithPlaceholder() {
        assertEquals(getInput1Value(), DEFAULT_PLACEHOLDER_TEXT, "Input 1 value");
        clickOnInput1();
        assertEquals(getInput1Value(), "", "Input 1 value");
    }

    public void testDeleteTextFromInputWithPlaceholder() {
        assertEquals(getInput1Value(), DEFAULT_PLACEHOLDER_TEXT, "Input 1 value");
        assertTrue(getInput1StyleClass().contains(DEFAULT_PLACEHOLDER_CLASS), "Input 1 styleClass");

        sendKeysToInput1("abcd");
        assertEquals(getInput1Value(), "abcd", "Input 1 value");
        assertFalse(getInput1StyleClass().contains(DEFAULT_PLACEHOLDER_CLASS), "Input 1 styleClass");
        Color textColor = ColorUtils.convertToAWTColor(getInput1Color());
        assertEquals(textColor, Color.black, "Input 1 text color");

        clearInput1();
        clickOnInput2();

        assertEquals(getInput1Value(), DEFAULT_PLACEHOLDER_TEXT, "Input 1 value");
        assertTrue(getInput1StyleClass().contains(DEFAULT_PLACEHOLDER_CLASS), "Input 1 styleClass");
        textColor = ColorUtils.convertToAWTColor(getInput1Color());
        assertEquals(textColor, Color.blue, "Input 1 text color");
    }

    public void testRendered() {
        placeholderAttributes.set(PlaceholderAttributes.rendered, Boolean.FALSE);
        assertFalse(Graphene.element(placeholder).isVisible().apply(driver), "Placeholder should not be visible");
        assertFalse(getInput1StyleClass().contains(DEFAULT_PLACEHOLDER_CLASS), "Input 1 styleClass");
        assertEquals(getInput1Value(), "", "Input 1 value");
    }

    public void testSelector() {
        //default selector = input1
        assertEquals(getInput1Value(), DEFAULT_PLACEHOLDER_TEXT, "Input 1 value");
        assertEquals(getInput2Value(), "", "Input 2 value");
        placeholderAttributes.set(PlaceholderAttributes.selector, INPUT2_ID);
        assertEquals(getInput1Value(), "", "Input 1 value");
        assertEquals(getInput2Value(), DEFAULT_PLACEHOLDER_TEXT, "Input 2 value");
    }

    public void testSelectorEmpty() {
        //Placeholder is in first component, without selector, it should work for the first input
        placeholderAttributes.set(PlaceholderAttributes.selector, "");
        assertEquals(getInput1Value(), DEFAULT_PLACEHOLDER_TEXT, "Input 1 value");
        assertEquals(getInput2Value(), "", "Input 2 value");
    }

    public void testStyleClass() {
        assertEquals(getInput1Value(), DEFAULT_PLACEHOLDER_TEXT, "Input 1 value");
        testStyleClass(getInput1());
    }

    public void testTypeToInputWithPlaceholder() {
        assertEquals(getInput1Value(), DEFAULT_PLACEHOLDER_TEXT, "Input 1 value");
        assertTrue(getInput1StyleClass().contains(DEFAULT_PLACEHOLDER_CLASS), "Input 1 styleClass");
        Color textColor = ColorUtils.convertToAWTColor(getInput1Color());
        assertEquals(textColor, Color.blue);

        sendKeysToInput1("abcd");

        assertEquals(getInput1Value(), "abcd", "Input 1 value");
        assertFalse(getInput1StyleClass().contains(DEFAULT_PLACEHOLDER_CLASS), "Input 1 styleClass");
        textColor = ColorUtils.convertToAWTColor(getInput1Color());
        assertEquals(textColor, Color.BLACK);
    }
}
