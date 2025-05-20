package fr.augma.othoumod.mixin;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractVillager.class)
public abstract class MerchantEntityMixin extends AgeableMob implements InventoryCarrier, Npc, Merchant {

    protected MerchantEntityMixin(EntityType<? extends AgeableMob> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @author Augma
     * @reason For better ux in minecraft
     */
    @Overwrite
    public boolean canBeLeashed() {
        return true;
    }
}
