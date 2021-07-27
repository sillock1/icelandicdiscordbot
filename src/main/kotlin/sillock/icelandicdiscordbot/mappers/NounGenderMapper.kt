package sillock.icelandicdiscordbot.mappers

import org.springframework.stereotype.Component

@Component
class NounGenderMapper {
    fun map(genderCode: String) : String{
        when(genderCode){
            "kvk" -> return "kvenkynsnafnorð (Female noun)"
            "hk" -> return "hvorugkynsnafnorð (Neuter noun)"
            "kk" -> return "karlkynsnafnorð (Male noun)"
        }
        return "";
    }
}