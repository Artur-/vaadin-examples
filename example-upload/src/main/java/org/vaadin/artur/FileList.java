package org.vaadin.artur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

public class FileList extends Grid<File> {

    private File folder;

    public FileList(File folder) {
        super(File.class);
        getColumns().forEach(this::removeColumn);
        addColumn("name").setSortable(true);
        addColumn(file -> file.length()).setHeader("Size").setSortable(true);
        addColumn(file -> getType(file)).setHeader("Type");

        addSelectionListener(e -> {
            e.getFirstSelectedItem().ifPresent(this::showFile);
        });
        this.folder = folder;
        refresh();
    }

    private String getType(File file) {
        return isImage(file) ? "Image" : "";
    }

    public void refresh() {
        setItems(folder.listFiles());
    }

    private void showFile(File file) {
        if (isImage(file)) {
            Dialog dialog = new Dialog(new Image(new StreamResource(
                    file.getName(), new InputStreamFactory() {

                        @Override
                        public InputStream createInputStream() {
                            try {
                                return new FileInputStream(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                    }), file.getName()));
            dialog.open();
        }
    }

    private boolean isImage(File file) {
        return file.getName().endsWith(".png")
                || file.getName().endsWith(".jpg")
                || file.getName().endsWith(".gif");
    }
}
