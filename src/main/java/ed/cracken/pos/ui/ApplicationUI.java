/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import ed.cracken.pos.ui.app.MainScreen;
import ed.cracken.pos.ui.authentication.AccessControl;
import ed.cracken.pos.ui.authentication.BasicAccessControl;
import ed.cracken.pos.ui.authentication.LoginScreen;
import ed.cracken.pos.ui.authentication.LoginScreen.LoginListener;

/**
 *
 * @author edcracken
 */
@SpringUI
@Theme("mockapp")
public class ApplicationUI extends UI {

    private AccessControl accessControl = new BasicAccessControl();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("POS");

        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(ApplicationUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static ApplicationUI get() {
        return (ApplicationUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

}
