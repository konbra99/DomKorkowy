package graphics.gui;

import graphics.Engine;
import sound.SoundManager;

public class SettingsContext extends Context {

    protected Button backButton;
    protected Button nextButton;
    protected DataField server;
    protected DataField music;
    protected DataField sound;

    public SettingsContext() {
        super("gui/background1.png");

        String[] option_strings;
        Object[] option_values;

        // SERVER
        option_strings = new String[]{"konr_pc", "kacp_pc", "kuba_pc", "dam_pc"};
        option_values = new Object[] {0, 1, 2, 3};
        server = new OptionField(null, Button.LONG_BUTTON, option_strings, option_values);
        server.setText("server", "msgothic.bmp", 0.05f, 0.08f);
        server.move(-0.5f, 0.3f);

        // MUSIC
        music = new RadioField(Button.LONG_BUTTON, RadioField.OFF, SoundManager::playBackgroundMusic, SoundManager::stopBackgroundMusic);
        music.setText("music", "msgothic.bmp", 0.05f, 0.08f);
        music.move(-0.5f, 0.1f);

        // SOUND
        sound = new RadioField(Button.LONG_BUTTON, RadioField.ON, SoundManager::enableSoundEffects, SoundManager::disableSoundEffects);
        sound.setText("sound", "msgothic.bmp", 0.05f, 0.08f);
        sound.move(-0.5f, -0.1f);


        // BACK
        backButton = new Button(-0.95f, -0.95f, 0.20f, 0.4f, null, Button.LEFT_ARROW);
        backButton.setText("BACK", "msgothic.bmp", 0.04f, 0.1f);
        backButton.action = () -> {
            Engine.activeContext = Engine.menu;
        };

        // ACCEPT
        nextButton = new Button(0.75f, -0.95f, 0.20f, 0.4f, null, Button.RIGHT_ARROW);
        nextButton.setText("ACCEPT", "msgothic.bmp", 0.04f, 0.1f);
        nextButton.action = () -> {
            Engine.activeContext = Engine.menu;
        };
        super.init();
    }

    @Override
    public void draw() {
        super.draw();
        server.draw();
        music.draw();
        sound.draw();
        backButton.draw();
        nextButton.draw();
    }

    @Override
    public void update() {
        super.update();
        server.update();
        music.update();
        sound.update();
        backButton.update();
        nextButton.update();
    }
}