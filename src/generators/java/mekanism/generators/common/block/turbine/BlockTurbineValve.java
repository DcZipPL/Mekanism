package mekanism.generators.common.block.turbine;

import javax.annotation.Nonnull;
import mekanism.api.block.IHasTileEntity;
import mekanism.api.block.ISupportsComparator;
import mekanism.common.base.ILangEntry;
import mekanism.common.block.basic.BlockBasicMultiblock;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.interfaces.IHasGui;
import mekanism.common.inventory.container.ContainerProvider;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.SecurityUtils;
import mekanism.common.util.text.TextComponentUtil;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.inventory.container.turbine.TurbineContainer;
import mekanism.generators.common.registries.GeneratorsTileEntityTypes;
import mekanism.generators.common.tile.turbine.TileEntityTurbineValve;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockTurbineValve extends BlockBasicMultiblock implements IHasTileEntity<TileEntityTurbineValve>, ISupportsComparator, IHasGui<TileEntityTurbineValve>,
      IHasDescription {

    public BlockTurbineValve() {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(3.5F, 8F));
    }

    @Override
    @Deprecated
    public float getPlayerRelativeBlockHardness(BlockState state, @Nonnull PlayerEntity player, @Nonnull IBlockReader world, @Nonnull BlockPos pos) {
        return SecurityUtils.canAccess(player, MekanismUtils.getTileEntity(world, pos)) ? super.getPlayerRelativeBlockHardness(state, player, world, pos) : 0.0F;
    }

    @Override
    public INamedContainerProvider getProvider(TileEntityTurbineValve tile) {
        return new ContainerProvider(TextComponentUtil.translate(getTranslationKey()), (i, inv, player) -> new TurbineContainer(i, inv, tile));
    }

    @Override
    public TileEntityType<TileEntityTurbineValve> getTileType() {
        return GeneratorsTileEntityTypes.TURBINE_VALVE.getTileEntityType();
    }

    @Nonnull
    @Override
    public ILangEntry getDescription() {
        return GeneratorsLang.DESCRIPTION_TURBINE_VALVE;
    }
}