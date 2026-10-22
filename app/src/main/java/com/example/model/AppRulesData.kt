package com.example.model

data class CommunityRule(
    val number: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val iconEmoji: String,
    val highlightColorHex: Long
)

object AppRulesData {
    val RULES = listOf(
        CommunityRule(
            number = 1,
            title = "Salón Público Libre de Contenido Sexual",
            subtitle = "Respeto, arte y cortesía en la plaza comunitaria",
            description = "Está terminantemente prohibido publicar contenido sexual o erótico explícito dentro del Chat Público (Salón de la Luna). Este espacio es sagrado para exhibir obras, compartir versos poéticos, música y debates de admiración artística.",
            iconEmoji = "🏛️",
            highlightColorHex = 0xFFFFD56B
        ),
        CommunityRule(
            number = 2,
            title = "Intimidad Exclusiva en Chats Privados",
            subtitle = "Espacio confidencial para la pasión y el romance",
            description = "El contenido íntimo, sensible o sexual solo está permitido dentro de los chats privados 1 a 1 entre adultos y bajo consentimiento mutuo expreso. Respeta siempre los límites de tu pareja artística.",
            iconEmoji = "🔐",
            highlightColorHex = 0xFFFF3366
        ),
        CommunityRule(
            number = 3,
            title = "Fotos 100% Reales — Tolerancia Cero a IA",
            subtitle = "Autenticidad humana en cada perfil",
            description = "Debes utilizar fotografías reales tuyas. Cualquier perfil que emplee fotos generadas por Inteligencia Artificial, deepfakes o retratos no auténticos será eliminado de forma inmediata e irrevocable.",
            iconEmoji = "✨",
            highlightColorHex = 0xFF2ED573
        ),
        CommunityRule(
            number = 4,
            title = "Autoría y Amor por el Arte",
            subtitle = "Pintura, Poesía, Música, Escultura y más",
            description = "Valora el trabajo ajeno. Toda obra exhibida debe pertenecer a su creador. Comparte tus redes sociales oficiales (Instagram, Spotify, Behance) para que tu romance florezca también a través de tu talento.",
            iconEmoji = "🌙",
            highlightColorHex = 0xFFA78BFA
        )
    )

    // Simple keyword checker to protect the public lounge
    private val FORBIDDEN_PUBLIC_TERMS = listOf(
        "sexo", "nude", "desnudo", "pack", "porno", "xxx", "erotico explicito",
        "cam privada sexual", "servicios sexuales", "contenido sexual", "coito"
    )

    fun validatePublicChatContent(text: String): Pair<Boolean, String?> {
        val lower = text.lowercase()
        for (term in FORBIDDEN_PUBLIC_TERMS) {
            if (lower.contains(term)) {
                return Pair(
                    false,
                    "⚠️ Infracción de Regla #1: No se permite contenido sexual en el Chat Público. Por favor, reserva la intimidad para chats privados y comparte aquí tu arte o poesía."
                )
            }
        }
        return Pair(true, null)
    }
}
