/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.purchases.to;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author edcracken
 */
@Getter
@Setter
@Builder
public class PurchaseTo {
    private ProviderTo provider;
    private String providerId;
    private String providerName;
    private String documentNumber;
    private Date documentDate;
    private List<PurchaseItemTo> items;
}
