package fr.augma.othoumod.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class PlayerInventoryUtils {

    public static int getItemStackCount(final Inventory inventory, final ItemStack itemStack) {
        int count = 0;
        for (ItemStack stack : inventory) {
            if (stack.is(itemStack.getItem())) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public static void removeItems(final Inventory inventory, final ItemStack itemStack, final int count) {
        int copyCount = count;

        for (ItemStack stack : inventory) {
            if (copyCount <= 0) {
                break;
            }

            if (stack.is(itemStack.getItem())) {
                if (stack.getCount() <= copyCount) {
                    copyCount -= stack.getCount();
                    inventory.removeItem(stack);
                } else {
                    final int newCount = stack.getCount() - copyCount;
                    copyCount -= stack.getCount() - newCount;
                    stack.setCount(newCount);
                }
            }
        }
    }

}