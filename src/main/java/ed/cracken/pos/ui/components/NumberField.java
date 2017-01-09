package ed.cracken.pos.ui.components;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.ui.TextField;

/**
 * A field for entering numbers. On touch devices, a numeric keyboard is shown
 * instead of the normal one.
 */
public class NumberField extends TextField {

    public NumberField() {
        // Mark the field as numeric.
        // This affects the virtual keyboard shown on mobile devices.

        setConverter(new StringToIntegerConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                // do not use a thousands separator, as HTML5 input type
                // number expects a fixed wire/DOM number format regardless
                // of how the browser presents it to the user (which could
                // depend on the browser locale)
                DecimalFormat format = new DecimalFormat();
                format.setMaximumFractionDigits(0);
                format.setDecimalSeparatorAlwaysShown(false);
                format.setParseIntegerOnly(true);
                format.setGroupingUsed(false);
                return format;
            }
        });
    }

    public NumberField(String caption) {
        this();
        setCaption(caption);
    }
}
