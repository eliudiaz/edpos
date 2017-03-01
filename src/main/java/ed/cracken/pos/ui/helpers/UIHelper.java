/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.helpers;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 *
 * @author edcracken
 */
public class UIHelper {

    public static HorizontalLayout buildComponentsRow(Component... c) {
        HorizontalLayout hl = new HorizontalLayout(c);
        hl.setSpacing(true);
        return hl;
    }

    public static VerticalLayout buildComponentsColumn(Component... c) {
        VerticalLayout hl = new VerticalLayout(c);
        hl.setSpacing(true);
        return hl;
    }

    public static void addEnterShortCutListener(TextField c, String method, Object source) {
        c.addShortcutListener(new AbstractField.FocusShortcut(c, ShortcutAction.KeyCode.ENTER) {
            @Override
            public void handleAction(Object sender, Object target) {
                try {
                    MethodUtils.invokeMethod(source, method);
                }
                catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
