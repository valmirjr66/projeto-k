package com.exemplo.iot;

import java.awt.image.BufferedImage;

public interface OperationalSystemInterface {
    BufferedImage getSnapshotFromCamera();

    void sendRadioSignal(String channel, byte[] bytesToSend);
}
