/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.richfaces.tests.metamer.ftest.richAutocomplete;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for rich:autocomplete component page: faces/components/richAutocomplete/autocomplete.xhtml
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision$
 */
public class SimpleAutocompletePage {

    @FindBy(css="span[id$=autocomplete]")
    public WebElement autocomplete;

    @FindBy(css="input[id$=autocompleteInput]" )
    public WebElement autocompleteInput;

    @FindBy(css="div[id$=autocompleteItems]")
    public WebElement autocompleteItems;

    @FindBy(className="rf-au-itm")
    public WebElement autocompleteItem;

    @FindBy(className="rf-au-opt-sel")
    public WebElement autocompleteSelectedItem;
}
