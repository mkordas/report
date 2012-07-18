package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyURLToFile

class Report {

    static final CSS_URL = Report.getResource('/spock/damagecontrol/statics/style/damage-control.css')

    def testResultsFolder
    def specDefinitionsFolder
    def outputFolder

    def indexTemplate = new HtmlIndexTemplate()

    def run() {
        List specs = new TestResultsCollector(resultsFolder: testResultsFolder).collect().specList

        HtmlFileWriter writer = new HtmlFileWriter(outputFolder: outputFolder)

        writer.write('index', indexTemplate.generate(specs))

        specs.each { spec ->
            spec.readDefinitionFrom specDefinitionsFolder
            HtmlSpecTemplate specTemplate = new HtmlSpecTemplate(spec: spec)
            writer.write(spec.name, specTemplate.generate())
        }

        copyURLToFile(CSS_URL, new File(outputFolder.absolutePath + '/style/damage-control.css'))
    }
}
