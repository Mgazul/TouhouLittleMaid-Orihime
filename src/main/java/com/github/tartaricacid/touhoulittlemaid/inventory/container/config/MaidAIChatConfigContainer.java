package com.github.tartaricacid.touhoulittlemaid.inventory.container.config;

import com.github.tartaricacid.touhoulittlemaid.ai.manager.site.ClientAvailableSitesSync;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.AbstractMaidContainer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;

public class MaidAIChatConfigContainer extends AbstractMaidContainer {
    public record Data(int entityId, @Nullable CompoundTag configData,
                       Pair<Map<String, Map<String, String>>, Map<String, Map<String, String>>> sites) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, Data::entityId,
                ByteBufCodecs.COMPOUND_TAG, Data::configData,
                new StreamCodec<>() {
                    @Override
                    public void encode(RegistryFriendlyByteBuf buf, Pair<Map<String, Map<String, String>>, Map<String, Map<String, String>>> sites) {
                        ClientAvailableSitesSync.writeToNetwork(buf);
                    }

                    @Override
                    public Pair<Map<String, Map<String, String>>, Map<String, Map<String, String>>> decode(RegistryFriendlyByteBuf buf) {
                        return ClientAvailableSitesSync.readFromNetwork(buf);
                    }
                }, Data::sites,
                Data::new
        );
    }

    public static final MenuType<MaidAIChatConfigContainer> TYPE = new ExtendedScreenHandlerType<>((ExtendedScreenHandlerType.ExtendedFactory<MaidAIChatConfigContainer, Data>) MaidAIChatConfigContainer::create, new StreamCodec<>() {
        @Override
        public Data decode(RegistryFriendlyByteBuf buf) {
            return new Data(buf.readInt(), buf.readNbt(), ClientAvailableSitesSync.readFromNetwork(buf));
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, Data data) {
            buf.writeInt(data.entityId);
            buf.writeNbt(data.configData);
            ClientAvailableSitesSync.writeToNetwork(buf);
        }
    });

    private static final int PLAYER_INVENTORY_SIZE = 27;

    private final CompoundTag configData;
    private final Map<String, Map<String, String>> llmSites;
    private final Map<String, Map<String, String>> ttsSites;

    public MaidAIChatConfigContainer(int id, Inventory inventory, int entityId,
                                     @Nullable CompoundTag configData,
                                     Map<String, Map<String, String>> llmSites,
                                     Map<String, Map<String, String>> ttsSites) {
        super(TYPE, id, inventory, entityId);
        this.configData = configData;
        this.llmSites = llmSites;
        this.ttsSites = ttsSites;
    }

    public static MenuProvider create(EntityMaid maid) {
        return new ExtendedScreenHandlerFactory<Data>() {
            @Override
            public @NotNull Component getDisplayName() {
                return Component.literal("Maid AI Chat Config Container");
            }

            @Override
            public AbstractContainerMenu createMenu(int index, @NotNull Inventory playerInventory, @NotNull Player player) {
                int entityId = maid.getId();
                CompoundTag configData = maid.getAiChatManager().writeToTag(new CompoundTag());
                return new MaidAIChatConfigContainer(index, playerInventory, entityId, configData,
                        ClientAvailableSitesSync.getClientLLMSites(),
                        ClientAvailableSitesSync.getClientTTSSites());
            }

            @Override
            public Data getScreenOpeningData(ServerPlayer player) {
                int entityId = maid.getId();
                CompoundTag configData = maid.getAiChatManager().writeToTag(new CompoundTag());
                return new Data(entityId, configData, Pair.of(ClientAvailableSitesSync.getClientLLMSites(), ClientAvailableSitesSync.getClientTTSSites()));
            }

            @Override
            public boolean shouldCloseCurrentScreen() {
                return false;
            }

/*            @Override
            public boolean shouldTriggerClientSideContainerClosingOnOpen() {
                return false;
            }*/
        };
    }

    @NotNull
    private static MaidAIChatConfigContainer create(int windowId, Inventory inv, Data data) {
        int entityId = data.entityId;
        CompoundTag configData = data.configData;
        var sites = data.sites;
        return new MaidAIChatConfigContainer(windowId, inv, entityId, configData, sites.getLeft(), sites.getRight());
    }

    public CompoundTag getConfigData() {
        return configData;
    }

    public Map<String, Map<String, String>> getLLMSites() {
        return llmSites;
    }

    public Map<String, Map<String, String>> getTTSSites() {
        return ttsSites;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack stack1 = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack2 = slot.getItem();
            stack1 = stack2.copy();
            if (index < PLAYER_INVENTORY_SIZE) {
                if (!this.moveItemStackTo(stack2, PLAYER_INVENTORY_SIZE, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack2, 0, PLAYER_INVENTORY_SIZE, true)) {
                return ItemStack.EMPTY;
            }
            if (stack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return stack1;
    }
}