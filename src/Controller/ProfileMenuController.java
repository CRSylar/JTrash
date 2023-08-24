package Controller;

import Model.Profile;
import Utilities.Utils;
import View.AssetLoader;
import View.AvatarsOptionDialogue;
import View.ProfileManager;
import View.StartingScreen;

import javax.swing.*;
import java.util.List;

/**
 * Controller che gestisce Model e View della parte
 * Profilo, ovvero quando l'utente chiede di accedere al profilo
 * del giocatore
 */
public class ProfileMenuController {

    /**
     * La View relativa alla pagina del profilo
     */
    ProfileManager pm;

    /**
     * Costruisce un Controller del profilo
     * @param profile il profilo da gestire e visualizzare
     */
    public ProfileMenuController(Profile profile) {
        pm = new ProfileManager(profile);
        setListeners();
    }

    /**
     * Imposta i listener per i bottoni esposti dalla View
     * e gestirne i relativi click
     */
    private void setListeners() {
        pm.getDeleteButton().addActionListener(e -> deleteProfileAndExit());
        pm.getBackToMenuButton().addActionListener(e -> backToMenu());
        pm.getChangeAvatarButton().addActionListener(e -> showAvatarsModal());
    }

    /**
     * Distrugge la view relativa alla pagina profilo e
     * istanzia un controller del menu principale, che prende la gestione da li in avanti
     */
    private void backToMenu() {
        StartingScreen sc = new StartingScreen();
        MainMenuController ssc = new MainMenuController(sc);
        pm.dispose();
    }

    /**
     * Cancella il profilo utente caricato, dopo di che
     * chiama backToMenu per chiudere la finestra e tornare al menu principale
     * (che chiederà di creare un nuovo profilo)
     */
    private void deleteProfileAndExit() {
        Profile.loadProfile(null);
        backToMenu();
    }

    /**
     * Mostra il modal per la scelta dell' avatar
     * utilizza gli stream per prendere le immagini degli avatar disponibili,
     * trasformarli in JButton che contengono JLabel
     * e resta in ascolto della scelta dell'utente.
     * Dopo di che salva la scelta del nuovo avatar sul profilo del giocatore
     */
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
