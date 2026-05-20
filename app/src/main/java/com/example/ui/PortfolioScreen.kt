package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.Message
import com.example.viewmodel.PortfolioUiState
import com.example.viewmodel.PortfolioViewModel
import com.example.viewmodel.Project
import com.example.viewmodel.Sender
import com.example.viewmodel.TimelineItem

// Beautiful Bento Grid Palette (Lavender & Amethyst M3 style)
val BentoLightBg = Color(0xFFFEF7FF)
val BentoDarkText = Color(0xFF1D1B20)
val BentoSecondaryText = Color(0xFF49454F)
val BentoBrandPurple = Color(0xFF6750A4)
val BentoBannerBg = Color(0xFFD0BCFF)
val BentoDeepPurpleText = Color(0xFF21005D)
val BentoCardBg1 = Color(0xFFF3EDF7)
val BentoCardBg2 = Color(0xFFE8DEF8)
val BentoBorderColor = Color(0xFFCAC4D0)
val BentoPillBorderColor = Color(0xFFEADDFF)
val BentoWhite = Color(0xFFFFFFFF)

@Composable
fun PortfolioScreen(
    viewModel: PortfolioViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    // Background Container (Bento Theme Soft Lavender)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BentoLightBg)
            .imePadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            
            // WHLLdev Brand Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(BentoBrandPurple),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "W",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "WHLLdev.me",
                            color = BentoDarkText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Full-Stack Developer",
                            color = BentoSecondaryText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                IconButton(
                    onClick = {
                        try {
                            uriHandler.openUri("mailto:wilkaxyz15@gmail.com")
                        } catch (e: Exception) {
                            android.widget.Toast.makeText(
                                context,
                                "Email client not found. Copied wilkaxyz15@gmail.com to clipboard",
                                android.widget.Toast.LENGTH_LONG
                            ).show()
                            val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                            val clip = android.content.ClipData.newPlainText("email", "wilkaxyz15@gmail.com")
                            clipboard.setPrimaryClip(clip)
                        }
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(BentoCardBg2)
                        .testTag("contact_email")
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Contact email",
                        tint = BentoDeepPurpleText,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // High Precision Hero Panel (Bento Amethyst Header)
            HeroProfileSummary()

            Spacer(modifier = Modifier.height(14.dp))

            // Bento Pill Navigation Tab Row
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = BentoBrandPurple,
                divider = {},
                edgePadding = 16.dp,
                indicator = {}, // Custom indication handled inside Tab layout
                modifier = Modifier.fillMaxWidth()
            ) {
                val tabs = listOf("Skills & Tech", "Architectures", "Career Records", "Ask HireBot AI")
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTab == index
                    val tabBgColor by animateColorAsState(
                        targetValue = if (isSelected) BentoCardBg2 else Color.Transparent,
                        label = "tab_bg"
                    )
                    val tabTextColor by animateColorAsState(
                        targetValue = if (isSelected) BentoDeepPurpleText else BentoSecondaryText,
                        label = "tab_text"
                    )

                    Tab(
                        selected = isSelected,
                        onClick = { selectedTab = index },
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 6.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(tabBgColor)
                            .testTag(
                                when (index) {
                                    0 -> "portfolio_tab_skills"
                                    1 -> "portfolio_tab_projects"
                                    2 -> "portfolio_tab_timeline"
                                    else -> "portfolio_tab_chatbot"
                                }
                            ),
                        text = {
                            Text(
                                text = title,
                                color = tabTextColor,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Active Tab Content Area - fluid weighted block
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (selectedTab) {
                    0 -> SkillsLayout(uiState)
                    1 -> ProjectsLayout(uiState)
                    2 -> TimelineLayout(uiState)
                    3 -> ChatBotLayout(uiState, viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeroProfileSummary() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = BentoBannerBg),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "INTRODUCTION",
                color = BentoDeepPurpleText.copy(alpha = 0.7f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
            
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile avatar with Bento brand ring
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(BentoBrandPurple),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "W",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "William • WHLLdev",
                        color = BentoDeepPurpleText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "location",
                            tint = BentoBrandPurple,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Asia-Pacific Region / UTC+7",
                            color = BentoDeepPurpleText.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Lead Full-Stack Software Engineer specializing in premium client environments, Jetpack Compose architectures, and distributed systems integrations.",
                color = BentoDeepPurpleText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Dynamic Info Badges
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BadgeChip("5+ Years Exp")
                BadgeChip("12+ Systems Shipped")
                BadgeChip("Gemini Certified")
            }
        }
    }
}

@Composable
fun BadgeChip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(BentoDeepPurpleText)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SkillsLayout(uiState: PortfolioUiState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(uiState.skillCategories) { category ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BentoCardBg1),
                border = BorderStroke(1.dp, BentoBorderColor),
                shape = RoundedCornerShape(28.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val categoryIcon = when (category.icon) {
                            "smartphone" -> Icons.Default.Person
                            "cloud" -> Icons.Default.Build
                            else -> Icons.Default.Star
                        }
                        Icon(
                            imageVector = categoryIcon,
                            contentDescription = category.title,
                            tint = BentoBrandPurple,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = category.title,
                            color = BentoDarkText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    category.skills.forEach { skill ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = skill.name,
                                    color = BentoDarkText,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${(skill.rating * 100).toInt()}% Proficiency",
                                    color = BentoSecondaryText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            // Prof Bar
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(CircleShape)
                                    .background(BentoWhite)
                                    .border(BorderStroke(1.dp, BentoBorderColor.copy(alpha = 0.5f)), CircleShape)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(skill.rating)
                                        .fillMaxHeight()
                                        .clip(CircleShape)
                                        .background(BentoBrandPurple)
                                )
                            }
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectsLayout(uiState: PortfolioUiState) {
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(uiState.projects) { project ->
            val isExpanded = expandedStates[project.title] ?: false
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedStates[project.title] = !isExpanded },
                colors = CardDefaults.cardColors(containerColor = BentoCardBg2),
                border = BorderStroke(1.dp, BentoBorderColor),
                shape = RoundedCornerShape(28.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .animateContentSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = project.category.uppercase(),
                                color = BentoBrandPurple,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = project.title,
                                color = BentoDeepPurpleText,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand info",
                            tint = BentoDeepPurpleText,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = project.description,
                        color = BentoDarkText,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Tech Tags (Styled like the white bento grid technology items in HTML)
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        project.techStack.forEach { tech ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(BentoWhite)
                                    .border(BorderStroke(1.dp, BentoPillBorderColor), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = tech,
                                    color = BentoDeepPurpleText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }

                    if (isExpanded) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Key Achievements",
                            color = BentoDeepPurpleText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        project.highlights.forEach { bullet ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "bullet",
                                    tint = BentoBrandPurple,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = bullet,
                                    color = BentoDarkText,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
fun TimelineLayout(uiState: PortfolioUiState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(uiState.experienceHistory) { work ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BentoCardBg1),
                border = BorderStroke(1.dp, BentoBorderColor),
                shape = RoundedCornerShape(28.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Vertical Timeline graphic with stable fixed dimensions
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(BentoBrandPurple)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(1.5.dp)
                                .height(140.dp)
                                .background(BentoBorderColor)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = work.period,
                            color = BentoBrandPurple,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = work.role,
                            color = BentoDarkText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = work.company,
                            color = BentoDeepPurpleText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = work.description,
                            color = BentoSecondaryText,
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        work.bulletPoints.forEach { point ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "•",
                                    color = BentoBrandPurple,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                Text(
                                    text = point,
                                    color = BentoDarkText,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
fun ChatBotLayout(
    uiState: PortfolioUiState,
    viewModel: PortfolioViewModel
) {
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // Slide bottom scroll to last message on receipt
    LaunchedEffect(uiState.chatMessages.size, uiState.isAiTyping) {
        if (uiState.chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.chatMessages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Chat List Panel
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.chatMessages) { message ->
                ChatBubble(message)
            }

            if (uiState.isAiTyping) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = BentoBrandPurple,
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "HireBot is compiling response...",
                            color = BentoSecondaryText,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Popular Preset Prompts
        val presets = listOf(
            "What projects has William built?",
            "Explain William's Android core skills",
            "Is Wilka accepting immediate roles?",
            "Tell me about AetherChat"
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 6.dp)
        ) {
            items(presets) { prompt ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(BentoCardBg2)
                        .border(BorderStroke(1.dp, BentoBorderColor), RoundedCornerShape(12.dp))
                        .clickable {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            viewModel.handlePresetPrompt(prompt)
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = prompt,
                        color = BentoDeepPurpleText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Custom Query Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = uiState.currentInputField,
                onValueChange = { viewModel.onInputFieldChanged(it) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input"),
                placeholder = {
                    Text(
                        text = "Query about Wilka's credentials...",
                        color = BentoSecondaryText,
                        fontSize = 13.sp
                    )
                },
                maxLines = 2,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        viewModel.sendMessage()
                    }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BentoDarkText,
                    unfocusedTextColor = BentoDarkText,
                    cursorColor = BentoBrandPurple,
                    focusedContainerColor = BentoCardBg1,
                    unfocusedContainerColor = BentoCardBg1,
                    focusedBorderColor = BentoBrandPurple,
                    unfocusedBorderColor = BentoBorderColor
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    viewModel.sendMessage()
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(BentoBrandPurple)
                    .testTag("send_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send message",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    val isUser = message.sender == Sender.USER
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val bgGradients = if (isUser) {
        Brush.linearGradient(colors = listOf(BentoBrandPurple, BentoBrandPurple))
    } else {
        Brush.linearGradient(colors = listOf(BentoCardBg1, BentoCardBg1))
    }
    val textColor = if (isUser) Color.White else BentoDarkText
    val shape = if (isUser) {
        RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .clip(shape)
                .background(bgGradients)
                .border(BorderStroke(1.dp, if (isUser) Color.Transparent else BentoBorderColor), shape)
                .padding(horizontal = 14.dp, vertical = 10.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                color = textColor,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = if (isUser) "You" else "HireBot AI",
            color = BentoSecondaryText,
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}
