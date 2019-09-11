package io.github.tubb.multichannel

class BundleData {
    String src
    String targetFileName

    @Override
    String toString() {
        return String.format('{%s, %s}', src, targetFileName)
    }
}