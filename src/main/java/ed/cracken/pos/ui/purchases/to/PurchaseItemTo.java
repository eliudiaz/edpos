/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.purchases.to;

import ed.cracken.pos.backend.model.Product;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class PurchaseItemTo {

    private Product product;
    private String code;
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal subtotal;

    public PurchaseItemTo() {
    }

}