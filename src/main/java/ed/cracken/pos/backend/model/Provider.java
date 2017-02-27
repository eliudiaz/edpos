/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.cracken.pos.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author eliud
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Provider {

    private String id;
    private String name;
    private String address;

}
