package com.rocketchat.core.callback;

import com.rocketchat.common.data.model.User;
import com.rocketchat.common.listener.Callback;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sachin on 20/7/17.
 */
public class RoomCallback {
    public interface GroupCreateCallback extends Callback {
        void onCreateGroup(String roomId);
    }

    public interface GetMembersCallback extends Callback {
        void onGetRoomMembers(Integer total, List<User> members);
    }

}