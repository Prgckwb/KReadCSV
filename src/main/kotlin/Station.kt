import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Station {
    companion object {
        const val LINE_CD: Int = 5
        const val STATION_NAME: Int = 2

        val STATION_PATH: Path = Paths.get("./src/main/resources/station.csv")
    }

    val stationsList = Files.readAllLines(STATION_PATH)

    fun getStationListFromLineCd(lineCd: String): String{
        val stations = mutableListOf<String>()
        for (str in stationsList){
            val splitedStringList = str.split(",")
            if(splitedStringList[LINE_CD] == lineCd){
                stations.add(splitedStringList[STATION_NAME])
            }
        }
        return stations.joinToString("\n")
    }

    fun ex(): String{
        var str: String = ""
        File(STATION_PATH.toUri()).forEachLine { str += "${it}\n" }
        return str
    }
}