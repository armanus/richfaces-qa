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
package org.richfaces.tests.metamer.ftest.richTabPanel;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;
import org.richfaces.fragment.tabPanel.RichFacesTabPanel;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;

public class TabPanelSimplePage extends MetamerPage {

    @FindByJQuery("div[id*=tabPanel]")
    private RichFacesTabPanel panelTab;

    @FindByJQuery("div[id*=tabPanel]")
    private WebElement panelTabAsWebElement;

    @FindByJQuery("input[id$=hCreateTabButton]")
    private WebElement createTabHButton;

    @FindByJQuery("input[id$=a4jCreateTabButton]")
    private WebElement createTabA4jButton;

    public WebElement getCreateTabButtonA4j() {
        return createTabA4jButton;
    }

    public WebElement getCreateTabButtonHButton() {
        return createTabHButton;
    }

    public RichFacesTabPanel getPanelTab() {
        return panelTab;
    }

    /**
     * Return RichFacesTabPanel as WebElement to be used for visibility assertion in tests.
     *
     * @return WebElement
     */
    public WebElement getPanelTabAsWebElement() {
        return panelTabAsWebElement;
    }

}
