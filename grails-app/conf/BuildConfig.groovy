grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.repos.default = "crm"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        //mavenCentral()
        mavenRepo "http://labs.technipelago.se/repo/plugins-releases-local/"
        mavenRepo "http://labs.technipelago.se/repo/crm-releases-local/"
    }
    dependencies {
    }

    plugins {
        build(":tomcat:$grailsVersion",
                ":rest-client-builder:1.0.2",
                ":release:2.0.4") {
            export = false
        }
        runtime ":hibernate:$grailsVersion"

        test(":codenarc:latest.integration") { export = false }
        test(":spock:0.6") { export = false }

        compile ":rendering:0.4.3"

        compile ":sequence-generator:latest.integration"
        compile "grails.crm:crm-core:latest.integration"
        compile "grails.crm:crm-contact-lite:latest.integration"
        runtime "grails.crm:crm-ui-bootstrap:latest.integration"
        runtime "grails.crm:crm-tags:latest.integration"

        compile ":selection:latest.integration"
        runtime ":selection-repository:latest.integration"
    }
}

grails.plugin.location.'crm-product' = "../crm-product"

codenarc {
    reports = {
        CrmXmlReport('xml') {
            outputFile = 'CodeNarcReport.xml'
            title = 'Grails CRM CodeNarc Report'
        }
        CrmHtmlReport('html') {
            outputFile = 'target/test-reports/CodeNarcReport.html'
            title = 'Grails CRM CodeNarc Report'
        }
    }
    processTestUnit = false
    processTestIntegration = false
}
