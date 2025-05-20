package fr.augma.othoumod.util;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CompactUtils {

    public static void compact(final ServerPlayer player, final int slotId) {
        if (player.getInventory().getFreeSlot() == -1) {
            player.sendSystemMessage(Component.literal("T'es full inv chef, au moins un petite place stp"));
            return;
        }

        if (player.inventoryMenu == null) {
            return;
        }

        final Slot slot = player.inventoryMenu.getSlot(slotId);
        if (slot != null && slot.hasItem()) {
            final ItemStack stack = slot.getItem().copy();
            stack.setCount(1);
            CompactUtils.compact(player, stack);
        }
    }

    public static void compact(final ServerPlayer player) {
        if (player.getInventory().getFreeSlot() == -1) {
            player.sendSystemMessage(Component.literal("T'es full inv chef, au moins un petite place stp"));
            return;
        }

        final ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND).copy();
        stack.setCount(1);
        if (stack == ItemStack.EMPTY) {
            player.sendSystemMessage(Component.literal("Merci d'avoir un item dans la main enculé"));
            return;
        }

        CompactUtils.compact(player, stack);
    }

    public static void compact(final ServerPlayer player, final ItemStack stack) {
        int itemCount = PlayerInventoryUtils.getItemStackCount(player.getInventory(), stack);
        if (itemCount < 9) {
            player.sendSystemMessage(Component.literal("Plus de 9 item plz fait un effort"));
            return;
        }

        final List<ItemStack> grid = Arrays.asList(stack, stack, stack, stack, stack, stack, stack, stack, stack);
        final CraftingInput workbenchInput = CraftingInput.of(3, 3, grid);

        final Optional<RecipeHolder<CraftingRecipe>> optRecipe = player.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, workbenchInput, player.level());
        if (!optRecipe.isPresent()) {
            player.sendSystemMessage(Component.literal("Aucun craft existe pour cette item t'es vraiment con"));
            return;
        }

        final RecipeHolder<CraftingRecipe> recipe = optRecipe.get();
        final ItemStack recipeResult = recipe.value().assemble(workbenchInput, player.level().registryAccess()).copy();

        while (itemCount >= 9) {
            PlayerInventoryUtils.removeItems(player.getInventory(), stack, 9);
            player.getInventory().add(recipeResult.copy());
            itemCount = PlayerInventoryUtils.getItemStackCount(player.getInventory(), stack);
        }
    }

}