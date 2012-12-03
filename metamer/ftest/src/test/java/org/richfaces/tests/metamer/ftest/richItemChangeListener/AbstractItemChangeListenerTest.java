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
package org.richfaces.tests.metamer.ftest.richItemChangeListener;

import static org.jboss.arquillian.ajocado.utils.URLUtils.buildUrl;

import java.net.URL;
import javax.faces.event.PhaseId;

import org.jboss.arquillian.graphene.spi.annotations.Page;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage.WaitRequestType;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public abstract class AbstractItemChangeListenerTest<P extends ICLPage> extends AbstractWebDriverTest {

    @Page
    protected P page;

    private final String testedComponent;

    public AbstractItemChangeListenerTest(String testedComponent) {
        this.testedComponent = testedComponent;
    }

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richItemChangeListener/" + testedComponent + ".xhtml");
    }

    private void testICL(final String expectedText) {
        MetamerPage.waitRequest(page.getInactivePanel(), WaitRequestType.XHR).click();
        page.assertListener(PhaseId.UPDATE_MODEL_VALUES, expectedText);
    }

    public void testICLAsAttributeOfComponent(String expectedMSG) {
        testICL(expectedMSG);
    }

    public void testICLInComponentWithType(String expectedMSG) {
        testICL(expectedMSG);
    }

    public void testICLInComponentWithListener(String expectedMSG) {
        testICL(expectedMSG);
    }

    public void testICLAsForAttributeWithType(String expectedMSG) {
        testICL(expectedMSG);
    }
}
