package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a Reminder page
 */
public class ReminderWindow extends UiPart<Stage> {

    public static final String REMINDER_MESSAGE = "Reminder to eat medication";

    private static final Logger logger = LogsCenter.getLogger(ReminderWindow.class);
    private static final String FXML = "ReminderWindow.fxml";

    @FXML
    private Label reminderMessage;

    /**
     * Creates a new ReminderWindow.
     *
     * @param root Stage to use as the root of the ReminderWindow.
     */
    public ReminderWindow(Stage root) {
        super(FXML, root);
        //reminderMessage.setText(REMINDER_MESSAGE);
    }

    /**
     * Creates a new ReminderWindow.
     */
    public ReminderWindow() {
        this(new Stage());
    }

    /**
     * Shows the Reminder window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing Reminder window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the Reminder window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the Reminder window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the Reminder window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
