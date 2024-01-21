package xyz.wagyourtail.soundcategories.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.wagyourtail.soundcategories.VolumeManager;

import java.io.PrintWriter;

@Mixin(GameOptions.class)
public class MixinGameOptions {

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z", ordinal = 0, remap = false))
    public void onLoad(CallbackInfo ci, @Local String[] arg) {
        VolumeManager.load(arg);
    }

    @Inject(method = "save", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V", remap = false))
    public void onSave(CallbackInfo ci, @Local PrintWriter pw) {
        VolumeManager.save(pw);
    }

}
