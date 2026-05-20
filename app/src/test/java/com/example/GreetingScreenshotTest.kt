package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.ui.PortfolioScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.PortfolioViewModel
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun greeting_screenshot() {
    val viewModel = PortfolioViewModel()
    composeTestRule.setContent { MyApplicationTheme { PortfolioScreen(viewModel = viewModel) } }

    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/greeting.png")
  }

  @Test
  fun test_all_tabs_and_chat() {
    val viewModel = PortfolioViewModel()
    composeTestRule.setContent { MyApplicationTheme { PortfolioScreen(viewModel = viewModel) } }

    // Click on Skills tab
    composeTestRule.onNodeWithTag("portfolio_tab_skills").performClick()
    composeTestRule.waitForIdle()

    // Click on Projects tab
    composeTestRule.onNodeWithTag("portfolio_tab_projects").performClick()
    composeTestRule.waitForIdle()

    // Click on Timeline tab
    composeTestRule.onNodeWithTag("portfolio_tab_timeline").performClick()
    composeTestRule.waitForIdle()

    // Click on Chatbot tab
    composeTestRule.onNodeWithTag("portfolio_tab_chatbot").performClick()
    composeTestRule.waitForIdle()

    // Test entering text and sending
    composeTestRule.onNodeWithTag("chat_input").performTextInput("Hi there")
    composeTestRule.onNodeWithTag("send_button").performClick()
    composeTestRule.waitForIdle()
  }
}
