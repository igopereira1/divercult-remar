package divercult

import org.springframework.security.access.annotation.Secured
import groovy.json.JsonBuilder
import grails.converters.JSON
import br.ufscar.sead.loa.remar.api.MongoHelper
import grails.util.Environment
import java.nio.file.Files
import grails.transaction.Transactional

@Secured(['isAuthenticated()'])

class Fase1Controller {

def beforeInterceptor = [action: this.&check, only: ['index']]

def springSecurityService

    private check() {
        if (springSecurityService.isLoggedIn())
            session.user = springSecurityService.currentUser
        else {
            log.debug "Logout: session.user is NULL !"
            session.user = null
            redirect controller: "login", action: "index"

            return false
        }
    }

    def index (){
        if (params.t) {
            session.taskId = params.t
        }

    render view: "index", model: [listaPares: Fase1.findAllByOwnerId(session.user.id)
]    

}
    def show() {
        render template: "tile",
                model: [tileInstance: Tile.findById(params.id)]
    }

    def create() {
        
    }
def save() {
        def novopar = new Fase1()
        novopar.content = params.content
        novopar.ownerId = session.user.id
        novopar.taskId = session.taskId
        novopar.save()

        def id = novopar.getId()
        def userPath = servletContext.getRealPath("/data/" + novopar.ownerId.toString() + "/" + novopar.taskId.toString() + "/pares")
        def userFolder = new File(userPath)
        userFolder.mkdirs()
        
        def f1Uploaded = request.getFile("img-par")

        def f1 = new File("$userPath/par${id}.png")
        f1Uploaded.transferTo(f1)
        
        redirect controller: "Fase1", action: "index"
    }

    def toJson() {
        def j = 0
        def list = Fase1.getAll(params.id ? params.id.split(',').toList() : null)
        def builder = new JsonBuilder()
        def textos = list.collect { p ->
                    p.getContent()
 
                }
        def json = builder(["textos": textos]
        )
    

        log.debug builder.toString()

        def dataPath = servletContext.getRealPath("/data")
        def userPath = new File(dataPath, "/" + springSecurityService.getCurrentUser().getId() + "/" + session.taskId)
        userPath.mkdirs()

        def fileName = "fase1.json"
        File file = new File("$userPath/$fileName");
        PrintWriter pw = new PrintWriter(file);
        pw.write(builder.toString());
        pw.close();

        String id = MongoHelper.putFile(file.absolutePath)
        String fileList = "files=${id}&"
        

        def i, fileId

        for (i = 0; i <list.size(); i++) {
        
            Fase1 par = list[i]
            def parPath = servletContext.getRealPath("/data/" + par.ownerId.toString() + "/" + par.taskId.toString() + "/pares")
            
            File original = new File(String.format("$parPath/par%d.png", par.id))
            File novo = new File(String.format("$userPath/%d.png", i))
            Files.copy(original.toPath(), novo.toPath());
            fileId = MongoHelper.putFile(novo.absolutePath)
            fileList += "files=${fileId}&"
        }

        def port = request.serverPort
        if (Environment.current == Environment.DEVELOPMENT) {
        port = 8080
        }

        redirect url: "http://${request.serverName}:${port}/process/task/complete/${session.taskId}?${fileList}"
    }

    @Transactional
    def delete(Fase1 questionInstance) {
        if (questionInstance == null) {
            notFound()
            return
        }

        questionInstance.delete flush: true

        if (request.isXhr()) {
            render(contentType: "application/json") {
                JSON.parse("{\"id\":" + questionInstance.getId() + "}")
            }
        } else {
            // TODO
        }
    }
        
}   