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
package org.restcomm.javax.media.mscontrol.networkconnection;

import org.restcomm.fsm.State;
import org.restcomm.fsm.StateEventHandler;

import jain.protocol.ip.mgcp.message.DeleteConnection;
import jain.protocol.ip.mgcp.message.parms.CallIdentifier;
import jain.protocol.ip.mgcp.message.parms.ConnectionIdentifier;
import jain.protocol.ip.mgcp.message.parms.EndpointIdentifier;

/**
 *
 * @author kulikov
 */
public class DeleteConnectionRequest implements StateEventHandler {
    private NetworkConnectionImpl connection;
    
    protected DeleteConnectionRequest(NetworkConnectionImpl connection) {
        this.connection = connection;
    }
    
    public void onEvent(State state) {
        //if connection has not concrete name then it was not created
        if (!connection.getEndpoint().hasConcreteName()) {
            return;
        }
        //prepear callID and endpointID parameters for request
        CallIdentifier callId = connection.getMediaSession().getCallID();
        EndpointIdentifier endpointID = connection.getEndpoint().getIdentifier();
        ConnectionIdentifier connectionID = connection.getConnectionID();
        //ask for new unique transaction handler
        int txHandle = connection.getMediaSession().getUniqueHandler();
        
        //ask for new unique transaction handler
        DeleteConnection req = new DeleteConnection(this, callId, endpointID, connectionID);
        req.setTransactionHandle(txHandle);

        connection.getMediaSession().getDriver().attach(txHandle, new DeleteConnectionResponseHandler(connection));
        //send request
        connection.getMediaSession().getDriver().send(req);
    }

}
