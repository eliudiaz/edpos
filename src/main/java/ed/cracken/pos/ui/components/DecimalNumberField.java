package ed.cracken.pos.ui.components;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import com.vaadin.ui.TextField;
import ed.cracken.pos.ui.helpers.DataFormatHelper;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * A field for entering numbers. On touch devices, a numeric keyboard is shown
 * instead of the normal one.
 */
public class DecimalNumberField extends TextField {

    public DecimalNumberField() {
        final DecimalFormat df = (DecimalFormat) DataFormatHelper.getFormatter();
        setConverter(new StringToBigDecimalConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return df;
            }

            @Override
            public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale) throws Converter.ConversionException {
                try {
                    return value == null || value.isEmpty() ? BigDecimal.ZERO : BigDecimal.valueOf(df.parse(value).doubleValue());
                } catch (ParseException ex) {
                    ex.printStackTrace(System.err);
                    return BigDecimal.ZERO;
                }
            }

        });
    }

    public DecimalNumberField(String caption) {
        this();
        setCaption(caption);
    }
}
