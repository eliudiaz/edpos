/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.helpers;

import com.vaadin.ui.Notification;

/**
 *
 * @author edcracken
 */
public final class NotificationsHelper {

    public static void showError(String msg) {
        Notification.show(msg, Notification.Type.ERROR_MESSAGE);
    }

    public static void showSaveNotification(String msg) {
        Notification.show(msg, Notification.Type.TRAY_NOTIFICATION);
    }
}
