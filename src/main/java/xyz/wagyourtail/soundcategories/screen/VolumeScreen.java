package xyz.wagyourtail.soundcategories.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.locale.LanguageManager;
import xyz.wagyourtail.soundcategories.VolumeManager;

public class VolumeScreen extends Screen {
    private final Screen parent;
    private final GameOptions options;
    protected String title = "Musing & Sound Options";


    public VolumeScreen(Screen parent, GameOptions options) {
        this.parent = parent;
        this.options = options;
    }

    @Override
    protected void keyPressed(char chr, int key) {
        if (key == 1) {
            this.minecraft.openScreen(this.parent);
            options.save();
        }
    }

    @Override
    public void init() {
        super.init();
        LanguageManager languageManager = LanguageManager.getInstance();
        this.title = languageManager.translate("soundcategories.title");

        this.buttons.add(
            new SliderWidget(
                0,
                this.width / 2 - 155,
                this.height / 6 + 24 * 0,
                310,
                20,
                languageManager.translate("soundcategories.category.master"),
                VolumeManager.getMasterVolume(),
                VolumeManager::setMasterVolume
            )
        );

        VolumeManager.SoundCategory[] cat = VolumeManager.SoundCategory.values();
        for (int i = 1; i < cat.length; i++) {
            this.buttons.add(
                new SliderWidget(
                    i * 100,
                    this.width / 2 - 155 + (i - 1) % 2 * 160,
                    this.height / 6 + 24 * ((i + 1) >> 1),
                    languageManager.translate(cat[i].getKey()),
                    cat[i].getVolume(),
                    cat[i]::setVolume
                )
            );
        }

        this.buttons.add(
            new ButtonWidget(
                101,
                this.width / 2 - 100,
                this.height / 6 + 168,
                languageManager.translate("gui.done")
            )
        );

    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        renderBackground();
        this.drawCenteredString(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(mouseX, mouseY, tickDelta);
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        super.buttonClicked(button);
        if (button.id == 101) {
            this.minecraft.openScreen(this.parent);
            options.save();
        }
    }
}
