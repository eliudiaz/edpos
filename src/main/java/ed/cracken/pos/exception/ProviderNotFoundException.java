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
public class ProviderNotFoundException extends ValidationException {

    public ProviderNotFoundException() {
        super("No se encontro provider con ese ID!");
    }

}
