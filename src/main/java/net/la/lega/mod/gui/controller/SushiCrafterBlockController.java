package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.widget.*;
import net.la.lega.mod.entity.SushiCrafterBlockEntity;
import net.la.lega.mod.gui.controller.abstraction.AbstractBlockController;
import net.la.lega.mod.recipe.SushiCraftingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SushiCrafterBlockController extends AbstractBlockController
{
    public SushiCrafterBlockController(int syncId, PlayerInventory playerInventory, BlockContext context)
    {
        super(SushiCraftingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context), context);
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        
        List<WItemSlot> ingredientSlots = new ArrayList<>();
        for(int i = 0; i < SushiCrafterBlockEntity.INGREDIENTS_SLOTS.length; i++)
        {
            ingredientSlots.add(WItemSlot.of(blockInventory, SushiCrafterBlockEntity.INGREDIENTS_SLOTS[i]));
        }
        List<WItemSlot> requiredSlots = new ArrayList<>();
        for(int i = 0; i < SushiCrafterBlockEntity.REQUIRED_SLOTS.length; i++)
        {
            requiredSlots.add(WItemSlot.of(blockInventory, SushiCrafterBlockEntity.REQUIRED_SLOTS[i]));
        }
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, SushiCrafterBlockEntity.OUTPUT_SLOT);
        WLabel title = new WLabel("Sushi Crafter", WLabel.DEFAULT_TEXT_COLOR);
        WBar progressBar = new WBar(new Identifier("lalegamod:textures/ui/progress_bg.png"), new Identifier("lalegamod:textures/ui/progress_bar.png"), 0, 1, WBar.Direction.RIGHT);
        WSprite heartIcon = new WSprite(new Identifier("minecraft:textures/item/heart_of_the_sea.png"));
        
        for(int i = 0; i < SushiCrafterBlockEntity.INGREDIENTS_SLOTS.length; i++)
        {
            root.add(ingredientSlots.get(i), 10 + (i * 23), 75);
        }
        
        for(int i = 0; i < SushiCrafterBlockEntity.REQUIRED_SLOTS.length; i++)
        {
            root.add(requiredSlots.get(i), 38 + (i * 36), 26);
        }
        
        root.add(heartIcon, 55, 48);
        root.add(title, 10, 2);
        root.add(outputSlot, 140, 48);
        root.add(progressBar, 104, 48, 26, 17);
        root.add(this.createPlayerInventoryPanel(), 0, 110);
        root.validate(this);
    }
}