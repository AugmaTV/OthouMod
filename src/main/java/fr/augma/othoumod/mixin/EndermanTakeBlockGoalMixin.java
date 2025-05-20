package fr.augma.othoumod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.entity.monster.EnderMan$EndermanTakeBlockGoal")
public abstract class EndermanTakeBlockGoalMixin {

    @Inject(at=@At("HEAD"), method = "canUse", cancellable = true)
    public void onCanUse(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(false);
    }
}
