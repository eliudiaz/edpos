/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.components;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 *
 * @author eliud
 */
public class EdErrorHandler implements ErrorHandler {

    @Override
    public void error(ErrorEvent event) {
        // Finds the original source of the error/exception
        AbstractComponent component = DefaultErrorHandler.findAbstractComponent(event);
        if (component != null) {
            ErrorMessage errorMessage = getErrorMessageForException(event.getThrowable());
            if (errorMessage != null) {
                component.setComponentError(errorMessage);
                new Notification(null, errorMessage.getFormattedHtmlMessage(), Type.WARNING_MESSAGE, true).show(Page.getCurrent());
                return;
            }
        }
        DefaultErrorHandler.doDefault(event);
    }

    private static ErrorMessage getErrorMessageForException(Throwable t) {
        FieldGroup.CommitException commitException = getCauseOfType(t, FieldGroup.CommitException.class);
        if (commitException != null) {
            return new UserError(commitException.toString());
        }

        return new UserError(t.toString());

    }

    private static <T extends Throwable> T getCauseOfType(Throwable th, Class<T> type) {
        while (th != null) {
            if (type.isAssignableFrom(th.getClass())) {
                return (T) th;
            } else {
                th = th.getCause();
            }
        }
        return null;
    }

}
