import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Station {
    companion object {
        const val LINE_CD: Int = 5
        const val STATION_NAME: Int = 2
    }

    private var stationsList: MutableList<String> = if (ReadModel.isApp) {
        val resourceDir = File(System.getProperty("compose.application.resources.dir")) // 2
        val filePath = resourceDir.resolve("station.csv").toPath()              //  2
        Files.readAllLines(filePath)            //  2
    } else {
        val filePath: Path = Paths.get("./src/main/resources/station.csv")     // 1
        Files.readAllLines(filePath)                     // 1
    }

    //
    fun getStationListFromLineCd(lineCd: String): MutableList<String> {
        val stations = mutableListOf<String>()
        for (str in stationsList) {
            val splitedStringList = str.split(",")
            if (splitedStringList[LINE_CD] == lineCd) {
                stations.add(splitedStringList[STATION_NAME])
            }
        }
//        return stations.joinToString("\n")
        return stations
    }
}