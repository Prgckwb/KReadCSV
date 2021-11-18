import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Station {
    companion object {
        const val LINE_CD: Int = 5
        const val STATION_NAME: Int = 2
    }

    /*
        (1)IntelliJから実行する時は1を実行
        (2)appファイルから実行する場合は2を実行
     */
        val STATION_PATH: Path = Paths.get("./src/main/resources/station.csv")     // 1
        val stationsList = Files.readAllLines(STATION_PATH)                     // 1
//    private val resourceDir = File(System.getProperty("compose.application.resources.dir")) // 2
//    private val filePath = resourceDir.resolve("station.csv").toPath()              //  2
//    private val stationsList: MutableList<String> = Files.readAllLines(filePath)            //  2

//
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