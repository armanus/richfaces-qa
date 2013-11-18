package org.richfaces.tests.showcase.contextMenu.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.common.Event;
import org.richfaces.fragment.contextMenu.RichFacesContextMenu;

public class SimpleContextMenuPage {

    @FindBy(css = "img[id$='pic']")
    private WebElement picture;

    @FindBy(className = "rf-ctx-lbl")
    private RichFacesContextMenu contextMenu;

    public WebElement getPicture() {
        return picture;
    }

    public RichFacesContextMenu getContextMenu() {
        contextMenu.advanced().setupShowEvent(Event.CLICK);
        contextMenu.advanced().setupTargetFromWidget();
        return contextMenu;
    }
}
