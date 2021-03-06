package mekanism.common.block.transmitter;

import mekanism.api.block.IHasTileEntity;
import mekanism.common.block.interfaces.ITieredBlock;
import mekanism.common.registries.MekanismTileEntityTypes;
import mekanism.common.tier.TransporterTier;
import mekanism.common.tile.transmitter.TileEntityLogisticalTransporter;
import net.minecraft.tileentity.TileEntityType;

public class BlockLogisticalTransporter extends BlockLargeTransmitter implements ITieredBlock<TransporterTier>, IHasTileEntity<TileEntityLogisticalTransporter> {

    private final TransporterTier tier;

    public BlockLogisticalTransporter(TransporterTier tier) {
        this.tier = tier;
    }

    @Override
    public TransporterTier getTier() {
        return tier;
    }

    @Override
    public TileEntityType<TileEntityLogisticalTransporter> getTileType() {
        switch (tier) {
            case ADVANCED:
                return MekanismTileEntityTypes.ADVANCED_LOGISTICAL_TRANSPORTER.getTileEntityType();
            case ELITE:
                return MekanismTileEntityTypes.ELITE_LOGISTICAL_TRANSPORTER.getTileEntityType();
            case ULTIMATE:
                return MekanismTileEntityTypes.ULTIMATE_LOGISTICAL_TRANSPORTER.getTileEntityType();
            case BASIC:
            default:
                return MekanismTileEntityTypes.BASIC_LOGISTICAL_TRANSPORTER.getTileEntityType();
        }
    }
}