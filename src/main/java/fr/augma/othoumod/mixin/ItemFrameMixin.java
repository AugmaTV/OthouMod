package fr.augma.othoumod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrame.class)
public abstract class ItemFrameMixin extends HangingEntity {
    protected ItemFrameMixin(EntityType<? extends HangingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        final ItemFrame itemFrame = (ItemFrame) (Object) this;
        if (!itemFrame.level().isClientSide() && !player.isCrouching()) {
            if (hand.equals(InteractionHand.OFF_HAND)) {
                cir.setReturnValue(InteractionResult.PASS);
                return;
            }

            BlockPos bPos = getBlockPos(itemFrame);
            BlockState placedOn = itemFrame.level().getBlockState(bPos);

            if (placedOn.getBlock() instanceof ChestBlock) {
                ChestBlock chestBlock = (ChestBlock) placedOn.getBlock();

                MenuProvider menuProvider = ((ChestBlockMixin) chestBlock).invokeGetMenuProvider(placedOn, itemFrame.level(), bPos);

                if (menuProvider != null) {
                    player.openMenu(menuProvider);
                    player.awardStat(this.getOpenStat());
                    PiglinAi.angerNearbyPiglins((ServerLevel) itemFrame.level(), player, true);
                }
                cir.setReturnValue(InteractionResult.PASS);

            } else if (placedOn.getBlock() instanceof BarrelBlock) {
                BlockEntity blockentity = itemFrame.level().getBlockEntity(bPos);
                if (blockentity instanceof BarrelBlockEntity) {
                    player.openMenu((BarrelBlockEntity) blockentity);
                    player.awardStat(Stats.OPEN_BARREL);
                    PiglinAi.angerNearbyPiglins((ServerLevel) itemFrame.level(), player, true);
                }
                cir.setReturnValue(InteractionResult.PASS);
            }
        }
    }

    @NotNull
    private static BlockPos getBlockPos(ItemFrame itemFrame) {
        int x = itemFrame.getBlockX();
        int y = itemFrame.getBlockY();
        int z = itemFrame.getBlockZ();

        switch(itemFrame.getDirection()) {
            case DOWN:
                y += 1d;
                break;
            case UP:
                y -= 1d;
                break;
            case NORTH:
                z += 1d;
                break;
            case SOUTH:
                z -= 1d;
                break;
            case WEST:
                x += 1d;
                break;
            case EAST:
                x -= 1d;
                break;
        }

        BlockPos bPos = new BlockPos(x, y, z);
        return bPos;
    }

    protected Stat<ResourceLocation> getOpenStat() {
        return Stats.CUSTOM.get(Stats.OPEN_CHEST);
    }
}
