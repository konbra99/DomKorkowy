package graphics.gui;

import graphics.Engine;

public class SettingsContext extends Context {

    protected Button backButton;
    protected Button nextButton;
    protected DataField server;
    protected DataField music;
    protected DataField fps;
    protected DataField fullScreen;

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
        option_strings = new String[]{"OFF", "ON"};
        option_values = new Object[] {false, true};
        music = new OptionField(null, Button.LONG_BUTTON, option_strings, option_values);
        music.setText("music", "msgothic.bmp", 0.05f, 0.08f);
        music.move(-0.5f, 0.1f);

        // FPS
        option_strings = new String[]{"30", "45", "60", "75", "120"};
        option_values = new Object[] {30, 45, 60, 75, 120};
        fps = new OptionField(null, Button.LONG_BUTTON, option_strings, option_values);
        fps.setText("fps", "msgothic.bmp", 0.05f, 0.08f);
        fps.move(-0.5f, -0.1f);

        // FULL_SCREEN
        option_strings = new String[]{"OFF", "ON"};
        option_values = new Object[] {false, true};
        fullScreen = new OptionField(null, Button.LONG_BUTTON, option_strings, option_values);
        fullScreen.setText("full", "msgothic.bmp", 0.05f, 0.08f);
        fullScreen.move(-0.5f, -0.3f);

        // BACK
        backButton = new Button(-0.95f, -0.95f, 0.20f, 0.4f, null, Button.RIGHT_ARROW);
        backButton.setText("BACK", "msgothic.bmp", 0.04f, 0.1f);
        backButton.action = () -> {
            Engine.activeContext = Engine.menu;
            Engine.STATE = Engine.GAME_STATE.MENU;
        };

        // BACK
        nextButton = new Button(0.75f, -0.95f, 0.20f, 0.4f, null, Button.LEFT_ARROW);
        nextButton.setText("ACCEPT", "msgothic.bmp", 0.04f, 0.1f);
        nextButton.action = () -> {
            Engine.activeContext = Engine.menu;
            Engine.STATE = Engine.GAME_STATE.MENU;
        };
        super.init();
    }

    @Override
    public void draw() {
        super.draw();
        server.draw();
        music.draw();
        fps.draw();
        fullScreen.draw();
        backButton.draw();
        nextButton.draw();
    }

    @Override
    public void update() {
        super.update();
        server.update();
        music.update();
        fps.update();
        fullScreen.update();
        backButton.update();
        nextButton.update();
    }
}
