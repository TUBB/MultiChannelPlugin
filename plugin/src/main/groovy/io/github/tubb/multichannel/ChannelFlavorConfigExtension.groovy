package io.github.tubb.multichannel;

/**
 * channelFlavorConfig dsl
 * Created by tubingbing on 18/3/27.
 */

class ChannelFlavorConfigExtension {
    /**
     * Channels config file
     */
    String channelConfigFilePath
    /**
     * Custom product flavor config
     */
    Closure configProductFlavor
}
