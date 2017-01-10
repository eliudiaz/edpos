/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.ui.seller.to;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.vaadin.mockapp.samples.backend.data.Product;

/**
 *
 * @author eliud
 */
@Setter
@Getter
@Builder
public class ItemTo {

    private String productId;
    private String description;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private Product product;
}
