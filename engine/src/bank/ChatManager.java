package bank;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private final List<SingleChatEntry> chatDataList;

    public ChatManager() {
        chatDataList = new ArrayList<>();
    }

    public int getVersion() {
        return chatDataList.size();
    }
}