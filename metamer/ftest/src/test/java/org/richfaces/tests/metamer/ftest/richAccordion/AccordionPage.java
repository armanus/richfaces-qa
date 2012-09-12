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
package org.richfaces.tests.metamer.ftest.richAccordion;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;

/**
 * @author <a href="https://community.jboss.org/people/ppitonak">Pavol Pitonak</a>
 * @since 4.3.0.M2
 */
public class AccordionPage extends MetamerPage {

    @FindBy(css = "div[id$=accordion]")
    WebElement accordion;

    @FindBy(css = "div.rf-ac-itm-hdr[id$=header]")
    List<WebElement> headers;
    @FindBy(css = "div.rf-ac-itm-lbl-act")
    List<WebElement> activeHeaders;
    @FindBy(css = "div.rf-ac-itm-lbl-dis")
    List<WebElement> disabledHeaders;
    @FindBy(css = "div.rf-ac-itm-lbl-inact")
    List<WebElement> inactiveHeaders;

    @FindBy(css = "div.rf-ac-itm-cnt")
    List<WebElement> itemContents;

    @FindBy(css = "div.rf-ac-itm td.rf-ac-itm-ico")
    List<WebElement> leftIcons;
    @FindBy(css = "div.rf-ac-itm td.rf-ac-itm-exp-ico")
    List<WebElement> rightIcons;

}
