/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.helpers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author eliud
 */
public final class DataFormatHelper {

    public static final String formatNumber(BigDecimal val) {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(val.doubleValue());
    }

    public static final String formatDate(Date date) {
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        return sd.format(date);
    }

}
