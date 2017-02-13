package ed.cracken.pos.ui.components;

import com.vaadin.ui.TextField;
import ed.cracken.pos.ui.converters.CurrencyConverter;

/**
 * A field for entering numbers. On touch devices, a numeric keyboard is shown
 * instead of the normal one.
 */
public class DecimalNumberField extends TextField {

    public DecimalNumberField() {
        setConverter(new CurrencyConverter());
    }

    public DecimalNumberField(String caption) {
        this();
        setCaption(caption);
    }
}
