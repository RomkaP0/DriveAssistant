package com.romka_po.driveassistant

import com.romka_po.driveassistant.utils.VerifyUtils
import org.junit.Assert
import org.junit.Test

class TextUtilsTest {
    var verifyUtils: VerifyUtils = VerifyUtils()

    @Test
    fun checkEmailFun1() {
        verifyUtils
        Assert.assertEquals(false, verifyUtils.checkEmail("261002@sibmail.com"))
    }
    @Test
    fun checkEmailFun2() {
        verifyUtils
        Assert.assertEquals(false, verifyUtils.checkEmail("261002"))
    }
    @Test
    fun checkEmailFun3() {
        verifyUtils
        Assert.assertEquals(false, verifyUtils.checkEmail("@sibmail.com"))
    }
    @Test
    fun checkEmailFun4() {
        verifyUtils
        Assert.assertEquals(false, verifyUtils.checkEmail("261002@sibmail"))
    }
    @Test
    fun checkEmailFun5() {
        verifyUtils
        Assert.assertEquals(false, verifyUtils.checkEmail("261002@sibmail."))
    }
    @Test
    fun checkEmailFun6() {
        verifyUtils
        Assert.assertEquals(true, verifyUtils.checkEmail("rvp8@tpu.ru"))
    }

    @Test
    fun checkNameFun1() {
        Assert.assertEquals(false, verifyUtils.checkName("Chuck Norris"))
    }

    @Test
    fun checkNameFun2() {
        Assert.assertEquals(true, verifyUtils.checkName("roman"))
    }

    @Test
    fun checkNameFun3() {
        Assert.assertEquals(true, verifyUtils.checkName("Chuck-Norris"))
    }
    @Test
    fun checkNameFun4() {
        Assert.assertEquals(true, verifyUtils.checkName("Ромка-По"))
    }
}

