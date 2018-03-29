package io.github.tubb.multichannel;

/**
 * apkOutputConfig dsl
 * Created by tubingbing on 18/3/27.
 */

class BundleOutputConfigExtension {
    /**
     * Apk output absolute dir
     */
    String outputDir
    /**
     * Rename bundle file
     */
    Closure renameBundleFileName
}
