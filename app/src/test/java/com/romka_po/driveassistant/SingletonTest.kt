package com.romka_po.driveassistant
import com.romka_po.driveassistant.model.MapOfResource
import org.junit.Assert
import org.junit.Test

class SingletonTest {

    @Test
    fun isSingleton1(){
        Assert.assertEquals(true, MapOfResource==MapOfResource)
    }
    @Test
    fun isSingleton2(){
        Assert.assertEquals(true, MainActivity()==MainActivity())
    }
}