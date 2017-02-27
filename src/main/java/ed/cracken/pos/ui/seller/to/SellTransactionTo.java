/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller.to;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author edcracken
 */
@Getter
@Setter
@AllArgsConstructor
public class SellTransactionTo {

    private SellSummaryTo summary;
    private Collection<ItemTo> items;
}
