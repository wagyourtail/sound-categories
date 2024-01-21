package xyz.wagyourtail.soundcategories.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.sound.system.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xyz.wagyourtail.soundcategories.VolumeManager;

@Mixin(SoundEngine.class)
public class MixinSoundEngine {

    @ModifyArg(method = "tickMusic", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;setVolume(Ljava/lang/String;F)V"))
    public float onMusic(float volume) {
        return (float) (VolumeManager.getMasterVolume() * VolumeManager.getMusicVolume());
    }

    @ModifyArg(method = "playRecord", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;setVolume(Ljava/lang/String;F)V"))
    public float onRecord(float volume) {
        return (float) (VolumeManager.getMasterVolume() * VolumeManager.getRecordVolume());
    }

    @ModifyArg(method = "play(Ljava/lang/String;FF)V", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;setVolume(Ljava/lang/String;F)V"))
    public float onPlaySound1(float volume, @Local(argsOnly = true) String name) {
        return VolumeManager.getVolume(name);
    }

    @ModifyArg(method = "play(Ljava/lang/String;FFFFF)V", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;setVolume(Ljava/lang/String;F)V"))
    public float onPlaySound2(float volume, @Local(argsOnly = true) String name) {
        return VolumeManager.getVolume(name);
    }

}
