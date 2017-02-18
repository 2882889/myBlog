
//package com.xueqiu.qa.appcrawler.report

import org.scalatest.FunSuite
class AppCrawler_0 extends FunSuite {
  override def suiteName="autoTest"

  test("clickedIndex=0 xpath=startupActions-swipe(\"left\")-0"){
    
    markup("<img src='0_autoTest_left.ps.jpg' width='400' /><br></br><p>after clicked</p><img src='1_autoTest_left.ori.jpg' width='400' />")

  }
        
  test("clickedIndex=1 xpath=startupActions-swipe(\"left\")-1"){
    
    markup("<img src='1_autoTest_left.ps.jpg' width='400' /><br></br><p>after clicked</p><img src='2_autoTest_down.ori.jpg' width='400' />")

  }
        
  test("clickedIndex=2 xpath=startupActions-swipe(\"down\")-2"){
    
    markup("<img src='2_autoTest_down.ps.jpg' width='400' /><br></br><p>after clicked</p><img src='3_autoTest_.ori.jpg' width='400' />")

  }
        
  test("clickedIndex=3 xpath=startupActions-println(driver)-3"){
    
    markup("<img src='3_autoTest_.ps.jpg' width='400' /><br></br><p>after clicked</p><img src='4_autoTest_autoTest-test.ori.jpg' width='400' />")

  }
        
  test("clickedIndex=4 xpath=//UIAApplication[@label=\"autoTest\"]/UIAWindow/UIAButton[@label=\"test\"]"){
    
    markup("<img src='4_autoTest_autoTest-test.ps.jpg' width='400' /><br></br><p>after clicked</p><img src='7_autoTest_autoTest-test.ori.jpg' width='400' />")

  }
        
}
      