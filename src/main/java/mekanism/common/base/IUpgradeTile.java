package mekanism.common.base;

import mekanism.api.Upgrade;
import mekanism.common.tile.component.TileComponentUpgrade;

public interface IUpgradeTile {

    default boolean supportsUpgrades() {
        return true;
    }

    TileComponentUpgrade getComponent();

    void recalculateUpgrades(Upgrade upgradeType);
}