package sillock.icelandicdiscordbot.factories

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.creators.imagecreators.IImageCreator
import sillock.icelandicdiscordbot.models.enums.InflectionType

@Component
class ImageCreatorFactory(private val imageCreators: List<IImageCreator>) {
    fun create(inflectionType: InflectionType) : IImageCreator{
        return imageCreators.first { x -> x.inflectionType == inflectionType }
    }
}