package sillock.icelandicdiscordbot.factories

import org.springframework.stereotype.Component
import sillock.icelandicdiscordbot.mappers.IInflectionalMapper
import sillock.icelandicdiscordbot.models.enums.InflectionType

@Component
class InflectionalMapperFactory(private val inflectionalMappers: List<IInflectionalMapper>) {
    fun create(inflectionType: InflectionType): IInflectionalMapper {
        return inflectionalMappers.first { x -> x.inflectionType == inflectionType }
    }
}