/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2017, Telestax Inc and individual contributors
 * by the @authors tag. 
 *  
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *  
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.restcomm.fsm;

/**
 *
 * @author kulikov
 */
public class Transition implements Runnable {

    private String name;
    protected State destination;
    
    private TransitionHandler handler;
    private State state;
    
    protected Transition(String name, State destination) {
        this.name = name;
        this.destination = destination;
    }
    
    public String getName() {
        return name;
    }
    
    public void setHandler(TransitionHandler handler) {
        this.handler = handler;
    }
    
    protected State process(State state) {
        this.state = state;
        //leave current state
        state.leave();
        
        run();
        
        //enter to the destination
        this.destination.enter();
        return this.destination;
    }
    
    @Override
    public String toString() {
        return name;
    }

    public void run() {
        
        //invoke handler if assigned
        if (handler != null) {
            handler.process(state);
        }
        
    }
}
