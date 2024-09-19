
import cn.edu.hust.FileUtils.RWCache;
import cn.edu.hust.ui.LobbyJFrame;

import java.io.IOException;

public class   APP {

    public static void main(String[] args) throws IOException {
        //获取Cache记录
        char mark = RWCache.readFromCache("cache_lobby_bgImage");
        //创建游戏大厅界面
        LobbyJFrame lobbyJFrame = new LobbyJFrame();
    }
}
