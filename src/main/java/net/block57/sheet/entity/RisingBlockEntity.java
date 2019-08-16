package net.block57.sheet.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Iterator;

public class RisingBlockEntity extends FallingBlockEntity {

    private BlockState block;
    private boolean isFastBlock;

    public RisingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world)
	{
        super(entityType, world);
        this.block = Blocks.SAND.getDefaultState();
        this.dropItem = true;
    }

    public RisingBlockEntity(World world, double double_1, double double_2, double double_3, BlockState blockState, boolean isFast)
    {
		super(world, double_1, double_2, double_3, blockState);
		//this(Sheet.RISING_BLOCK, world);
		this.block = blockState;
		this.inanimate = true;
		this.setPosition(double_1, double_2 + (double)((1.0F - this.getHeight()) / 2.0F), double_3);
		this.setVelocity(Vec3d.ZERO);
		this.prevX = double_1;
		this.prevY = double_2;
		this.prevZ = double_3;
		this.setFallingBlockPos(new BlockPos(this));
		this.isFastBlock = isFast;
    }

	@Environment(EnvType.CLIENT)
	public BlockPos getFallingBlockPos() {
		return new BlockPos(this);
	}

	public void tick() {
		if (this.block.isAir()) {
			this.remove();
		} else {
			this.prevX = this.x;
			this.prevY = this.y;
			this.prevZ = this.z;
			Block block_1 = this.block.getBlock();
			BlockPos blockPos_2;
			if (this.timeFalling++ == 0) {
				blockPos_2 = new BlockPos(this);
				if (this.world.getBlockState(blockPos_2).getBlock() == block_1) {
					this.world.clearBlockState(blockPos_2, false);
				} else if (!this.world.isClient) {
					this.remove();
					return;
				}
			}

			if (!this.hasNoGravity()) {
				if (!isFastBlock)
					this.setVelocity(this.getVelocity().add(0.0D, 0.04D, 0.0D));
				else
					this.setVelocity(this.getVelocity().add(0.0D, -0.04D, 0.0D));
			}

			this.move(MovementType.SELF, this.getVelocity());
			if (!this.world.isClient) {
				blockPos_2 = new BlockPos(this);
				boolean boolean_1 = this.block.getBlock() instanceof ConcretePowderBlock;
				boolean boolean_2 = boolean_1 && this.world.getFluidState(blockPos_2).matches(FluidTags.WATER);
				double double_1 = this.getVelocity().lengthSquared();
				if (boolean_1 && double_1 > 1.0D) {
					BlockHitResult blockHitResult_1 = this.world.rayTrace(new RayTraceContext(new Vec3d(this.prevX, this.prevY, this.prevZ), new Vec3d(this.x, this.y, this.z), RayTraceContext.ShapeType.COLLIDER, RayTraceContext.FluidHandling.SOURCE_ONLY, this));
					if (blockHitResult_1.getType() != HitResult.Type.MISS && this.world.getFluidState(blockHitResult_1.getBlockPos()).matches(FluidTags.WATER)) {
						blockPos_2 = blockHitResult_1.getBlockPos();
						boolean_2 = true;
					}
				}

				if (!isFastBlock)
				{
					BlockHitResult isTopBlocked = this.world.rayTrace(new RayTraceContext(new Vec3d(this.x, this.y, this.z), new Vec3d(this.x, this.y+1, this.z), RayTraceContext.ShapeType.COLLIDER, RayTraceContext.FluidHandling.NONE, this));
					if (isTopBlocked.getType() != HitResult.Type.MISS)
						this.onGround = true;
				}

				if (!this.onGround && !boolean_2) {
					if (!this.world.isClient && (this.timeFalling > 100 && (blockPos_2.getY() < 1 || blockPos_2.getY() > 256) || this.timeFalling > 600)) {
						if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
							this.dropItem(block_1);
						}

						this.remove();
					}
				} else {
					BlockState blockState_1 = this.world.getBlockState(blockPos_2);
					this.setVelocity(this.getVelocity().multiply(0.7D, -0.5D, 0.7D));
					if (blockState_1.getBlock() != Blocks.MOVING_PISTON) {
						this.remove();
						if (true) {
							boolean boolean_3 = blockState_1.canReplace(new AutomaticItemPlacementContext(this.world, blockPos_2, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
							boolean boolean_4 = this.block.canPlaceAt(this.world, blockPos_2);
							if (boolean_3 && boolean_4) {
								if (this.block.contains(Properties.WATERLOGGED) && this.world.getFluidState(blockPos_2).getFluid() == Fluids.WATER) {
									this.block = (BlockState)this.block.with(Properties.WATERLOGGED, true);
								}

								if (this.world.setBlockState(blockPos_2, this.block, 3)) {
									if (block_1 instanceof FallingBlock) {
										((FallingBlock)block_1).onLanding(this.world, blockPos_2, this.block, blockState_1);
									}

									if (this.blockEntityData != null && block_1 instanceof BlockEntityProvider) {
										BlockEntity blockEntity_1 = this.world.getBlockEntity(blockPos_2);
										if (blockEntity_1 != null) {
											CompoundTag compoundTag_1 = blockEntity_1.toTag(new CompoundTag());
											Iterator var12 = this.blockEntityData.getKeys().iterator();

											while(var12.hasNext()) {
												String string_1 = (String)var12.next();
												Tag tag_1 = this.blockEntityData.getTag(string_1);
												if (!"x".equals(string_1) && !"y".equals(string_1) && !"z".equals(string_1)) {
													compoundTag_1.put(string_1, tag_1.copy());
												}
											}

											blockEntity_1.fromTag(compoundTag_1);
											blockEntity_1.markDirty();
										}
									}
								} else if (this.dropItem /*&& this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)*/) {
									getServer().getWorld(dimension).createExplosion(null, blockPos_2.getX(), blockPos_2.getY(), blockPos_2.getZ(), 2.5f, Explosion.DestructionType.BREAK);
									LightningEntity lightningEntity = new LightningEntity(world, blockPos_2.getX(), blockPos_2.getY(), blockPos_2.getZ(), false);
									getServer().getWorld(dimension).addLightning(lightningEntity);
								}
							} else if (this.dropItem /*&& this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)*/) {
								getServer().getWorld(dimension).createExplosion(null, blockPos_2.getX(), blockPos_2.getY(), blockPos_2.getZ(), 2.5f, Explosion.DestructionType.BREAK);
								LightningEntity lightningEntity = new LightningEntity(world, blockPos_2.getX(), blockPos_2.getY(), blockPos_2.getZ(), false);
								getServer().getWorld(dimension).addLightning(lightningEntity);
							}
						} else if (block_1 instanceof FallingBlock) {
							((FallingBlock)block_1).onDestroyedOnLanding(this.world, blockPos_2);
						}
					}
				}
			}

			this.setVelocity(this.getVelocity().multiply(0.98D));
		}

		this.setFallingBlockPos(new BlockPos(this));
	}

}
