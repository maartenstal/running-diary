package com.la.runners.client.widget.form.field;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.res.Resources;

public class CheckBoxField extends FormField {

    private CheckBox field;
    
    public CheckBoxField(String name, String debugId) {
        super(null, debugId);
        HorizontalPanel panel = new HorizontalPanel();
        panel.add(new Label(name + LABEL_SEPARATOR));
        panel.setStyleName(Resources.INSTANCE.form().editorCheckBoxContainer());
        field = new CheckBox();
        field.setStyleName(Resources.INSTANCE.form().editorCheckBox());
        panel.add(field);
        setField(panel);
    }

    @Override
    public Boolean getValue() {
        return field.getValue();
    }
    
    @Override
    public void setValue(Object value) {
        if(value == null) {
            value = Boolean.FALSE;
        }
        field.setValue((Boolean)value);
    }

    @Override
    public void reset() {
        setValue(Boolean.FALSE);
    }

}
