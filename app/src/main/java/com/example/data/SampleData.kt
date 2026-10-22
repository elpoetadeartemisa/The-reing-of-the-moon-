package com.example.data

import com.example.model.*

object SampleData {

    val CURRENT_USER = ArtistProfile(
        id = "user_me",
        firstName = "Julián",
        lastName = "Navarro",
        country = "España",
        countryFlag = "🇪🇸",
        age = 27,
        bio = "Pintor expresionista y amante de las noches despejadas. Pinto cielos violetas al óleo buscando una mirada que comprenda el silencio de las estrellas.",
        mainDiscipline = ArtDiscipline.PINTURA,
        secondaryDiscipline = ArtDiscipline.ESCRITOS,
        isRealPhotoVerified = true,
        noAiCertified = true,
        city = "Madrid",
        latitude = 40.4168,
        longitude = -3.7038,
        distanceKm = 0.0,
        romanticQuote = "En cada pincelada late el deseo de encontrarte bajo la luna.",
        socialLinks = SocialLinks(
            instagram = "julian_canvas_art",
            spotify = "artist/julian_nocturno",
            behance = "juliannavarro",
            twitter = "julian_moonart"
        ),
        portfolio = listOf(
            Artwork(
                id = "art_me_1",
                title = "Nocturno en la Plaza Mayor",
                discipline = ArtDiscipline.PINTURA,
                description = "Óleo sobre lienzo de gran formato. Reflejos de farolas doradas y parejas abrazadas bajo una llovizna suave.",
                year = "2024",
                technique = "Óleo con espátula",
                likesCount = 42
            ),
            Artwork(
                id = "art_me_2",
                title = "Epitafio para un Romance Breve",
                discipline = ArtDiscipline.ESCRITOS,
                description = "Poema en endecasílabos dedicado a los amores fugaces que dejan estelas eternas.",
                poetryVerses = "Fuimos la chispa viva en el madero,\nla lumbre que a la noche desafía,\nun breve resplandor, un te venero\nque al rayar de la aurora fenecía.",
                year = "2024",
                technique = "Poesía lírica",
                likesCount = 38
            )
        )
    )

    val ARTISTS = listOf(
        ArtistProfile(
            id = "artist_valeria",
            firstName = "Valeria",
            lastName = "Montes",
            country = "México",
            countryFlag = "🇲🇽",
            age = 26,
            bio = "Poetisa y fotógrafa analógica de 35mm. Capturo la melancolía de los trenes y escribo versos a la luz de las velas. Busco una conexión pura que vibre con los libros de Neruda y la lluvia de café.",
            mainDiscipline = ArtDiscipline.ESCRITOS,
            secondaryDiscipline = ArtDiscipline.FOTOGRAFIA,
            isRealPhotoVerified = true,
            noAiCertified = true,
            city = "Coyoacán, CDMX",
            latitude = 40.4200,
            longitude = -3.7050,
            distanceKm = 1.8,
            romanticQuote = "Si me invitas un vino tinto, te recito los versos que nunca publiqué.",
            socialLinks = SocialLinks(
                instagram = "valeria.poesia.lunar",
                spotify = "valeria_poemas_podcast",
                twitter = "valeriamontes_p"
            ),
            portfolio = listOf(
                Artwork(
                    id = "art_val_1",
                    title = "El Reino de tu Piel",
                    discipline = ArtDiscipline.ESCRITOS,
                    description = "Poema escrito durante una noche de luna llena en el balcón del taller.",
                    poetryVerses = "No busco eternidades en campanas,\nni juramentos que el olvido arranca;\nquiero posar mis besos en tus sábanas\ny despertar en tu cintura blanca.\n\nBajo el reinado fiel de luna abierta,\nsabrás que amar es una herida viva,\nuna ventana cómplice y desierta\nque a tu latido trémulo se aviva.",
                    year = "2024",
                    technique = "Soneto contemporáneo",
                    likesCount = 129
                ),
                Artwork(
                    id = "art_val_2",
                    title = "Sombras en el Tranvía Antiguo",
                    discipline = ArtDiscipline.FOTOGRAFIA,
                    description = "Fotografía revelada a mano en cuarto oscuro con sales de plata. Luces de farolas recortando una silueta misteriosa.",
                    year = "2023",
                    technique = "Película Ilford HP5 Plus en blanco y negro",
                    likesCount = 94
                )
            )
        ),
        ArtistProfile(
            id = "artist_mateo",
            firstName = "Mateo",
            lastName = "Rossi",
            country = "Argentina",
            countryFlag = "🇦🇷",
            age = 29,
            bio = "Violonchelista y compositor clásico contemporáneo. Toco tangos melancólicos y suites de Bach al anochecer. Me enamoro de quienes saben escuchar el silencio entre dos notas.",
            mainDiscipline = ArtDiscipline.MUSICA,
            secondaryDiscipline = ArtDiscipline.ESCRITOS,
            isRealPhotoVerified = true,
            noAiCertified = true,
            city = "San Telmo, BsAs",
            latitude = 40.4120,
            longitude = -3.6980,
            distanceKm = 2.4,
            romanticQuote = "Una nota de cello basta para desnudar el alma sin tocar la ropa.",
            socialLinks = SocialLinks(
                instagram = "mateorossi_cello",
                spotify = "artist/mateorossi_nocturnos",
                soundcloud = "mateorossi_acoustic"
            ),
            portfolio = listOf(
                Artwork(
                    id = "art_mat_1",
                    title = "Nocturno Lunar Op. 7 para Violonchelo",
                    discipline = ArtDiscipline.MUSICA,
                    description = "Composición original en Re menor inspirada en el reflejo de la luna sobre el Río de la Plata.",
                    audioTitle = "Nocturno Lunar en Re menor",
                    audioDuration = "4:32 min",
                    year = "2024",
                    technique = "Grabación en vivo en sala acústica con cuerdas de tripa",
                    likesCount = 215
                ),
                Artwork(
                    id = "art_mat_2",
                    title = "Balada para un Abrazo en la Madrugada",
                    discipline = ArtDiscipline.MUSICA,
                    description = "Dueto íntimo de violonchelo y piano de cola grabado en una toma continua.",
                    audioTitle = "Madrugada & Ceniza",
                    audioDuration = "3:48 min",
                    year = "2024",
                    technique = "Acústico en cinta analógica",
                    likesCount = 143
                )
            )
        ),
        ArtistProfile(
            id = "artist_camila",
            firstName = "Camila",
            lastName = "Soler",
            country = "Colombia",
            countryFlag = "🇨🇴",
            age = 25,
            bio = "Escultora en terracota y bronce fundido. Mis manos moldean cuerpos que se buscan y bocas a punto de encontrarse. Apasionada de las tertulias hasta las 4 AM con café de origen.",
            mainDiscipline = ArtDiscipline.ESCULTURA,
            secondaryDiscipline = ArtDiscipline.PINTURA,
            isRealPhotoVerified = true,
            noAiCertified = true,
            city = "Medellín",
            latitude = 40.4280,
            longitude = -3.7120,
            distanceKm = 4.1,
            romanticQuote = "El barro recuerda el calor de los dedos mucho antes de entrar al fuego.",
            socialLinks = SocialLinks(
                instagram = "camila_esculturas",
                behance = "camilasoler_art",
                tiktok = "@camilasoler_studio"
            ),
            portfolio = listOf(
                Artwork(
                    id = "art_cam_1",
                    title = "El Beso Inconcluso",
                    discipline = ArtDiscipline.ESCULTURA,
                    description = "Escultura en bronce con pátina verde lunar. Dos rostros que gravitan a milímetros de besarse sin llegar a tocarse.",
                    year = "2024",
                    technique = "Cera perdida y bronce a la tierra",
                    likesCount = 188
                ),
                Artwork(
                    id = "art_cam_2",
                    title = "Torso de Artemisa Bajo la Llovizna",
                    discipline = ArtDiscipline.ESCULTURA,
                    description = "Terracota negra pulida a mano con cera virgen de abejas.",
                    year = "2023",
                    technique = "Modelado directo en gres refractario",
                    likesCount = 112
                )
            )
        ),
        ArtistProfile(
            id = "artist_elena",
            firstName = "Elena",
            lastName = "Vidal",
            country = "Chile",
            countryFlag = "🇨🇱",
            age = 28,
            bio = "Muralista y grabadora en linóleo. Adoro los amaneceres después de una velada de pintura y vino. Si te gusta la música indie y visitar museos de noche, hablemos.",
            mainDiscipline = ArtDiscipline.PINTURA,
            secondaryDiscipline = ArtDiscipline.ARTESANIAS,
            isRealPhotoVerified = true,
            noAiCertified = true,
            city = "Valparaíso",
            latitude = 40.4090,
            longitude = -3.6890,
            distanceKm = 5.2,
            romanticQuote = "Te pintaría un cielo donde no haga falta fingir nada.",
            socialLinks = SocialLinks(
                instagram = "elena_vidal_murales",
                behance = "elenavidal",
                twitter = "elenavidalg"
            ),
            portfolio = listOf(
                Artwork(
                    id = "art_ele_1",
                    title = "Constelación de Dos Amantes",
                    discipline = ArtDiscipline.PINTURA,
                    description = "Grabado en madera y pan de oro. Dos cuerpos entrelazados flotando en el mar cósmico.",
                    year = "2024",
                    technique = "Xilografía sobre papel japonés Kozo",
                    likesCount = 164
                )
            )
        ),
        ArtistProfile(
            id = "artist_antoine",
            firstName = "Antoine",
            lastName = "Mercier",
            country = "Francia",
            countryFlag = "🇫🇷",
            age = 31,
            bio = "Pianista bohemio y luthier de instrumentos antiguos. Vivo entre el aroma a madera barnizada y las sonatas nocturnas. Busco un alma poética para compartir paseos bajo la niebla.",
            mainDiscipline = ArtDiscipline.MUSICA,
            secondaryDiscipline = ArtDiscipline.ESCULTURA,
            isRealPhotoVerified = true,
            noAiCertified = true,
            city = "Montmartre / Madrid",
            latitude = 40.4320,
            longitude = -3.7010,
            distanceKm = 6.0,
            romanticQuote = "La música es la forma más honesta de rozar a alguien sin pedir permiso.",
            socialLinks = SocialLinks(
                instagram = "antoine_luthier_piano",
                spotify = "artist/antoine_mercier",
                soundcloud = "antoine_mercier_paris"
            ),
            portfolio = listOf(
                Artwork(
                    id = "art_ant_1",
                    title = "Impromptu a la Luz de una Vela",
                    discipline = ArtDiscipline.MUSICA,
                    description = "Pieza solista de piano grabada en un Pleyel de 1912 con sordina de fieltro.",
                    audioTitle = "Clair de Lune Bohème",
                    audioDuration = "5:10 min",
                    year = "2024",
                    technique = "Piano acústico vintage",
                    likesCount = 310
                )
            )
        )
    )

    val INITIAL_PUBLIC_MESSAGES = listOf(
        PublicChatMessage(
            id = "msg_1",
            senderId = "artist_valeria",
            senderName = "Valeria Montes",
            senderCountryFlag = "🇲🇽",
            senderDiscipline = ArtDiscipline.ESCRITOS,
            text = "«Que la luna no sepa que tus ojos tienen más gravedad que todos sus cráteres juntos...»\nCompartiendo este fragmento recién nacido en mi libreta para las almas que aún desvelan.",
            timestamp = "Hace 10 min",
            topicTag = "Poema del Alma",
            heartsCount = 28,
            isLikedByMe = true,
            attachedArtwork = Artwork(
                id = "art_post_1",
                title = "Manuscrito a Medianoche",
                discipline = ArtDiscipline.ESCRITOS,
                description = "Versos escritos con tinta sepia sobre pergamino artesanal.",
                poetryVerses = "Si me buscas, estaré donde la sombra\napenas roza el borde de tu aliento...",
                year = "2024"
            )
        ),
        PublicChatMessage(
            id = "msg_2",
            senderId = "artist_mateo",
            senderName = "Mateo Rossi",
            senderCountryFlag = "🇦🇷",
            senderDiscipline = ArtDiscipline.MUSICA,
            text = "Acabo de terminar los arreglos de cello para el 'Nocturno en Re'. Si alguien quiere escuchar el ensayo antes de la presentación del viernes en el café literario, avíseme por privado 🍷🎻",
            timestamp = "Hace 25 min",
            topicTag = "Música Nocturna",
            heartsCount = 41,
            isLikedByMe = false
        ),
        PublicChatMessage(
            id = "msg_3",
            senderId = "artist_camila",
            senderName = "Camila Soler",
            senderCountryFlag = "🇨🇴",
            senderDiscipline = ArtDiscipline.ESCULTURA,
            text = "¡El bronce de 'El Beso Inconcluso' ya salió del horno de fundición! Ver el metal al rojo vivo enfriándose bajo la noche es una de las sensaciones más románticas del mundo.",
            timestamp = "Hace 1 hora",
            topicTag = "Exhibición",
            heartsCount = 53,
            isLikedByMe = true
        ),
        PublicChatMessage(
            id = "msg_4",
            senderId = "artist_elena",
            senderName = "Elena Vidal",
            senderCountryFlag = "🇨🇱",
            senderDiscipline = ArtDiscipline.PINTURA,
            text = "Pregunta para los poetas y músicos del Salón: ¿Qué melodía o qué verso os hace pintar con mayor intensidad cuando la luna está en cuarto creciente?",
            timestamp = "Hace 2 horas",
            topicTag = "Reflexión Lunar",
            heartsCount = 19,
            isLikedByMe = false
        )
    )

    val INITIAL_PRIVATE_CONVERSATIONS = listOf(
        PrivateChatConversation(
            id = "conv_valeria",
            artist = ARTISTS[0],
            lastMessage = "Me encantó la textura de tu óleo. ¿Estarías libre para tomar un café y ver tu cuaderno?",
            lastMessageTime = "18:42",
            unreadCount = 1,
            messages = listOf(
                PrivateMessage(
                    id = "pm_1",
                    senderId = "artist_valeria",
                    text = "¡Hola Julián! Vi tu cuadro 'Nocturno en la Plaza Mayor' y me recordó muchísimo a un poema de Lorca que leía anoche.",
                    timestamp = "18:30",
                    isFromMe = false
                ),
                PrivateMessage(
                    id = "pm_2",
                    senderId = "user_me",
                    text = "Hola Valeria, qué hermosa coincidencia. Justamente mientras lo pintaba tenía abierto el Romancero Gitano.",
                    timestamp = "18:36",
                    isFromMe = true
                ),
                PrivateMessage(
                    id = "pm_3",
                    senderId = "artist_valeria",
                    text = "Me encantó la textura de tu óleo. ¿Estarías libre para tomar un café y ver tu cuaderno?",
                    timestamp = "18:42",
                    isFromMe = false
                )
            )
        ),
        PrivateChatConversation(
            id = "conv_camila",
            artist = ARTISTS[2],
            lastMessage = "El café de Malasaña con terraza nocturna es perfecto para conversar de escultura.",
            lastMessageTime = "Ayer",
            unreadCount = 0,
            messages = listOf(
                PrivateMessage(
                    id = "pm_4",
                    senderId = "user_me",
                    text = "Tu escultura 'El Beso Inconcluso' tiene una tensión magnética increíble, Camila.",
                    timestamp = "Ayer 20:15",
                    isFromMe = true
                ),
                PrivateMessage(
                    id = "pm_5",
                    senderId = "artist_camila",
                    text = "¡Muchas gracias! Quería plasmar ese segundo exacto antes de que el amor se convierta en fuego.",
                    timestamp = "Ayer 20:28",
                    isFromMe = false
                ),
                PrivateMessage(
                    id = "pm_6",
                    senderId = "artist_camila",
                    text = "El café de Malasaña con terraza nocturna es perfecto para conversar de escultura.",
                    timestamp = "Ayer 20:30",
                    isFromMe = false
                )
            )
        )
    )

    val ROMANTIC_SPOTS = listOf(
        RomanticSpot(
            id = "spot_1",
            name = "Café & Galería La Luna Bohemia",
            category = "Café de Arte & Vino",
            address = "Calle del Espíritu Santo 14, Malasaña",
            city = "Madrid",
            latitude = 40.4260,
            longitude = -3.7045,
            distanceKm = 1.2,
            romanticDescription = "Iluminación con velas de cera, cuadros expuestos en paredes de ladrillo visto, música de vinilo en vivo y rincón con libros de poesía libre.",
            vibe = "Íntimo & Literario"
        ),
        RomanticSpot(
            id = "spot_2",
            name = "Mirador del Templo bajo las Estrellas",
            category = "Mirador Nocturno al Aire Libre",
            address = "Calle Ferraz 1, Parque del Oeste",
            city = "Madrid",
            latitude = 40.4240,
            longitude = -3.7178,
            distanceKm = 2.1,
            romanticDescription = "Vistas panorámicas hacia el crepúsculo. Lugar predilecto de artistas plásticos y fotógrafos para contemplar la luna sobre los jardines.",
            vibe = "Místico & Panorámico"
        ),
        RomanticSpot(
            id = "spot_3",
            name = "Taller Abierto & Vinoteca El Cincel",
            category = "Atelier de Escultura & Vinos",
            address = "Calle Huertas 32, Barrio de las Letras",
            city = "Madrid",
            latitude = 40.4132,
            longitude = -3.6990,
            distanceKm = 0.8,
            romanticDescription = "Cata de vinos artesanales rodeada de bustos de mármol y arcilla. Las parejas pueden modelar una pequeña pieza de cerámica juntos.",
            vibe = "Sensorial & Creador"
        ),
        RomanticSpot(
            id = "spot_4",
            name = "Jardín Romántico del Museo de Bellas Artes",
            category = "Jardín Secreto",
            address = "Paseo del Prado 8",
            city = "Madrid",
            latitude = 40.4150,
            longitude = -3.6920,
            distanceKm = 1.6,
            romanticDescription = "Fuentes de cantería, jazmines que florecen al caer el sol y bancos de piedra donde leer versos en complicidad.",
            vibe = "Romántico Clásico"
        )
    )
}
