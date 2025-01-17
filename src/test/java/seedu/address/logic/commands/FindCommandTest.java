package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.CompletedPrescriptions.getCompletedPrescriptionList;
import static seedu.address.testutil.TypicalPrescriptions.ASPIRIN;
import static seedu.address.testutil.TypicalPrescriptions.PROPRANOLOL;
import static seedu.address.testutil.TypicalPrescriptions.ZOMIG;
import static seedu.address.testutil.TypicalPrescriptions.getTypicalPrescriptionList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.prescription.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrescriptionList(), getCompletedPrescriptionList(), new UserPrefs());
        expectedModel = new ModelManager(model.getPrescriptionList(),
            model.getCompletedPrescriptionList(), new UserPrefs());
    }
    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different prescription -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    // EP: Whitespace
    @Test
    public void execute_whitespace_noPrescriptionFound() {
        String expectedMessage = String.format(MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPrescriptionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPrescriptionList());
    }

    // EP: Single Word
    @Test
    public void execute_singleKeyword_onePrescriptionFound() {
        String expectedMessage = String.format(MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("Aspirin");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPrescriptionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ASPIRIN), model.getFilteredPrescriptionList());
    }

    // EP: Multiple words
    @Test
    public void execute_multipleKeywords_multiplePrescriptionsFound() {
        String expectedMessage = String.format(MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW, 3);
        // "Zolmitriptan Rapimelt" is the name for ZOMIG
        NameContainsKeywordsPredicate predicate = preparePredicate("Aspirin Propranolol Zolmitriptan Rapimelt");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPrescriptionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ASPIRIN, PROPRANOLOL, ZOMIG), model.getFilteredPrescriptionList());
    }

    // EP: Prefix substring
    @Test
    public void execute_prefixSubstring_prescriptionsFound() {
        String expectedMessage = String.format(MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("Asp");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPrescriptionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ASPIRIN), model.getFilteredPrescriptionList());
    }

    // EP: Substring in the middle
    @Test
    public void execute_subStringInMiddle_prescriptionsFound() {
        String expectedMessage = String.format(MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("pir");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPrescriptionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ASPIRIN), model.getFilteredPrescriptionList());
    }

    // EP: Postfix substring
    @Test
    public void execute_postfixSubstring_prescriptionsFound() {
        String expectedMessage = String.format(MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("irin");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPrescriptionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ASPIRIN), model.getFilteredPrescriptionList());
    }

    // EP: All small letters
    @Test
    public void execute_allSmallLetters_prescriptionsFound() {
        String expectedMessage = String.format(MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("aspirin");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPrescriptionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ASPIRIN), model.getFilteredPrescriptionList());
    }

    // EP: All capital letters
    @Test
    public void execute_allCapitalLetters_prescriptionsFound() {
        String expectedMessage = String.format(MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("ASPIRIN");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPrescriptionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ASPIRIN), model.getFilteredPrescriptionList());
    }

    // EP: Mixed case
    @Test
    public void execute_mixedCase_prescriptionsFound() {
        String expectedMessage = String.format(MESSAGE_PRESCRIPTIONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("aSpIrIn");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPrescriptionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ASPIRIN), model.getFilteredPrescriptionList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
