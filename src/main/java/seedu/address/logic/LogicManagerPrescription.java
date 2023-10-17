package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandPrescription;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PrescriptionListParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelPrescription;
import seedu.address.model.ReadOnlyPrescriptionList;
import seedu.address.model.prescription.Prescription;
import seedu.address.storage.StoragePrescription;

/**
 * The main LogicManagerPrescription of the app.
 */
public class LogicManagerPrescription implements LogicPrescription {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManagerPrescription.class);

    private final ModelPrescription model;
    private final StoragePrescription storage;
    private final PrescriptionListParser prescriptionListParser;

    /**
     * Constructs a {@code LogicManagerPrescription} with the given {@code Model} and {@code Storage}.
     */
    public LogicManagerPrescription(ModelPrescription model, StoragePrescription storage) {
        this.model = model;
        this.storage = storage;
        prescriptionListParser = new PrescriptionListParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        CommandPrescription command = prescriptionListParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.savePrescriptionList(model.getPrescriptionList());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyPrescriptionList getPrescriptionList() {
        return model.getPrescriptionList();
    }

    @Override
    public ObservableList<Prescription> getFilteredPrescriptionList() {
        return model.getFilteredPrescriptionList();
    }

    @Override
    public Path getPrescriptionListFilePath() {
        return model.getPrescriptionListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}