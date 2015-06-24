package com.ecrm.Utils.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

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
        config.setHostname("192.168.43.163");
        config.setPort(8000);

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
                UserOnline checkUser = UserOnline.checkContainIn(onlineUser, user.getUsername(), null);
                if(checkUser == null) {
                    checkUser = new UserOnline(client.getSessionId().toString(), user.getUsername(), user.getRole());
                    onlineUser.add(checkUser);
                    System.out.println(user.getUsername() + " connect! " + onlineUser.size());
                } else {
                    checkUser.setSocketId(client.getSessionId().toString());
                }

            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                String id = client.getSessionId().toString();
                UserOnline user = UserOnline.checkContainIn(onlineUser, null, id);
                if (user != null) {
                    onlineUser.remove(user);
                    System.out.println(user + " disconnect!");
                }
            }
        });

        server.start();
        return server;
    }

    public void closeServer() {
        server.stop();
    }

    public void SentNotifyToStaff(String message){
        List<UserOnline> staffs = UserOnline.getUserByRole(onlineUser, 2);
        System.out.println("Send " + onlineUser.size());
        if(staffs.size() > 0) {
            for (UserOnline staff: staffs) {
                SocketIOClient receiver = server.getClient(UUID.fromString(staff.getSocketId()));
                receiver.sendEvent("NewReport", new TransferNotifyObject(message));
                System.out.println("Sent");
            }
        }
    }

    public boolean serverExit() {
        return server != null;
    }
}
