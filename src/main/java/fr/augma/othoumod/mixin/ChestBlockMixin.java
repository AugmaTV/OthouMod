package fr.augma.othoumod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChestBlock.class)
public interface ChestBlockMixin {

    @Invoker(value = "getMenuProvider")
    MenuProvider invokeGetMenuProvider(BlockState state, Level world, BlockPos pos);
}
