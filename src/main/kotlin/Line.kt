import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class Line {
    companion object {
        const val LINE_CD = 0
        const val LINE_NAME = 2
    }

    private var linesList: MutableList<String> = if (ReadModel.isApp) {
        val resourcesDir = File(System.getProperty("compose.application.resources.dir"))
        val filePath = resourcesDir.resolve("line.csv").toPath()
        Files.readAllLines(filePath)
    } else {
        val LINE_PATH = Paths.get("./src/main/resources/line.csv")
        Files.readAllLines(LINE_PATH)
    }


    fun getLinesListsFromLineName(lineName: String): Pair<List<String>, List<String>> {
        val optionsLineName = mutableListOf<String>()
        val optionsLineCd = mutableListOf<String>()

//        val searchList = linesList.map { it.split(",")[LINE_NAME] }

        for (str in linesList) {
            if (str.contains(lineName)) {
                var flag = false
                val separateStr = str.split(",")
                for (words in optionsLineCd) {
                    if (separateStr[LINE_CD] == words) {
                        flag = true
                        break;
                    }
                }
                if (!flag && !separateStr[LINE_NAME].contains("新幹線")) {
                    optionsLineCd.add(separateStr[LINE_CD])
                    optionsLineName.add(separateStr[LINE_NAME])
                }
            }
        }

        return Pair(optionsLineCd, optionsLineName)
    }
}