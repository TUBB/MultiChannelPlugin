package io.github.tubb.multichannel

import org.gradle.api.Project;

/**
 * channelFlavorConfig dsl handler
 * Created by tubingbing on 18/3/27.
 */

class ChannelFlavorConfigExtensionHandler {

    static void createChannel(Project project) {
        def appChannelExtension = project['appChannel']
        if (appChannelExtension == null) {
            throw new RuntimeException('Please config the appChannel dsl')
        }
        def channelFlavorConfigExtension = appChannelExtension.channelFlavorConfig
        if (channelFlavorConfigExtension != null) {
            String channelConfigFilePath = channelFlavorConfigExtension.channelConfigFilePath
            File channelConfigFile = new File(channelConfigFilePath)
            if (channelConfigFile.isDirectory() || !channelConfigFile.exists()) {
                throw new IllegalArgumentException("[${channelConfigFilePath}] is not a valid file path")
            }
            Closure configProductFlavor = channelFlavorConfigExtension.configProductFlavor
            if (configProductFlavor == null) {
                throw new IllegalArgumentException('configProductFlavor cant not be null')
            }
            channelConfigFile.eachLine { name ->
                if (!name.startsWith("#")) {
                    def productFlavors = project.android.productFlavors
                    productFlavors.create(name, configProductFlavor(name))
                }
            }
        } else {
            println('Can\'t found channelFlavorConfig dsl, this opt will be ignore')
        }
    }
}
