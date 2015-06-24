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
    private static final String ACCOUNT_SID = "AC64001238c3f322e6f605c72cf39e2006";
    private static final String AUTH_TOKEN = "7506c80088bf501e84e6b5679ea994d8";
    private static final String SMS_CENTER = "+16307967728";
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
