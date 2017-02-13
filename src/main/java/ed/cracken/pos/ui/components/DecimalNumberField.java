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
public class DecimalNumberField extends TextField {

    public DecimalNumberField() {

        setConverter(new StringToIntegerConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {             
                DecimalFormat format = new DecimalFormat();
                format.setMaximumFractionDigits(2);
                format.setDecimalSeparatorAlwaysShown(false);
                format.setGroupingUsed(false);
                return format;
            }
        });
    }

    public DecimalNumberField(String caption) {
        this();
        setCaption(caption);
    }
}
