package org.vaadin.artur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.MultiFileReceiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    private FileList fileList;

    public MainView() {
        setClassName("main-layout");
        setSizeFull();
        Upload upload = new Upload((MultiFileReceiver) (filename, mimeType) -> {
            File file = new File(new File("uploaded-files"), filename);
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                return null;
            }
        });
        upload.addStartedListener(e -> {
            getLogger().info("Handling upload of " + e.getFileName() + " ("
                    + e.getContentLength() + " bytes) started");
        });
        upload.addSucceededListener(e -> {
            getLogger().info("Handling upload of " + e.getFileName() + " ("
                    + e.getContentLength() + " bytes) completed");
            fileList.refresh();
        });

        fileList = new FileList(getUploadFolder());
        setFlexGrow(1, fileList);
        add(upload, fileList);
    }

    private static File getUploadFolder() {
        File folder = new File("uploaded-files");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    private Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }
}
