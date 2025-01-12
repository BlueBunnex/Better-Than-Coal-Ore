package net.bluebunnex.btco;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class BetterThanCoalOre {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Item DIRT_PILE;
    public static Item SAND_PILE;
    public static Item GRAVEL_PILE;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

        DIRT_PILE = new TemplateItem(NAMESPACE.id("dirt_pile"))
                .setTranslationKey(NAMESPACE, "dirt_pile");

        SAND_PILE = new TemplateItem(NAMESPACE.id("sand_pile"))
                .setTranslationKey(NAMESPACE, "sand_pile");

        GRAVEL_PILE = new TemplateItem(NAMESPACE.id("gravel_pile"))
                .setTranslationKey(NAMESPACE, "gravel_pile");
    }
}
