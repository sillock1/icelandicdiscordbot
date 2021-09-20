package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component

@Component
class NounGenderMapper {
    fun map(genderCode: String) : String{
        when(genderCode){
            "kvk" -> return "kvenkynsnafnorð (Feminine noun)"
            "hk" -> return "hvorugkynsnafnorð (Neuter noun)"
            "kk" -> return "karlkynsnafnorð (Masculine noun)"
        }
        return "";
    }
}