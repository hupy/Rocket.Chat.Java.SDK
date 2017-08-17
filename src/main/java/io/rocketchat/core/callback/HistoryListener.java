package io.rocketchat.core.callback;

import io.rocketchat.common.data.model.ErrorObject;
import io.rocketchat.common.listener.Listener;
import io.rocketchat.core.model.RocketChatMessage;
import java.util.List;

/**
 * Created by sachin on 21/7/17.
 */
public interface HistoryListener extends Listener {
    void onLoadHistory(List<RocketChatMessage> list, int unreadNotLoaded, ErrorObject error);
}
