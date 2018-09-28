package org.vaadin.artur;

import org.vaadin.artur.github_corner.GitHubCorner;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        add(new GitHubCorner("Artur-", "vaadin-examples"));

        setClassName("main-layout");
        TextField numberInput = new TextField("Number input");
        setType(numberInput, "number");
        TextField emailInput = new TextField("Email input");
        setType(emailInput, "email");
        TextField telInput = new TextField("Telephone input");
        setType(telInput, "tel");

        Button button = new Button("Show values", event -> {
            showValue(numberInput);
            showValue(emailInput);
            showValue(telInput);
        });

        add(numberInput, emailInput, telInput, button);
    }

    private void showValue(TextField textField) {
        Notification
                .show(textField.getLabel() + " value: " + textField.getValue());
    }

    private static void setType(TextField textField, String type) {
        textField.getElement().getNode().runWhenAttached(ui -> {
            ui.getPage().executeJavaScript("$0.focusElement.type=$1", textField,
                    type);
        });
    }
}
