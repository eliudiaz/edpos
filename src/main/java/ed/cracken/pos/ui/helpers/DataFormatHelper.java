/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.helpers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author eliud
 */
public final class DataFormatHelper {

    public static final NumberFormat getFormatter() {
        return new DecimalFormat("###,###.00");
    }

    public static final String formatNumber(BigDecimal val) {
        return getFormatter().format(val.doubleValue());
    }

    public static final String formatDate(Date date) {
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        return sd.format(date);
    }

}
