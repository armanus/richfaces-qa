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
package org.richfaces.tests.page.fragments.impl.list;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.graphene.spi.annotations.Root;
import org.jodah.typetools.TypeResolver;
import org.openqa.selenium.WebElement;
import org.richfaces.tests.page.fragments.impl.utils.picker.ChoicePicker;
import org.richfaces.tests.page.fragments.impl.utils.picker.ChoicePickerHelper;
import org.richfaces.tests.page.fragments.impl.utils.picker.MultipleChoicePicker;

/**
 * Base for ListComponents implementations.
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 * @param <T>
 */
public abstract class AbstractListComponent<T extends ListItem> implements ListComponent<T> {

    @Root
    private WebElement root;

    @FindBy(jquery = "> *")
    private List<WebElement> items;

    private final Class<T> listItemClass;

    public AbstractListComponent() {
        listItemClass = (Class<T>) TypeResolver.resolveRawArgument(ListComponent.class, getClass());
    }

    @Override
    public T getItem(int index) {
        return getItem(ChoicePickerHelper.byIndex().index(index));
    }

    @Override
    public T getItem(String text) {
        return getItem(ChoicePickerHelper.byVisibleText().match(text));
    }

    @Override
    public T getItem(ChoicePicker picker) {
        return instantiateItemFragment(picker.pick(getItemsElements()));
    }

    @Override
    public List<T> getItems(MultipleChoicePicker picker) {
        List<WebElement> foundItems = picker.pickMultiple(getItemsElements());
        List<T> result = Lists.newArrayList();
        for (WebElement foundItem : foundItems) {
            result.add(instantiateItemFragment(foundItem));
        }
        return result;
    }

    @Override
    public List<T> getItems() {
        return getItemsFragments();
    }

    protected List<WebElement> getItemsElements() {
        return Collections.unmodifiableList(items);
    }

    protected List<T> getItemsFragments() {
        List<T> result = Lists.newArrayList();
        for (WebElement foundItem : getItemsElements()) {
            result.add(instantiateItemFragment(foundItem));
        }
        if (result.size() == 1 && !result.get(0).getRoot().isDisplayed()) {
            // hack for RF's list.
            // when the list should be empty, there is always a hidden item.
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public WebElement getRoot() {
        return root;
    }

    private T instantiateItemFragment(WebElement item) {
        return Graphene.createPageFragment(listItemClass, item);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return getItems().size();
    }

}
