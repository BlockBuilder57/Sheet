package net.block57.sheet.items;

import net.block57.sheet.Sheet;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class NigraCoin extends Item {

    public NigraCoin(Settings settings)
    {
        super(settings);
    }

    /*@Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
    {
        if (world != null && world.isClient)
            tooltip.add(new TranslatableText("item.sheet.test_item.tooltip", ((ClientPlayerEntity) world.getPlayers().toArray()[0]).getName()));
    }*/

    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
    {
		world.playSoundFromEntity(playerEntity, playerEntity, Sheet.NIGRA_EVENT, SoundCategory.VOICE, 0.25f, 1f);

        if (!playerEntity.abilities.creativeMode)
			playerEntity.getStackInHand(hand).decrement(1);

		return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
    }
}
