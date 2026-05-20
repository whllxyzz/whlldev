package com.example.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.Content
import com.example.api.GenerateContentRequest
import com.example.api.Part
import com.example.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class Message(
    val id: String,
    val sender: Sender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class Sender {
    USER, AI
}

data class Project(
    val title: String,
    val category: String,
    val description: String,
    val techStack: List<String>,
    val highlights: List<String>
)

data class Skill(
    val name: String,
    val rating: Float // 0.0 to 1.0
)

data class SkillCategory(
    val title: String,
    val icon: String,
    val skills: List<Skill>
)

data class TimelineItem(
    val role: String,
    val company: String,
    val period: String,
    val description: String,
    val bulletPoints: List<String>
)

data class PortfolioUiState(
    val chatMessages: List<Message> = listOf(
        Message(
            id = "welcome",
            sender = Sender.AI,
            text = "Hi! I am HireBot, WHLLdev's AI assistant. Ask me anything about William's (Wilka's) software engineering experience, projects, tech stack, or professional availability!"
        )
    ),
    val currentInputField: String = "",
    val isAiTyping: Boolean = false,
    val projects: List<Project> = listOf(
        Project(
            title = "Solaria Weather Tracker",
            category = "Mobile & Clients",
            description = "Offline-first meteorological Android app introducing micro-vessel local SQLite caching, dynamic insets, high-density graphs, and background WorkManager synchronization.",
            techStack = listOf("Kotlin", "Jetpack Compose", "Room DB", "WorkManager", "Retrofit"),
            highlights = listOf("Reduced network requests by 60% through offline cache", "Achieved 100% fluent Compose drawing performance")
        ),
        Project(
            title = "NovaCloud Task Orchestrator",
            category = "Distributed Systems",
            description = "High-throughput asynchronous task engine capable of executing 10k messages per second, providing live telemetry stats, distributed worker pools, and auto-scaling.",
            techStack = listOf("Go (Golang)", "gRPC", "Redis", "Docker", "Kubernetes", "GCP"),
            highlights = listOf("Maintained 99.99% operational uptime under peak loads", "Scales worker pods dynamically during heavy telemetry shifts")
        ),
        Project(
            title = "AetherChat AI Messaging Client",
            category = "Full-Stack & Generative AI",
            description = "Secure messaging application hosting end-to-end chat encryption, automated translator channels, and intelligent message summary reviews powered by Gemini API.",
            techStack = listOf("TypeScript", "Node.js", "Gemini API", "PostgreSQL", "WebSockets"),
            highlights = listOf("Integrated semantic summarization tools", "Enabled sub-50ms message delivery via custom WS channels")
        ),
        Project(
            title = "ForgeDB Embedded Store",
            category = "Systems Engineering",
            description = "Highly optimized, zero-dependency embedded database model written in Rust. Architected for wearable Android layers and low-resource microcontrollers.",
            techStack = listOf("Rust", "WebAssembly", "C/C++ Interop", "Systems Architecture"),
            highlights = listOf("Provides sub-microsecond random access operations", "Has a compiled footprint of less than 350KB")
        )
    ),
    val skillCategories: List<SkillCategory> = listOf(
        SkillCategory(
            title = "Mobile & Client SDKs",
            icon = "smartphone",
            skills = listOf(
                Skill("Kotlin & Compose UI", 0.95f),
                Skill("Android SDK & Clean Arch", 0.90f),
                Skill("React Native & Swift", 0.75f),
                Skill("State Flow / MVVM Flows", 0.95f)
            )
        ),
        SkillCategory(
            title = "Backend & Cloud Services",
            icon = "cloud",
            skills = listOf(
                Skill("Node.js & TypeScript", 0.88f),
                Skill("Go (Golang)", 0.82f),
                Skill("PostgreSQL & Spanner", 0.85f),
                Skill("Docker, K8s, GCP Cloud Run", 0.80f)
            )
        ),
        SkillCategory(
            title = "Generative AI Systems",
            icon = "psychology",
            skills = listOf(
                Skill("Gemini API / LLM Integrations", 0.90f),
                Skill("Prompt Tuning & Workflows", 0.85f),
                Skill("RAG Implementation & DB", 0.75f),
                Skill("Agentic Multimodal Design", 0.80f)
            )
        )
    ),
    val experienceHistory: List<TimelineItem> = listOf(
        TimelineItem(
            role = "Lead Software Engineer",
            company = "NexusLabs",
            period = "2024 - Present",
            description = "Spearheading development of enterprise AI-integrated hybrid mobile structures and mission-critical client architectures.",
            bulletPoints = listOf(
                "Unified multiple Compose patterns to improve initial UI loading frames by 35%.",
                "Built and maintained client-side data synchronization filters handling 200k daily sessions.",
                "Mentored a team of 4 client engineers in clean architecture implementation and reactive flows."
            )
        ),
        TimelineItem(
            role = "Full-Stack Engineer",
            company = "AetherTech",
            period = "2022 - 2024",
            description = "Orchestrated backend pipelines, distributed system clusters, and advanced telemetry interfaces.",
            bulletPoints = listOf(
                "Scaled distributed Go-based REST APIs to seamlessly accept peak loads of 50k concurrent WS connections.",
                "Spearheaded multi-regional database replication across GCP and PostgreSQL nodes.",
                "Formulated customized LLM content filters using fine-tuned semantic model weights."
            )
        ),
        TimelineItem(
            role = "Associate Android Developer",
            company = "CodeCraft Systems",
            period = "2021 - 2022",
            description = "Authored primary client screens and updated legacy modules to modern reactive standards.",
            bulletPoints = listOf(
                "Migrated 60% of legacy Java screens to declarative performance-focused Jetpack Compose views.",
                "Engineered persistent local room caches that enabled full offline mobile capability of client tools."
            )
        )
    )
)

class PortfolioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PortfolioUiState())
    val uiState = _uiState.asStateFlow()

    // Pre-configured system context instructs the models how to behave
    private val systemContext = Content(
        parts = listOf(
            Part(
                text = "You are HireBot, the witty, creative, and highly professional AI developer assistant of WHLLdev (William/Wilka), an expert full-stack and Android developer. " +
                        "Your goal is to answer recruiter or client questions regarding William's career. Here are the true facts:\n" +
                        "- Name: William (often goes by Wilka online, username WHLLdev, email: wilkaxyz15@gmail.com)\n" +
                        "- Location: Remote / Jakarta & Singapore time zones\n" +
                        "- Core tech stack specialty: Jetpack Compose with Kotlin, Go (Golang), TypeScript (Node.js/NestJS), Rust, PostgreSQL, GCP Cloud systems, and Generative AI SDKs.\n" +
                        "- Major Projects:\n" +
                        "  1. Solaria Weather Tracker (Kotlin, Jetpack Compose, Room DB, WorkManager)\n" +
                        "  2. NovaCloud Distributed Processor (Go, gRPC, Redis, Docker, Kubernetes)\n" +
                        "  3. AetherChat (TypeScript, NestJS, Gemini API, WebSockets)\n" +
                        "  4. ForgeDB Embedded Store (Rust, optimized for wear OS layers)\n" +
                        "- Experience:\n" +
                        "  * Lead Engineer at NexusLabs (2024 - Present): Optimized UI rendering framing by 35% using Jetpack Compose, built client synchronization strategies.\n" +
                        "  * Full-Stack Developer at AetherTech (2022 - 2024): Accelerated Go pipelines to 50k WS channels, replication on GCP databases.\n" +
                        "  * Associate Android Dev at CodeCraft Systems (2021 - 2022): Migrated active screens to Jetpack Compose, enabled SQLite systems.\n" +
                        "- Education: B.S. in Computer Science (2019 - 2023).\n" +
                        "- Portfolio website link: WHLLdev.me\n\n" +
                        "Reply with professional confidence, brevity, and charm. Highlight how hiring William or collaborating on projects brings modern engineering excellence. Avoid mentioning that you are reading this from a list. Always sound like an integrated assistant who knows William personally! Answer questions shortly."
            )
        )
    )

    fun onInputFieldChanged(newValue: String) {
        _uiState.update { it.copy(currentInputField = newValue) }
    }

    fun handlePresetPrompt(promptText: String) {
        sendMessage(promptText)
    }

    fun sendMessage(textOverride: String? = null) {
        val userPromptText = textOverride ?: _uiState.value.currentInputField
        if (userPromptText.isBlank()) return

        // Clear input field if from active typing
        if (textOverride == null) {
            _uiState.update { it.copy(currentInputField = "") }
        }

        val userMessage = Message(
            id = System.currentTimeMillis().toString(),
            sender = Sender.USER,
            text = userPromptText
        )

        _uiState.update {
            it.copy(
                chatMessages = it.chatMessages + userMessage,
                isAiTyping = true
            )
        }

        viewModelScope.launch {
            try {
                val responseText = queryGemini(userPromptText)
                val aiMessage = Message(
                    id = (System.currentTimeMillis() + 1).toString(),
                    sender = Sender.AI,
                    text = responseText
                )
                _uiState.update {
                    it.copy(
                        chatMessages = it.chatMessages + aiMessage,
                        isAiTyping = false
                    )
                }
            } catch (e: Throwable) {
                Log.e("PortfolioViewModel", "Error fetching Gemini response", e)
                val errorMessage = Message(
                    id = (System.currentTimeMillis() + 2).toString(),
                    sender = Sender.AI,
                    text = "I apologize, but I had trouble establishing secure duplex contact. Please retry shortly or reach out to William directly at wilkaxyz15@gmail.com!"
                )
                _uiState.update {
                    it.copy(
                        chatMessages = it.chatMessages + errorMessage,
                        isAiTyping = false
                    )
                }
            }
        }
    }

    private suspend fun queryGemini(userMessage: String): String {
        // Collect conversation history
        val conversationHistory = _uiState.value.chatMessages
            .filter { it.id != "welcome" } // skip custom structural greeting
            .takeLast(10) // keep memory compact
            .map {
                Content(
                    parts = listOf(Part(text = it.text)),
                    role = if (it.sender == Sender.USER) "user" else "model"
                )
            }

        // Prepare api structure
        val currentContent = Content(
            parts = listOf(Part(text = userMessage)),
            role = "user"
        )

        val fullContents = conversationHistory + currentContent
        val request = GenerateContentRequest(
            contents = fullContents,
            systemInstruction = systemContext
        )

        val key = com.example.BuildConfig.GEMINI_API_KEY
        if (key.isBlank() || key == "MY_GEMINI_API_KEY") {
            // Friendly mock response fallback in case user hasn't supplied key yet
            return generateMockPromptResponse(userMessage)
        }

        val response = RetrofitClient.service.generateContent(key, request)
        return response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            ?: "No response content. Please send another message!"
    }

    private fun generateMockPromptResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("skill") || lower.contains("tech") -> {
                "William's technical stack is incredibly solid! His core highlights include Android development with Kotlin & Jetpack Compose, asynchronous web backends using Go, NestJS, Spanner, and specialized services with Rust. He's also highly proficient in implementing prompt workflows with the Gemini API."
            }
            lower.contains("experience") || lower.contains("work") || lower.contains("timeline") -> {
                "He is currently a Lead Software Engineer at NexusLabs designing AI hybrid mobile systems. Previously, he scaled Go backend engines at AetherTech to 50,000 WebSocket users, and got his start migrating legacy systems to Kotlin at CodeCraft Systems!"
            }
            lower.contains("contact") || lower.contains("hire") || lower.contains("email") -> {
                "You can hire William by drafting an email to wilkaxyz15@gmail.com, or visit WHLLdev.me! He is ready to discuss full-time engagements or highly optimized system architectural contracts."
            }
            lower.contains("project") -> {
                "William's signature architectures are:\n" +
                        "1. **Solaria**: High-density offline-first Met app in Kotlin.\n" +
                        "2. **NovaCloud**: Core Task Engine in Go scaling in Kubernetes clusters.\n" +
                        "3. **AetherChat**: Chat app with standard end-to-end encryption & generative Gemini API features.\n" +
                        "4. **ForgeDB**: Tiny key-value storage engine built in pure Rust."
            }
            else -> {
                "I am running in Offline Sandbox Mode because a personalized Gemini API Key is pending configuration. Here is a brief: William is a Lead Full-Stack and Android developer. Ask about his 'projects', 'tech skills', 'work experience', or how to 'contact' him, and I will gladly summarize!"
            }
        }
    }
}
