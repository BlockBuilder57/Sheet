package net.block57.sheet.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public class SheetToolMaterial implements ToolMaterial {
	private final int miningLevel;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Lazy<Ingredient> repairIngredient;

	public SheetToolMaterial(int level, int durability, float speed, float damage, int enchantrank, Supplier<Ingredient> repairMaterial) {
		this.miningLevel = level;
		this.itemDurability = durability;
		this.miningSpeed = speed;
		this.attackDamage = damage;
		this.enchantability = enchantrank;
		this.repairIngredient = new Lazy(repairMaterial);
	}

	public int getDurability() {
		return this.itemDurability;
	}

	public float getMiningSpeed() {
		return this.miningSpeed;
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	public int getMiningLevel() {
		return this.miningLevel;
	}

	public int getEnchantability() {
		return this.enchantability;
	}

	public Ingredient getRepairIngredient() {
		return (Ingredient)this.repairIngredient.get();
	}
}
