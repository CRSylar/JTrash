package View;

import Model.Profile;

import javax.swing.*;
import java.awt.*;

/**
 * Frame che rappresenta la vista del profilo,
 * ospita le informazioni sul profilo in uso
 */
public class ProfileManager extends JFrame {

    /**
     * Pannello contente il form (non editabile)
     * che mostra le statistiche del giocatore
     */
    JPanel formPanel = new JPanel();
    /**
     * Pannello che ospita l'avatar e relativo bottone
     * per modificarlo
     */
    JPanel formAvatar = new JPanel();
    /**
     * Bottone utilizzato per cancellare tutte le informazioni
     * del profilo attualmente in uso
     */
    JButton deleteButton = new JButton("Delete Profile");
    /**
     * Bottone utilizzato per aprire il modal e accedere all' OptionDialogue
     * per modificare l'avatar in uso
     */
    JButton changeAvatarButton = new JButton("Change Avatar");
    /**
     * Bottone utilizzato per tornare indietro verso il menu principale
     */
    JButton backToMenuButton = new JButton("Back to Menu");

    /**
     * Costruisce un Frame di visualizzazione Profilo.
     * Imposta il titolo, la dimensione, il layout...
     * Aggiunge i componenti al frame e rende tutto visibile
     * @param profile il profilo di cui mostrare le statistiche
     */
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
        add(buttonsPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Crea il Pannello che ospita i due bottoni
     * - DeleteProfile
     * - BackToMenu
     * @return un JPanel che contiene questi elementi
     */
    private JPanel buttonsPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,3));
        panel.add(deleteButton);
        panel.add(new JPanel());
        panel.add(backToMenuButton);
        return panel;
    }

    /**
     * Imposta l'immagine dell'avatar in uso nel pannello
     * @param pic l'id dell'immagine in uso
     */
    public void setFormAvatar(int pic) {
        formAvatar.removeAll();
        formAvatar.setLayout(new GridBagLayout());

        formAvatar.add(
                new JLabel(new ImageIcon(
                        AssetLoader.getInstance().getAvatars().get(pic).getRight()
                ))
        );
        System.out.println("Resetting with pic n: "+pic);
        formAvatar.add(changeAvatarButton);
        formAvatar.revalidate();
        formAvatar.repaint();
    }

    /**
     * Imposta i valori del Pannello che mostra le statistiche del giocatore
     * @param profile il profilo da cui prendere le statistiche
     */
    private void setProfileForm(Profile profile) {
        formPanel.setLayout(new GridLayout(0,2,0,2));
        if (Profile.isProfileNotLoaded())
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

    /**
     * Aggiunge al pannello un JTextField contenente il testo ricevuto
     * @param testToShow il testo ricevuto
     */
    private void setField(String testToShow) {
        JTextField t = new JTextField(testToShow);
        t.setEditable(false);
        t.setBorder(null);
        t.setFont(new Font(t.getFont().getName(), Font.ITALIC, t.getFont().getSize()+1));
        t.setBackground(null);
        t.setSize(300, 100);
        formPanel.add(t);
    }

    /**
     * Riceve un JLabel contente un'icona e lo inserisce in un nuovo bottone,
     * impostandone un listener per settare il valore del pane ricevuto.
     * Durante questa trasformazione il testo del JLabel viene settato a NULL
     * @param avatarIcon il JLabel contente l'icona
     * @param pane il cui valore settare al click del bottone
     * @return JButton
     */
    public JButton iconToButton(JLabel avatarIcon, JOptionPane pane) {
        int iconId = Integer.parseInt(avatarIcon.getText());
        avatarIcon.setText(null);
        JButton jButton = new JButton();
        jButton.setBorder(null);
        jButton.setBackground(null);
        jButton.add(avatarIcon);
        jButton.addActionListener(e -> pane.setValue(iconId));
        return jButton;
    }

    /**
     *
     * @return il bottone deleteProfile
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /**
     *
     * @return il bottone changeAvatar
     */
    public JButton getChangeAvatarButton() {
        return changeAvatarButton;
    }

    /**
     *
     * @return il bottone backToMenu
     */
    public JButton getBackToMenuButton() {
        return backToMenuButton;
    }
}
