/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errorhandling;

/**
 *
 * @author jplm
 */
public class NoConnectionException extends Exception {

    public NoConnectionException(String msg) {
        super(msg);
    }

    public NoConnectionException() {
        super("No connection to the server");
    }

}
