package net.block57.sheet.client.gui.screen.ingame;

import net.block57.sheet.Sheet;
import net.block57.sheet.items.NokiaPhone;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;

public class NokiaPhoneScreen extends Screen {

	public PlayerEntity player;
	public ItemStack itemStack;
	private ButtonWidget buttonExplode;

	public NokiaPhoneScreen(PlayerEntity playerEntity, ItemStack itemStack, Hand hand)
	{
		super(new LiteralText("balls"));
		this.player = playerEntity;
		this.itemStack = itemStack;
	}

	protected void init()
	{
		buttonExplode = addButton(new ButtonWidget(this.width/2 + 40, this.height/2 - 10, 80, 20, new TranslatableText("item.sheet.nokia_phone.explode").asString(), (x) -> {
			this.minecraft.openScreen(null);
			if (itemStack.getItem() instanceof NokiaPhone)
				itemStack.decrement(1);
			CreeperEntity awman = new CreeperEntity(EntityType.CREEPER, player.getEntityWorld());

			CompoundTag nbtTag = new CompoundTag();
			nbtTag.putBoolean("ignited", true);
			nbtTag.putBoolean("NoAI", true);
			nbtTag.putShort("Fuse", (short)465);

			awman.initialize(player.getEntityWorld(), player.getEntityWorld().getLocalDifficulty(player.getBlockPos()), SpawnType.COMMAND, null, null);
			awman.addPotionEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 460, 1, false, false));
			awman.readCustomDataFromTag(nbtTag);
			awman.setPositionAndAngles(player.getBlockPos(), 0f, 0f);
			awman.startRiding(player);
			player.world.spawnEntity(awman);

			player.getEntityWorld().playSoundFromEntity(null, awman, Sheet.NOKIA_ARABIC_EVENT, SoundCategory.MASTER, 0.5f, 1f);
		}));

		super.init();
	}

	public int getStringWidth(String string_1) {
		return this.font.getStringWidth(this.font.isRightToLeft() ? this.font.mirror(string_1) : string_1);
	}

	public void render(int int_1, int int_2, float float_1)
	{
		this.renderBackground();
		this.setFocused(null);

		Text header = new TranslatableText("item.sheet.nokia_phone");
		this.font.draw(header.asString(), this.width/2 - getStringWidth(header.asString())/2, this.height/2 - 40, 0xFFFFFF);

		super.render(int_1, int_2, float_1);
	}
}
