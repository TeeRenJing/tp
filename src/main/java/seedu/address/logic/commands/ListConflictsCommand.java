package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.prescription.Name;
import seedu.address.model.prescription.Prescription;

/**
 * Deletes a prescription from prescription list.
 */
public class ListConflictsCommand extends Command {

    public static final String COMMAND_WORD = "listConflicts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists conflicting drugs"
            + "for the prescription at the index. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Here are the conflicting drugs.";
    public static final String MESSAGE_EMPTY_LIST = "There are no conflicting drugs.";


    private final Index targetIndex;

    /**
     * Creates a ListConflictsCommand to list the conflicting drugs.
     */
    public ListConflictsCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Prescription> lastShownList = model.getFilteredPrescriptionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRESCRIPTION_DISPLAYED_INDEX);
        }

        Prescription prescriptionToListConflicts = lastShownList.get(targetIndex.getZeroBased());
        StringBuilder conflictingDrugsString = new StringBuilder();
        Set<Name> conflictingDrugs = prescriptionToListConflicts.getConflictingDrugs();
        if (conflictingDrugs.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        }

        for (Name drug : conflictingDrugs) {
            conflictingDrugsString.append(drug.toString() + "\n");
        }
        return new CommandResult(MESSAGE_SUCCESS + "\n" + conflictingDrugsString.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListConflictsCommand)) {
            return false;
        }

        ListConflictsCommand otherListConflictsCommand = (ListConflictsCommand) other;
        return targetIndex.equals(otherListConflictsCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toListConflicts", targetIndex)
                .toString();
    }
}
