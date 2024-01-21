package xyz.wagyourtail.soundcategories.mixins;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.GameOptions__Option;
import net.minecraft.locale.LanguageManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.wagyourtail.soundcategories.screen.VolumeScreen;

@Mixin(OptionsScreen.class)
public abstract class MixinOptionsScreen extends Screen {

    @Shadow private static GameOptions__Option[] RENDER_OPTIONS;

    @Shadow private GameOptions options;
    @Unique
    private ButtonWidget soundCategoriesButton;

    @ModifyVariable(method = "init()V", at = @At(value = "STORE"), index = 2)
    public int onInit(int i) {
        return 2;
    }

    @Inject(method = "init()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/options/OptionsScreen;RENDER_OPTIONS:[Lnet/minecraft/client/options/GameOptions__Option;"))
    public void onInit(CallbackInfo ci) {
        // inject my button for sound categories
        buttons.add(soundCategoriesButton = new ButtonWidget(-1, this.width / 2 - 155, this.height / 6, 150, 20, LanguageManager.getInstance().translate("soundcategories.title")));
    }

    @ModifyVariable(method = "init()V", at = @At(value = "STORE"), index = 5)
    public int onGetGameOption(int i) {
        while (RENDER_OPTIONS[i] == GameOptions__Option.MUSIC || RENDER_OPTIONS[i] == GameOptions__Option.SOUND) {
            i = i + 1;
        }
        return i;
    }

    @Inject(method = "buttonClicked", at = @At(value = "HEAD"))
    public void onButtonClicked(ButtonWidget par1, CallbackInfo ci) {
        if (par1 == soundCategoriesButton) {
            minecraft.openScreen(new VolumeScreen(this, options));
        }
    }


}
