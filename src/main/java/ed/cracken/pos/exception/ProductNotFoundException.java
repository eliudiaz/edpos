/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.exception;

/**
 *
 * @author edcracken
 */
public class ProductNotFoundException extends ValidationException {

    public ProductNotFoundException() {
        super("No se encontro producto con ese ID!");
    }

}
