/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.helpers;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

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

}
