/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.converters;

import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author edcracken
 */
public class CurrencyConverter extends StringToBigDecimalConverter {

    @Override
    public BigDecimal convertToModel(String value,
            Class<? extends BigDecimal> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        //value = value.replaceAll("[€\\s]", "").trim();
        value = value.replaceAll("[\\s]", "").trim();
        if ("".equals(value)) {
            value = "0";
        }
        return super.convertToModel(value, targetType, locale);
    }

    @Override
    protected NumberFormat getFormat(Locale locale) {
        NumberFormat format = super.getFormat(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setMaximumFractionDigits(2);
            ((DecimalFormat) format).setMinimumFractionDigits(2);
        }
        return format;
    }

    @Override
    public String convertToPresentation(BigDecimal value,
            Class<? extends String> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        return super.convertToPresentation(value, targetType, locale);// + " €";
    }

}
