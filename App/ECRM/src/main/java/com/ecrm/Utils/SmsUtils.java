package com.ecrm.Utils;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.MessageList;
import org.apache.http.message.BasicNameValuePair;
import org.omg.CORBA.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Htang on 6/24/2015.
 */
public class SmsUtils {
    private static final String ACCOUNT_SID = "AC766b17714b930a93d09e97d8372f96ec";
    private static final String AUTH_TOKEN = "4920f5c1d5c7539735c7076bc32046c6";
    private static final String SMS_CENTER = "+17164063801";
    private static final String STATUS_CALLBACK_URL = "";
    private static final TwilioRestClient client;
    static {
        client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendMessage(String toNumber, String body) throws TwilioRestException {
        List<org.apache.http.NameValuePair> params = new ArrayList<org.apache.http.NameValuePair>();
        params.add(new BasicNameValuePair("Body", body));
        params.add(new BasicNameValuePair("To", toNumber));
        params.add(new BasicNameValuePair("From", SMS_CENTER));
        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = messageFactory.create(params);
        System.out.println(message.getSid());
    }
}
