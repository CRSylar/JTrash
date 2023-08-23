package Controller;

import Model.Profile;
import Utilities.Utils;
import View.AssetLoader;
import View.AvatarsOptionDialogue;
import View.ProfileManager;
import View.StartingScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProfileMenuController {

    ProfileManager pm;
    public ProfileMenuController(Profile profile) {
        pm = new ProfileManager(profile);
        setListeners();
    }

    private void setListeners() {
        pm.getDeleteButton().addActionListener(e -> deleteProfileAndExit());
        pm.getBackToMenuButton().addActionListener(e -> backToMenu());
        pm.getChangeAvatarButton().addActionListener(e -> showAvatarsModal());
    }

    private void backToMenu() {
        StartingScreen sc = new StartingScreen();
        MainMenuController ssc = new MainMenuController(sc);
        pm.dispose();
    }

    private void deleteProfileAndExit() {
        Profile.loadProfile(null);
        backToMenu();
    }

    private void showAvatarsModal() {
        JOptionPane pane = new JOptionPane();
        List<JButton> avatars = AssetLoader
                .getInstance()
                .getAvatars()
                .stream()
                .map( avatar -> new JLabel(avatar.getLeft().toString(), new ImageIcon(avatar.getRight()), SwingConstants.CENTER))
                .map (avatarIcon -> pm.iconToButton(avatarIcon, pane))
                .toList();


        int newPic = AvatarsOptionDialogue.show(
                pm,
                pane,
                "Choose new avatar",
                "Avatar selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                ((JLabel)avatars.get(Profile.getProfile().getPicture()).getComponent(0)).getIcon(),
                avatars.toArray(),
                avatars.get(Profile.getProfile().getPicture())
        );
        if (newPic >= 0 && newPic < avatars.size()){
            Profile.getProfile().setNewAvatar(newPic);
            pm.setFormAvatar(newPic);
            Utils.save();
        }
    }


}
