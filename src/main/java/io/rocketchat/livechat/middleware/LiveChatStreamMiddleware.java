package io.rocketchat.livechat.middleware;

import io.rocketchat.livechat.callback.AgentListener;
import io.rocketchat.livechat.callback.MessageListener;
import io.rocketchat.livechat.callback.SubscribeListener;
import io.rocketchat.livechat.callback.TypingListener;
import io.rocketchat.livechat.model.AgentObject;
import io.rocketchat.livechat.model.MessageObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sachin on 9/6/17.
 */

//This middleware consists of room subscriptiontype callback

public class LiveChatStreamMiddleware {

    public enum subscriptiontype {
        STREAMROOMMESSAGES,
        STREAMLIVECHATROOM,
        NOTIFYROOM
    }

    public static LiveChatStreamMiddleware middleware=new LiveChatStreamMiddleware();


    MessageListener messageListener;
    AgentListener.AgentConnectListener agentConnectListener;
    TypingListener typingListener;

    ConcurrentHashMap <String,Object[]> subcallbacks;


    LiveChatStreamMiddleware(){
        subcallbacks=new ConcurrentHashMap<>();
    }

    public static LiveChatStreamMiddleware getInstance(){
        return middleware;
    }

    public void subscribeRoom(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void subscribeLiveChatRoom(AgentListener.AgentConnectListener agentConnectListener) {
        this.agentConnectListener = agentConnectListener;
    }

    public void subscribeTyping(TypingListener callback){
        typingListener =callback;
    }

    public void createSubCallbacks(String id, SubscribeListener callback, subscriptiontype subscription){
        subcallbacks.put(id,new Object[]{callback,subscription});
    }

    public void processCallback(JSONObject object){
        String s = object.optString("collection");
        JSONArray array=object.optJSONObject("fields").optJSONArray("args");
        if (s.equals("stream-room-messages")) {
            if (messageListener !=null) {
                messageListener.onMessage(object.optJSONObject("fields").optString("eventName"), new MessageObject(array.optJSONObject(0)));
            }
        }else if (s.equals("stream-livechat-room")){
            if (agentConnectListener !=null) {
                agentConnectListener.onAgentConnect(new AgentObject(array.optJSONObject(0)));
            }
        }else{
            if (typingListener !=null) {
                typingListener.onTyping(object.optJSONObject("fields").optString("eventName"), array.optString(0), array.optBoolean(1));
            }
        }
    }

    public void processSubSuccess(JSONObject subObj){
        if (subObj.optJSONArray("subs")!=null) {
            String id = subObj.optJSONArray("subs").optString(0);
            if (subcallbacks.containsKey(id)) {
                Object object[] = subcallbacks.remove(id);
                SubscribeListener subscribeListener = (SubscribeListener) object[0];
                subscriptiontype subscription = (subscriptiontype) object[1];
                subscribeListener.onSubscribe(subscription, id);
            }
        }
    }

}
