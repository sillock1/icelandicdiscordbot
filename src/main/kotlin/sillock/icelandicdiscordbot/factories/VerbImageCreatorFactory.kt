package sillock.icelandicdiscordbot.factories

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.creators.imagecreators.IVerbImageCreator
import sillock.icelandicdiscordbot.models.enums.VerbImageCreatorType

@Component
class VerbImageCreatorFactory(private val verbImageCreatorList: List<IVerbImageCreator>) {
    fun create(verbImageCreatorType: VerbImageCreatorType): IVerbImageCreator {
        return verbImageCreatorList.first{x -> x.verbImageCreatorType == verbImageCreatorType}
    }
}