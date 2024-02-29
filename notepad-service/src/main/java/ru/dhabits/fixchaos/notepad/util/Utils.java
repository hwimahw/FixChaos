package ru.dhabits.fixchaos.notepad.util;

import ru.dhabits.fixchaos.notepad.db.model.Folder;
import ru.dhabits.fixchaos.notepad.db.model.Note;
import ru.dhabits.fixchaos.notepad.db.model.Notebook;

import java.util.List;

public class Utils {
    public static void setFolderToNotebooks(Folder folder, List<Notebook> notebooks) {
        if (folder == null || notebooks == null) {
            return;
        }
        notebooks.forEach(notebook -> notebook.setFolder(folder));
    }

    public static void setNotebooksToNotes(List<Notebook> notebooks) {
        if (notebooks == null) {
            return;
        }
        for (Notebook notebook : notebooks) {
            setNotebookToNotes(notebook, notebook.getNotes());
        }
    }

    public static void setNotebookToNotes(Notebook notebook, List<Note> notes) {
        if (notebook == null || notes == null) {
            return;
        }
        notes.forEach(note -> note.setNotebook(notebook));
    }
}
