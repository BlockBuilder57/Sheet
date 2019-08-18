package net.block57.sheet.items;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

public class BanHammer extends SwordItem {
	public BanHammer(ToolMaterial material, int damage, float speed, Item.Settings settings)
	{
		super(material, damage, speed, settings);
	}

	public boolean canMine(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
		return true;
	}

	public boolean postHit(ItemStack itemStack, LivingEntity livingEntityTarget, LivingEntity livingEntitySrc)
	{
		if (livingEntityTarget instanceof PlayerEntity && !((PlayerEntity) livingEntityTarget).abilities.creativeMode)
		{
			itemStack.damage(1, livingEntityTarget, (livingEntity_1x) -> {
				livingEntity_1x.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
			});
		}

		if (livingEntityTarget instanceof ServerPlayerEntity)
		{
			ServerPlayerEntity banTarget = (ServerPlayerEntity)livingEntityTarget;
			ServerPlayerEntity srcPlayer = (ServerPlayerEntity)livingEntitySrc;
			if (srcPlayer.getServer() != null && srcPlayer.server.getPermissionLevel(srcPlayer.getGameProfile()) < 3)
				banTarget = srcPlayer;
			if (itemStack.hasCustomName()) {
				banTarget.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.banned.reason", itemStack.getName()));
				if (banTarget.getServer() != null)
					banTarget.server.getPlayerManager().getUserBanList().add(new BannedPlayerEntry(banTarget.getGameProfile(), null, null, null, itemStack.getName().asTruncatedString(256)));
			}
			else {
				banTarget.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.banned"));
				if (banTarget.getServer() != null)
					banTarget.server.getPlayerManager().getUserBanList().add(new BannedPlayerEntry(banTarget.getGameProfile()));
			}
		}
		else
		{
			livingEntityTarget.remove();
			if (livingEntitySrc.getServer() != null)
				livingEntitySrc.getServer().getPlayerManager().sendToAll(new TranslatableText("multiplayer.player.left", livingEntityTarget.getDisplayName()).formatted(Formatting.YELLOW));
		}

		return true;
	}

	public boolean postMine(ItemStack itemStack, World world, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
		if (livingEntity instanceof PlayerEntity && !((PlayerEntity) livingEntity).abilities.creativeMode)
		{
			if (blockState.getHardness(world, blockPos) != 0.0F) {
				itemStack.damage(2, livingEntity, (livingEntity_1x) -> {
					livingEntity_1x.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
				});
			}
		}

		if (livingEntity.getServer() != null)
			livingEntity.getServer().getPlayerManager().sendToAll(new TranslatableText("multiplayer.player.left", blockState.getBlock().asItem().getStackForRender().toHoverableText().formatted(Formatting.YELLOW)).formatted(Formatting.YELLOW));

		return true;
	}
}
