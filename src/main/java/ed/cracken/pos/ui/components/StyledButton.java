/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.components;

import com.vaadin.ui.Button;

/**
 *
 * @author eliud
 */
public class StyledButton extends Button {

    public StyledButton(String caption, String style, String id) {
        super(caption);
        setStyleName(style);
        setId(id);
    }

}
