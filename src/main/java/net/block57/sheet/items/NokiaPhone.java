package net.block57.sheet.items;

import net.block57.sheet.client.gui.screen.ingame.NokiaPhoneScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class NokiaPhone extends Item {

	public NokiaPhone(Settings settings)
	{
		super(settings);
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
	{
		ItemStack itemStack = playerEntity.getStackInHand(hand);
		MinecraftClient.getInstance().openScreen(new NokiaPhoneScreen(playerEntity, itemStack, hand));
		return new TypedActionResult<>(ActionResult.SUCCESS, itemStack);
	}
}
