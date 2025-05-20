package fr.augma.othoumod.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class CompactPayload implements CustomPacketPayload {

    public static final StreamCodec<FriendlyByteBuf, CompactPayload> STREAM_CODEC = CustomPacketPayload.codec(CompactPayload::write, CompactPayload::new);
    public static final CustomPacketPayload.Type<CompactPayload> ID = CustomPacketPayload.createType("othoumod/compact");

    private int slotId = 0;

    public CompactPayload(FriendlyByteBuf friendlyByteBuf) {
        this.read(friendlyByteBuf);
    }

    public CompactPayload(final int slotId) {
        this.slotId = slotId;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    private void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(this.slotId);
    }

    private void read(FriendlyByteBuf friendlyByteBuf) {
        this.slotId = friendlyByteBuf.readInt();
    }

    public int getSlotId() {
        return this.slotId;
    }
}
