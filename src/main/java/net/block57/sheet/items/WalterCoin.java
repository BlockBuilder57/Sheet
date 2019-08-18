package net.block57.sheet.items;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class WalterCoin extends Item {

	public WalterCoin(Item.Settings settings)
	{
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
	{
		if (world != null && world.isClient)
			tooltip.add(new TranslatableText("item.sheet.waltercoin.tooltip").formatted(Formatting.AQUA));
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
	{
		if (world != null && world.isClient)
			MinecraftClient.getInstance().player.sendChatMessage(new TranslatableText(String.format("item.sheet.waltercoin.phrase%s", RANDOM.nextInt(8)), MinecraftClient.getInstance().player.getName()).asTruncatedString(256));

		if (!playerEntity.abilities.creativeMode)
			playerEntity.getStackInHand(hand).decrement(1);

		return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
	}
}
