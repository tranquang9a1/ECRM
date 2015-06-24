package com.ecrm.Utils.socket;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by ChiDNMSE60717 on 6/16/2015.
 */
public class ConfigurationSocket implements InitializingBean, DisposableBean
{
    public void afterPropertiesSet() throws Exception {
        SocketIO socketIO = new SocketIO();
        if(!socketIO.serverExit()) {
            socketIO.setup();
        }
    }

    public void destroy() throws Exception {
        SocketIO socketIO = new SocketIO();
        if(!socketIO.serverExit()) {
            socketIO.closeServer();
        }
    }

}
