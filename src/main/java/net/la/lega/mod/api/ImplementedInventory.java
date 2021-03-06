package net.la.lega.mod.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

import java.util.List;


@FunctionalInterface
public interface ImplementedInventory extends Inventory
{
    /**
     * Gets the item list of this inventory.
     * Must return the same instance every time it's called.
     *
     * @return the item list
     */
    DefaultedList<ItemStack> getItems();
    
    // Creation
    
    /**
     * Creates an inventory from the item list.
     *
     * @param items the item list
     * @return a new inventory
     */
    static ImplementedInventory of(DefaultedList<ItemStack> items)
    {
        return () -> items;
    }
    
    /**
     * Creates a new inventory with the size.
     *
     * @param size the inventory size
     * @return a new inventory
     */
    static ImplementedInventory ofSize(int size)
    {
        return of(DefaultedList.ofSize(size, ItemStack.EMPTY));
    }
    
    // Inventory
    
    /**
     * Returns the inventory size.
     *
     * <p>The default implementation returns the size of {@link #getItems()}.
     *
     * @return the inventory size
     */
    @Override
    default int getInvSize()
    {
        return getItems().size();
    }
    
    /**
     * @return true if this inventory has only empty stacks, false otherwise
     */
    @Override
    default boolean isInvEmpty()
    {
        for(int i = 0; i < getInvSize(); i++)
        {
            ItemStack stack = getInvStack(i);
            if(!stack.isEmpty())
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Gets the item in the slot.
     *
     * @param slot the slot
     * @return the item in the slot
     */
    @Override
    default ItemStack getInvStack(int slot)
    {
        return getItems().get(slot);
    }
    
    /**
     * Takes a stack of the size from the slot.
     *
     * <p>(default implementation) If there are less items in the slot than what are requested,
     * takes all items in that slot.
     *
     * @param slot the slot
     * @param count the item count
     * @return a stack
     */
    @Override
    default ItemStack takeInvStack(int slot, int count)
    {
        return Inventories.splitStack(this.getItems(), slot, count);
        // ItemStack result = Inventories.splitStack(getItems(), slot, count);
        // if (!result.isEmpty()) {
        //     markDirty();
        // }
        
        // return result;
    }
    
    /**
     * Removes the current stack in the {@code slot} and returns it.
     *
     * <p>The default implementation uses {@link Inventories#removeStack(List, int)}
     *
     * @param slot the slot
     * @return the removed stack
     */
    @Override
    default ItemStack removeInvStack(int slot)
    {
        return Inventories.removeStack(getItems(), slot);
    }
    
    /**
     * Replaces the current stack in the {@code slot} with the provided stack.
     *
     * <p>If the stack is too big for this inventory ({@link Inventory#getInvMaxStackAmount()}),
     * it gets resized to this inventory's maximum amount.
     *
     * @param slot the slot
     * @param stack the stack
     */
    @Override
    default void setInvStack(int slot, ItemStack stack)
    {
        this.getItems().set(slot, stack);
        if(stack.getCount() > this.getInvMaxStackAmount())
        {
            stack.setCount(this.getInvMaxStackAmount());
        }
    }
    
    @Override
    default void markDirty() {}
    
    /**
     * Clears {@linkplain #getItems() the item list}}.
     */
    @Override
    default void clear()
    {
        getItems().clear();
    }
    
    @Override
    default boolean canPlayerUseInv(PlayerEntity player)
    {
        return true;
    }
}