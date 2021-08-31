package sillock.icelandicdiscordbot.factories

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.creators.imagecreators.IImageCreator
import sillock.icelandicdiscordbot.models.enums.InflectionType
import sillock.icelandicdiscordbot.models.inflectedforms.InflectedForm

@Component
class ImageCreatorFactory(private val imageCreators: List<IImageCreator<InflectedForm>>) {
    fun create(inflectionType: InflectionType) : IImageCreator<InflectedForm>{
        return imageCreators.first { x -> x.inflectionType == inflectionType }
    }
}