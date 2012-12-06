package org.richfaces.tests.page.fragments.impl.autocomplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.arquillian.graphene.Graphene;

import org.jboss.arquillian.graphene.component.object.api.autocomplete.AutocompleteComponent;
import org.jboss.arquillian.graphene.component.object.api.autocomplete.ClearType;
import org.jboss.arquillian.graphene.component.object.api.autocomplete.Suggestion;
import org.jboss.arquillian.graphene.component.object.api.autocomplete.SuggestionParser;
import org.jboss.arquillian.graphene.component.object.api.scrolling.ScrollingType;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.spi.annotations.Root;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RichFacesAutocomplete<T> implements AutocompleteComponent<T> {

    public static final String CLASS_NAME_SUGG_LIST = "rf-au-lst-cord";
    public static final String CLASS_NAME_SUGG = "rf-au-itm";
    public static final String CLASS_NAME_SUGG_SELECTED = "rf-au-itm-sel";
    public static final String CSS_INPUT = "input[type='text']";

    private static final Logger LOGGER = Logger.getLogger(AutocompleteComponent.class.getName());

    @Root
    private WebElement root;

    @FindBy(css = CSS_INPUT)
    private WebElement inputToWrite;

    private String separator = " ";
    private SuggestionParser<T> parser;
    private List<Suggestion<T>> selectedSuggestions = new ArrayList<Suggestion<T>>();

    @Override
    public boolean areSuggestionsAvailable() {
        WebElement suggList = getRightSuggestionList();
        return suggList == null ? false : suggList.isDisplayed();
    }


    @Override
    public void clear(ClearType... clearType) {
        if (clearType.length == 0) {
            inputToWrite.clear();
            return;
        }
        if (clearType.length > 1) {
            throw new IllegalArgumentException("The number of clear type method arguments should be one!");
        }

        int valueLength = inputToWrite.getAttribute("value").length();

        switch(clearType[0]) {
            case BACK_SPACE: {
                Actions builder = new Actions(GrapheneContext.getProxy());
                for (int i = 0; i < valueLength; i++) {
                    builder.sendKeys(inputToWrite, Keys.BACK_SPACE);
                }
                builder.build().perform();
                break;
            }
            case ESCAPE_SQ: {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < valueLength; i++) {
                    sb.append("\b");
                }
                inputToWrite.sendKeys(sb.toString());
                root.click();
                break;
            }
            case DELETE: {
                Actions builder = new Actions(GrapheneContext.getProxy());
                String ctrlADel = Keys.chord(Keys.CONTROL, "a", Keys.DELETE);
                builder.sendKeys(inputToWrite, ctrlADel);

                builder.build().perform();
            }
        }
    }

    @Override
    public void finish() {
        Actions builder = new Actions(GrapheneContext.getProxy());
        builder.sendKeys(Keys.chord(Keys.SPACE, Keys.BACK_SPACE));
        builder.build().perform();
        root.findElement(By.xpath("//body")).click();
        waitForSuggestionsNotAvailable(Graphene.waitGui());
    }

     @Override
    public List<Suggestion<T>> getAllSuggestions() {
        checkParser();
        if (!areSuggestionsAvailable()) {
            return null;
        }
        List<Suggestion<T>> allSugg = new ArrayList<Suggestion<T>>();
        WebElement rightSuggList = getRightSuggestionList();
        List<WebElement> suggestions = rightSuggList.findElements(By.className(CLASS_NAME_SUGG));
        for (WebElement suggestion : suggestions) {
            allSugg.add(parser.parse(suggestion));
        }
        return allSugg;
    }

    @Override
    public List<Suggestion<T>> getSelectedSuggestions() {
        return selectedSuggestions;
    }

    @Override
    public List<String> getInputValues() {
        String currentInputValue = inputToWrite.getAttribute("value");
        return currentInputValue != null ? Arrays.asList(currentInputValue.split(separator)) : Collections.EMPTY_LIST;
    }

    @Override
    public void setSeparator(String regex) {
        this.separator = regex;
    }

    @Override
    public List<Suggestion<T>> getFirstNSuggestions(int n) {
        checkParser();
        List<Suggestion<T>> firstNSuggs = new ArrayList<Suggestion<T>>();

        if (!areSuggestionsAvailable()) {
            return null;
        }

        for (int i = 1; i <= n; i++) {
            firstNSuggs.add(getNthSuggestion(i));
        }

        return firstNSuggs;
    }

    @Override
    public Suggestion<T> getFirstSuggestion() {
        List<Suggestion<T>> suggestion = getFirstNSuggestions(1);

        if (suggestion != null) {
            return suggestion.get(0);
        }

        return null;
    }

    @Override
    public Suggestion<T> getNthSuggestion(int order) {
        checkParser();

        if (!areSuggestionsAvailable()) {
            return null;
        }

        WebElement rightSuggList = getRightSuggestionList();
        WebElement nthSuggestion = rightSuggList.findElement(By.cssSelector("." + CLASS_NAME_SUGG + ":nth-of-type(" + order
            + ")"));

        return parser.parse(nthSuggestion);
    }

    @Override
    public void type(String value) {
        inputToWrite.sendKeys(value);
        try {
            waitForSuggestionsAvailable(Graphene.waitGui());
        } catch (TimeoutException e) {
            LOGGER.log(Level.WARNING, "Suggestions aren't visible after typing into the input field.", e);
        }
    }

    @Override
    public List<Suggestion<T>> typeAndReturn(String value) {
        inputToWrite.sendKeys(value);
        try {
            waitForSuggestionsAvailable(Graphene.waitGui());
        } catch (TimeoutException ex) {
            // no suggestions available
            return null;
        }

        return getAllSuggestions();
    }

    public void autocomplete() {
        inputToWrite.sendKeys("\n"); // Keys.ENTER doesn't work
    }

    @Override
    public boolean autocompleteWithSuggestion(Suggestion<T> sugg) {
        return autocomplete(sugg);
    }

    @Override
    public boolean autocompleteWithSuggestion(Suggestion<T> sugg, ScrollingType scrollingType) {
        return autocomplete(sugg, scrollingType);
    }

    @Override
    public void setSuggestionParser(SuggestionParser<T> parser) {
        this.parser = parser;
    }

    @Override
    public String getFirstInputValue() {
        List<String> inputValues = getInputValues();
        return !inputValues.isEmpty() ? inputValues.get(0) : null;
    }

    @Override
    public String getInputValue() {
        return inputToWrite.getAttribute("value");
    }

    protected boolean autocomplete(Suggestion<T> suggToCompleteWith, ScrollingType... scrollingType) {
        if (!checkArgumentsAndThatSuggestionsAreAvailable(suggToCompleteWith, scrollingType)) {
            return false;
        }

        WebElement suggList = getRightSuggestionList();
        if (suggList == null) {
            throw new RuntimeException("The suggestions are available, but can not retrieve the right suggestion list!");
        }

        // get all suggestions and find the desired one
        List<WebElement> allSuggestions = suggList.findElements(By.className(CLASS_NAME_SUGG));
        // index for remembering how many times it will need to press key down to select suggestion
        int i = suggList.findElements(By.className(CLASS_NAME_SUGG_SELECTED)).isEmpty() ? 1 : 0;
        for (WebElement suggestion : allSuggestions) {
            if (suggestion.getText().equals(suggToCompleteWith.getValue())) {

                // choose the suggestion according to the scrolling type
                // at first it does not matter
                if (scrollingType.length == 0) {
                    suggestion.click();

                    // select the suggestion by pressing exact times down key and then enter
                } else if (scrollingType[0] == ScrollingType.BY_KEYS) {
                    Actions builder = new Actions(GrapheneContext.getProxy());
                    LOGGER.log(Level.FINE, "Scrolling by keys.");
                    for (int j = 0; j < i; j++) {
                        builder.sendKeys(Keys.DOWN);
                    }

                    builder.build().perform();
                    WebElement selectedSugg = suggList.findElement(By.className(CLASS_NAME_SUGG_SELECTED));

                    if (selectedSugg.getText().equals(suggToCompleteWith.getValue())) {
                        builder.sendKeys(selectedSugg, Keys.ENTER);
                        builder.build().perform();
                    } else {
                        builder.sendKeys(Keys.DOWN).sendKeys(selectedSugg, Keys.ENTER);
                        builder.build().perform();
                    }

                    // workaround for NoSuchElementException
                    Graphene.waitGui().until(Graphene.element(root).isPresent());

                    // or move the mouse over the right suggestion and click
                } else if (scrollingType[0] == ScrollingType.BY_MOUSE) {
                    Actions builder = new Actions(GrapheneContext.getProxy());
                    LOGGER.log(Level.FINE, "Scrolling by mouse.");
                    builder.moveToElement(suggestion);
                    builder.build().perform();

                    suggestion.click();
                }

                // add suggestion to the list of selected suggestions
                selectedSuggestions.add(suggToCompleteWith);
                return true;
            }
            i++;
        }

        return false;
    }

    protected void checkParser() {
        if (parser == null) {
            throw new IllegalStateException("The parser need to be set before executing this method!");
        }
    }

    protected boolean checkArgumentsAndThatSuggestionsAreAvailable(Object... arguments) {
        for (Object argument : arguments) {
            if (argument == null) {
                throw new IllegalArgumentException("Argument can not be null!");
            }
        }

        if (!areSuggestionsAvailable()) {
            return false;
        }

        return true;
    }

    /**
     * Returns suggestion list of this autocomplete, null if there is not any.
     *
     * @return
     */
    protected WebElement getRightSuggestionList() {
        // the problem here is that suggestion list object in DOM is moved out of the autocomplete component's form when it is
        // displayed, therefore at first it is neccessary to find correct suggestion list and then check if it is displayed
        List<WebElement> suggestionLists = root.findElements(By.xpath("//*[contains(@class,'" + CLASS_NAME_SUGG_LIST + "')]"));

        for (WebElement suggList : suggestionLists) {
            String idOfSuggLst = suggList.getAttribute("id");
            String idOfInput = inputToWrite.getAttribute("id");

            int index = idOfSuggLst.indexOf("List");
            boolean result = idOfInput.contains(idOfSuggLst.substring(0, index));

            if (result) {
                return suggList;
            }
        }

        return null;
    }

    protected void waitForSuggestionsAvailable(WebDriverWait wait) {
        wait.until(Graphene.element(getRightSuggestionList()).isVisible());
    }

    protected void waitForSuggestionsNotAvailable(WebDriverWait wait) {
        wait.until(Graphene.element(getRightSuggestionList()).not().isVisible());
    }

    protected void waitFor(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException ignored) {
        }
    }
}
