package com.jj.core.apk

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class JiangApkInstallerTest {

    @Test
    fun isApkFileReturnsTrueForApkSuffixIgnoringCase() {
        assertTrue(JiangApkInstaller.isApkFile(File("update.apk")))
        assertTrue(JiangApkInstaller.isApkFile(File("update.APK")))
    }

    @Test
    fun isApkFileReturnsFalseForNonApkSuffix() {
        assertFalse(JiangApkInstaller.isApkFile(File("update.zip")))
        assertFalse(JiangApkInstaller.isApkFile(File("update")))
    }
}
