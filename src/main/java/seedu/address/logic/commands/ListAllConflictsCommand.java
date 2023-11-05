package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRESCRIPTIONS;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.prescription.Name;
import seedu.address.model.prescription.Prescription;

/**
 * Lists all conflicting drugs from existing prescriptions.
 */
public class ListAllConflictsCommand extends Command {

    public static final String COMMAND_WORD = "listAllConflicts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all conflicting "
            + "medications from all prescriptions\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all conflicting drugs.";
    public static final String MESSAGE_EMPTY_LIST = "There are no conflicting drugs.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredPrescriptionList(PREDICATE_SHOW_ALL_PRESCRIPTIONS);

        ObservableList<Prescription> prescriptionList = model.getFilteredPrescriptionList();
        Set<Name> conflictingDrugs = new HashSet<Name>();
        for (Prescription prescription : prescriptionList) {
            conflictingDrugs.addAll(prescription.getConflictingDrugs());
        }

        if (conflictingDrugs.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        } else {
            StringBuilder conflictingDrugsString = new StringBuilder();
            for (Name drug : conflictingDrugs) {
                conflictingDrugsString.append(drug.toString() + "\n");
            }
            return new CommandResult(MESSAGE_SUCCESS + "\n" + conflictingDrugsString.toString());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ListAllConflictsCommand;
    }
}
