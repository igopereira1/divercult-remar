package divercult

import org.springframework.security.access.annotation.Secured
import groovy.json.JsonBuilder
import grails.converters.JSON
import br.ufscar.sead.loa.remar.api.MongoHelper
import grails.util.Environment

@Secured(['isAuthenticated()'])

class Fase3Controller {

def beforeInterceptor = [action: this.&check, only: ['index','indexImage']]

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

    render view: "index", model: [listaPares: Fase3.findAllByOwnerId(session.user.id)
]    

}
    def show() {
        render template: "tile",
                model: [tileInstance: Tile.findById(params.id)]
    }

    def create() {
        
    }

def indexImage() {
     if (params.t) {
        session.taskId = params.t
    }
}

def save() {
        def novopar = new Fase3()
        novopar.content = params.content 
        novopar.ownerId = session.user.id
        novopar.taskId = session.taskId
        novopar.save()

        redirect controller: "Fase3", action: "index"
    }

def saveImage() {

        def userPath = servletContext.getRealPath("/data/" + session.user.id.toString() + "/" + session.taskId.toString())
        def userFolder = new File(userPath)
        userFolder.mkdirs()
        
        def f1Uploaded = request.getFile("background")

        def f1 = new File("$userPath/bg.png")
        f1Uploaded.transferTo(f1)
        
        String id = MongoHelper.putFile(f1.absolutePath)

        def port = request.serverPort
        if (Environment.current == Environment.DEVELOPMENT) {
        port = 8080
        }

       render "http://${request.serverName}:${port}/process/task/complete/${session.taskId}?files=${id}"

    }

    def toJson() {
        def list = Fase3.getAll(params.id ? params.id.split(',').toList() : null)

        def builder = new JsonBuilder()
        def textos = list.collect { p ->
                    p.getContent()
 
                }
        def json = builder(["textos": textos]
        )
       

        def dataPath = servletContext.getRealPath("/data")
        def userPath = new File(dataPath, "/" + springSecurityService.getCurrentUser().getId() + "/" + session.taskId)
        userPath.mkdirs()

        def fileName = "fase3.json"
        File file = new File("$userPath/$fileName");
        PrintWriter pw = new PrintWriter(file);
        pw.write(builder.toString());
        pw.close();

        String id = MongoHelper.putFile(file.absolutePath)

        def port = request.serverPort
        if (Environment.current == Environment.DEVELOPMENT) {
        port = 8080
        }

        redirect uri: "http://${request.serverName}:${port}/process/task/complete/${session.taskId}", params: [files: id]
    }
        
    
}
