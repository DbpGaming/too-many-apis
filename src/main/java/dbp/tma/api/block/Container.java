package dbp.tma.api.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class Container extends net.minecraft.inventory.Container {
	private final Tile tile;

	public Container(InventoryPlayer playerInventory, Tile tile) {
		this.tile = tile;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotNum);
		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();
			if (slotNum == 2) {
				if (!this.mergeItemStack(slotStack, 3, 39, true)) return null;
				slot.onSlotChange(slotStack, stack);
			} else if (slotNum != 1 && slotNum != 0) {
				if (FurnaceRecipes.smelting().getSmeltingResult(slotStack) != null) {
					if (!this.mergeItemStack(slotStack, 0, 1, false)) return null;
				} else if (TileEntityFurnace.isItemFuel(slotStack)) {
					if (!this.mergeItemStack(slotStack, 1, 2, false)) return null;
				} else if (slotNum < 30) {
					if (!this.mergeItemStack(slotStack, 30, 39, false)) return null;
				} else if (slotNum < 39 && !this.mergeItemStack(slotStack, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(slotStack, 3, 39, false)) {
				return null;
			}

			if (slotStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (slotStack.stackSize == stack.stackSize) return null;

			slot.onPickupFromSlot(player, slotStack);
		}
		return stack;
	}
}