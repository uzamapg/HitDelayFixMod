package io.ghast.hitdelayfix.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    
    @Shadow 
    private int leftClickCounter;
    
    /**
     * Removes vanilla 1.8.9 hit delay completely
     * Sets leftClickCounter to 0 right when you click
     */
    @Inject(method = "clickMouse", at = @At("HEAD"))
    private void removeHitDelay(CallbackInfo ci) {
        this.leftClickCounter = 0;
    }
    
    /**
     * Prevents the counter from ever being set to anything other than 0
     * This ensures no other code can re-enable hit delay
     */
    @ModifyVariable(
        method = "runTick",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;leftClickCounter:I", ordinal = 0),
        ordinal = 0
    )
    private int keepCounterAtZero(int counter) {
        return 0;
    }
}
