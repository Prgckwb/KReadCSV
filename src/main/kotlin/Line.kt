import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class Line {
    companion object {
        const val LINE_CD = 0
        const val LINE_NAME = 2
    }

    /*
        (1)IntelliJから実行する時は1を実行
        (2)appファイルから実行する場合は2を実行
     */
        val LINE_PATH = Paths.get("./src/main/resources/line.csv")                // 1
        val linesList: MutableList<String> = Files.readAllLines(LINE_PATH)        // 1
//    val resourcesDir = File(System.getProperty("compose.application.resources.dir")) // 2
//    val filePath = resourcesDir.resolve("line.csv").toPath()                   // 2
//    val linesList: MutableList<String> = Files.readAllLines(filePath)                  // 2

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