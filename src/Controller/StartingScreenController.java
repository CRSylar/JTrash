package Controller;

import Model.Profile;
import Utilities.GameResult;
import Utilities.Utils;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

public class StartingScreenController {

    StartingScreen startingScreen;
    public StartingScreenController(StartingScreen startingScreen) {
        this(startingScreen,null);
    }
    public StartingScreenController(StartingScreen startingScreen, GameResult lastGame) {
        if (lastGame != null)
            updatePlayerScore(lastGame.getResult());
        this.startingScreen = startingScreen;

        if (!Profile.isProfileLoaded()) {
           String name = this.startingScreen.showProfileCreationDialog();
           Profile.createNewProfile(name);
           Utils.save();
        }

        initListeners();
        Sounds.getInstance().stop();
    }

    private void updatePlayerScore(GameResult.RESULT result) {
        Profile.getProfile().updateProfile(result);
        Utils.save();
    }


    private void initListeners() {
        startingScreen.getExitButton().addActionListener(e -> System.exit(0));
        startingScreen.getTwoPlayersButton().addActionListener(e -> startGame(2));
        startingScreen.getThreePlayersButton().addActionListener(e -> startGame(3));
        startingScreen.getFourPlayersButton().addActionListener(e -> startGame(4));
        startingScreen.getProfileButton().addActionListener(e -> showProfile());

    }

    private void startGame(int players) {
        System.out.println("Starting with "+players);
        // inserire qui nuova Schermata
        GameController gm = new GameController(players);
        Sounds.getInstance().play("assets/sounds/ambient.wav", true);
        gm.start();
        // dispose chiude la schermo attuale, lasciando attivo quello creato
        // da GameController
        startingScreen.dispose();
    }

    private void showProfile() {
        System.out.println(Profile.getProfile());
        ProfileManager pm = new ProfileManager(Profile.getProfile());
        initProfileListeners(pm);
        startingScreen.dispose();
    }

    private void initProfileListeners(ProfileManager pm) {
        pm.getDeleteButton().addActionListener(e -> deleteProfileAndExit(pm));
        pm.getBackButton().addActionListener(e -> backToMenu(pm));
        pm.getChangeAvatarButton().addActionListener(e -> showAvatarsModal(pm));
    }


    private void showAvatarsModal(ProfileManager pm) {
        JOptionPane pane = new JOptionPane();
        List<JButton> avatars = AssetLoader.getInstance().getAvatars()
                .stream()
                .map( avatar -> new JLabel(avatar.getLeft().toString(), new ImageIcon(avatar.getRight()), SwingConstants.CENTER))
                .map (avatarIcon -> {
                    int v = Integer.parseInt(avatarIcon.getText());
                    avatarIcon.setText(null);
                    JButton b = new JButton();
                    b.setBorder(null);
                    b.setBackground(null);
                    b.add(avatarIcon);
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            pane.setValue(v);
                        }
                    });
                    return b;
                })
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
        }
    }
    private void backToMenu(ProfileManager pm) {
        StartingScreen sc = new StartingScreen();
        StartingScreenController ssc = new StartingScreenController(sc);
        pm.dispose();
    }

    private void deleteProfileAndExit(ProfileManager pm) {
        Profile.loadProfile(null);
        backToMenu(pm);

    }

}
