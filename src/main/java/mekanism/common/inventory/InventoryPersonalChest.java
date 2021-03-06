package mekanism.common.inventory;

import mekanism.api.sustained.ISustainedInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.Constants.NBT;

//TODO: Should this be rewritten to be a mekanism inventory
public class InventoryPersonalChest extends Inventory {

    private final ItemStack itemStack;
    private boolean reading;
    public Hand currentHand;

    public InventoryPersonalChest(ItemStack stack, Hand hand) {
        super(55);
        itemStack = stack;
        currentHand = hand;
        read();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (!reading) {
            write();
        }
    }

    @Override
    public void openInventory(PlayerEntity player) {
        read();
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        write();
    }

    public void write() {
        ListNBT tagList = new ListNBT();
        for (int slotCount = 0; slotCount < getSizeInventory(); slotCount++) {
            ItemStack stack = getStackInSlot(slotCount);
            if (!stack.isEmpty()) {
                CompoundNBT tagCompound = new CompoundNBT();
                tagCompound.put("Item", stack.write(new CompoundNBT()));
                if (stack.getCount() > stack.getMaxStackSize()) {
                    tagCompound.putInt("SizeOverride", stack.getCount());
                }
                tagCompound.putByte("Slot", (byte) slotCount);
                tagList.add(tagCompound);
            }
        }
        if (!getStack().isEmpty()) {
            ((ISustainedInventory) getStack().getItem()).setInventory(tagList, getStack());
        }
    }

    public void read() {
        if (reading) {
            return;
        }
        reading = true;
        ListNBT tagList = ((ISustainedInventory) getStack().getItem()).getInventory(getStack());
        if (tagList != null) {
            for (int tagCount = 0; tagCount < tagList.size(); tagCount++) {
                CompoundNBT tagCompound = tagList.getCompound(tagCount);
                byte slotID = tagCompound.getByte("Slot");
                if (slotID >= 0 && slotID < getSizeInventory()) {
                    ItemStack stack = ItemStack.EMPTY;
                    if (tagCompound.contains("Item", NBT.TAG_COMPOUND)) {
                        stack = ItemStack.read(tagCompound.getCompound("Item"));
                        if (tagCompound.contains("SizeOverride", NBT.TAG_INT)) {
                            stack.setCount(tagCompound.getInt("SizeOverride"));
                        }
                    }
                    setInventorySlotContents(slotID, stack);
                }
            }
        }
        reading = false;
    }

    public ItemStack getStack() {
        return itemStack;
    }
}