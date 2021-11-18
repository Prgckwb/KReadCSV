import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Station {
    companion object {
        const val LINE_CD: Int = 5
        const val STATION_NAME: Int = 2
        const val STATION_CD:Int = 0

        val STATION_PATH: Path = Paths.get("./src/main/resources/station.csv")
    }

    val stationsList = Files.readAllLines(STATION_PATH)

    fun getStationListFromLineCd(lineCd: String): MutableList<String>{
        val stations = mutableListOf<String>()
        for (str in stationsList){
            val splitedStringList = str.split(",")
            if(splitedStringList[LINE_CD] == lineCd){
                stations.add(splitedStringList[STATION_NAME])
            }
        }
//        return stations.joinToString("\n")
        return stations
    }
}