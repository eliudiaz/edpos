package ed.cracken.pos.ui.components;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import com.vaadin.ui.TextField;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A field for entering numbers. On touch devices, a numeric keyboard is shown
 * instead of the normal one.
 */
public class DecimalNumberField extends TextField {

    public DecimalNumberField() {
        setConverter(new StringToBigDecimalConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return new DecimalFormat("###,###.##");
            }

            @Override
            public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale) throws Converter.ConversionException {
                return value == null || value.isEmpty() ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(value));
            }

        });
    }

    public DecimalNumberField(String caption) {
        this();
        setCaption(caption);
    }
}
