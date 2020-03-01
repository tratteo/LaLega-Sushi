package net.la.lega.mod.item;

import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class Rice extends Item
{
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID, "rice");
    
    public static final float saturation = 1F;
    public static final int hunger = 2;

    public Rice() 
    {
        super(new Item.Settings().group(ItemGroup.FOOD)
                .food(new FoodComponent.Builder()
                .hunger(hunger)
                .saturationModifier(saturation)
                .build())
            );
    }
}