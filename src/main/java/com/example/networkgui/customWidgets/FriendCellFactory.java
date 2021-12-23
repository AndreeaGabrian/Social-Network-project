package com.example.networkgui.customWidgets;

import com.company.dto.UserFriendshipDTO;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class FriendCellFactory implements Callback<ListView<UserFriendshipDTO>, ListCell<UserFriendshipDTO>> {

    @Override
    public ListCell<UserFriendshipDTO> call(ListView<UserFriendshipDTO> param) {
        return new FriendCell();
    }
}
