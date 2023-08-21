package View;

import Model.Profile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ProfileManager extends JFrame {

    JPanel formPanel = new JPanel();
    JPanel formAvatar = new JPanel();
    JButton deleteButton = new JButton("Delete Profile");
    JButton changeAvatarButton = new JButton("Change Avatar");
    JButton backToMenu = new JButton("Back to Menu");

    public ProfileManager(Profile profile) {
        setTitle("JTrash - Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(640, 360);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setProfileForm(profile);
        setFormAvatar(profile.getPicture());
        add(formAvatar, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(deleteButton, BorderLayout.SOUTH);
        //add(backToMenu, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void setFormAvatar(int pic) {
        formAvatar.setLayout(new GridBagLayout());
        formAvatar.add(
                new JLabel(new ImageIcon(
                        AssetLoader.getInstance().getAvatars().get(pic)
                ))
        );
        formAvatar.add(changeAvatarButton);
    }

    private void setProfileForm(Profile profile) {
        formPanel.setLayout(new GridLayout(0,2,0,2));
        if (!Profile.isProfileLoaded())
            // TODO il profilo non è caricato (non dovrebbe succedere) fare qualcosa
            return;

        formPanel.add(new JLabel("Name:", null, SwingConstants.CENTER));
        setField(profile.getName());
        formPanel.add(new JLabel("Level:",null, SwingConstants.CENTER));
        setField(String.valueOf(profile.getLevel()));
        formPanel.add(new JLabel("Total Games:",null, SwingConstants.CENTER));
        setField(String.valueOf(profile.getTotalGamesPlayed()));
        formPanel.add(new JLabel("Wins:",null, SwingConstants.CENTER));
        setField(String.valueOf(profile.getWins()));
        formPanel.add(new JLabel("Losses:",null, SwingConstants.CENTER));
        setField(String.valueOf(profile.getLosses()));
        formPanel.add(new JLabel("Exp:",null, SwingConstants.CENTER));
        setField(String.valueOf(profile.getExp()));
    }

    private void setField(String name) {
        JTextField t = new JTextField(name);
        t.setEditable(false);
        t.setBorder(null);
        t.setFont(new Font(t.getFont().getName(), Font.ITALIC, t.getFont().getSize()+1));
        t.setBackground(null);
        t.setSize(300, 100);
        formPanel.add(t);
    }
    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getChangeAvatarButton() {
        return changeAvatarButton;
    }
}
