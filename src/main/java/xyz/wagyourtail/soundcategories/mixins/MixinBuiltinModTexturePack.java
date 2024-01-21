package xyz.wagyourtail.soundcategories.mixins;

import net.ornithemc.osl.resource.loader.impl.BuiltInModTexturePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = BuiltInModTexturePack.class, remap = false)
public class MixinBuiltinModTexturePack {

    @ModifyArg(method = "getPath", at = @At(value = "INVOKE", target = "Ljava/nio/file/Path;resolve(Ljava/lang/String;)Ljava/nio/file/Path;"))
    public String onResolvePath(String path) {
        if (path.startsWith("/")) {
            return path.substring(1);
        } else {
            return path;
        }
    }

}
