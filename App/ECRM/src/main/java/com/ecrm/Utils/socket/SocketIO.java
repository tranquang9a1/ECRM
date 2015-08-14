package com.ecrm.Utils.socket;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.protocol.AckArgs;
import com.corundumstudio.socketio.protocol.JsonSupport;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ChiDNMSE60717 on 6/16/2015.
 */

public class SocketIO{
    private static List<UserOnline> onlineUser = new ArrayList<UserOnline>();
    private static SocketIOServer server;

    public SocketIOServer setup(){

        Configuration config = new Configuration();
//        config.setHostname("128.199.208.93");
//        config.setHostname("10.82.134.241");
        config.setHostname("192.168.1.139");
        config.setPort(3000);
        config.setOrigin("http://localhost:8080");

        server = new SocketIOServer(config);

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("Connect with ID = " + client.getSessionId());
            }
        });

        server.addEventListener("connectServer", UserOnline.class, new DataListener<UserOnline>() {
            @Override
            public void onData(SocketIOClient client, UserOnline user, AckRequest ackRequest) throws Exception {
                UserOnline checkUser = UserOnline.checkContainIn(onlineUser, user.getUsername(), null, user.getDeviceType());
                if (checkUser == null) {
                    List<String> listConnect = new ArrayList<String>();
                    listConnect.add(client.getSessionId().toString());
                    checkUser = new UserOnline(listConnect, user.getUsername(), user.getDeviceType(), user.getRole());
                    onlineUser.add(checkUser);
                    System.out.println(checkUser.getUsername() + " connect by web! " + onlineUser.size());
                } else {
                    checkUser.getSocketId().add(client.getSessionId().toString());
                }

            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                String id = client.getSessionId().toString();
                UserOnline user = UserOnline.checkContainIn(onlineUser, null, id, 0);
                if (user != null) {
                    List<String> listConnect = user.getSocketId();
                    boolean flag = false;

                    for(int i = 0; i < listConnect.size(); i++) {
                        if(listConnect.get(i).equals(id)) {
                            flag = true;
                        }
                    }

                    if(flag == true) {
                        user.getSocketId().remove(id);

                        if (user.getSocketId().size() <= 0) {
                            onlineUser.remove(user);
                            System.out.println(user.getUsername() + " disconnect!");
                        }
                    }
                 }
            }
        });

        server.start();
        return server;
    }

    public void closeServer() {
        server.stop();
    }

    public boolean serverExit() {
        return server != null;
    }

    public boolean sendNotifyObjectToStaff(String username, String eventType, JSONObject jsonObject){
        List<UserOnline> users = UserOnline.getUserByUsername(onlineUser, username);
        SocketIOClient receiver;
        if(users.size() > 0) {
            for (UserOnline user: users) {
                for (String item: user.getSocketId()) {
                    receiver = server.getClient(UUID.fromString(item));
                    receiver.sendEvent(eventType, jsonObject);
                }
                System.out.println("Sent message to " + user.getUsername());
            }
            return true;
        }

        return false;
    }

    public boolean sendNotifyMessageToUser(String username, String eventType, String message, String redirectLink) {
        List<UserOnline> users = UserOnline.getUserByUsername(onlineUser, username);

        if(users.size() > 0) {
            for (UserOnline user: users) {
                for (String item: user.getSocketId()) {
                    SocketIOClient receiver = server.getClient(UUID.fromString(item));

                    JSONObject returnObject = new JSONObject();
                    returnObject.put("message", message);
                    returnObject.put("redirectLink", redirectLink);

                    receiver.sendEvent(eventType, returnObject);
                }
                System.out.println("Sent message to " + user.getUsername());
            }
            return true;
        }

        return false;
    }
}
