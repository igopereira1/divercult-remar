package divercult

import org.springframework.security.access.annotation.Secured
import groovy.json.JsonBuilder
import grails.converters.JSON
import br.ufscar.sead.loa.remar.api.MongoHelper
import grails.util.Environment
import java.nio.file.Files
import grails.transaction.Transactional

@Secured(['isAuthenticated()'])

class Fase2Controller {

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

    render view: "index", model: [listaPares: Fase2.findAllByOwnerId(session.user.id)
]    

}
    def show() {
        render template: "tile",
                model: [tileInstance: Tile.findById(params.id)]
    }

    def create() {
        
    }
def save() {
        def novopar = new Fase2()
        novopar.content = params.content 
        novopar.school = params.school
        novopar.exp = params.exp
        novopar.bonus = params.bonus.toInteger()
        novopar.ownerId = session.user.id
        novopar.taskId = session.taskId
        novopar.save()

        redirect controller: "Fase2", action: "index"
    }

    def toJson() {
        def personagens = ["Severino Bonfim","Amineh Faryd","Antônio Pinheiro","Mary Santos","Carlos Eduardo Seixas",
			   "Viviane Bonfim","João Francisco Souza","Sérgio Dias","Renata Santos Mantovani","Luciane Bandeira",
			   "Vera Spadaccio","Jonathan Bonfim dos Santos","Márcia Cristina da Silva","Rosy Gonçalves",
			   "André Carvalho","Flávio Batista","William McIntosh","Ana Lucia Amaral","Júlio Tanaka",
			   "Eliomar Azevedo","Érica Souza Bonfim","Ricardo José Pinheiro","Fernanda Moreira",
			   "Ramon Santana de Souza","Elisângela Santana","Tadeu Dourado","Marcia Bonfim",
			   "Aline dos Santos Carvalho","Genivado Andrade Júnior","Cauã Carvalho Bonfim","Renata Farias",
			   "Bruno Albuquerque","Luana Maciel","Francis Alencar","Andrea Batista","Gabriela Oliveira",
			   "José Antônio Ezequiel","Fabiana Garcia","Thiago Sales","Pedro dos Santos","Bruna Andrade",
			   "Adelson Pereira","Edilane Menezes","Gercínia Batista","Thales de Azevedo","Juvenal Almeida",
			   "Juliano Anchieta","Patrícia Leal","Rudá Tibiriçá","Rafaelli dos Santos","Márcio Borges"]

        def idades = [20,27,34,28,25,27,22,26,28,30,45,25,21,43,25,30,37,20,23,25,19,26,24,19,19,31,20,38,25,18,38,26,
		  26,37,28,25,24,26,29,22,33,23,31,48,21,28,34,21,24,26,44]

        def contador = 0   
        def list = Fase2.getAll(params.id ? params.id.split(',').toList() : null)
        def builder = new JsonBuilder()
        def json = builder(["personagens": 
                list.collect { p ->
                    ["nome" : personagens[contador],
                    "idade" : idades[contador++],
                    "formação"     : p.getContent(),
                    "cursos"        : p.getSchool(),
                    "experiência": p.getExp(),
                    "pontuação": p.getBonus(),
                    "pcd" : false]
                }
        ])

        log.debug builder.toString()

        def dataPath = servletContext.getRealPath("/data")
        def userPath = new File(dataPath, "/" + springSecurityService.getCurrentUser().getId() + "/" + session.taskId)
        userPath.mkdirs()

        def fileName = "fase2.json"
        File original = new File("${dataPath}/modelo.json")
        File novo = new File("$userPath/$fileName");
        Files.copy(original.toPath(), novo.toPath());
        FileWriter fw = new FileWriter (novo, true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println  ("\"tempoTotal\":${params.time}, ");
        pw.println  ("\"personagens\": [")
        list.eachWithIndex { p, index -> 
        pw.println  ("{\"nome\": \"${personagens[contador]}\",")
        pw.println  ("\"idade\": ${idades[contador++]},")
        pw.println  ("\"formação\":      \"${p.getContent()}\",")
        pw.println  ("\"cursos\":        \"${p.getSchool()}\",")
        pw.println  ("\"experiência\":  \"${p.getExp()}\" ,")
        pw.println  ("\"pontuação\":    ${p.getBonus()} ,")
        pw.println  ("\"pcd\" : \"false\" }")
        if (index+1 != list.size()){
        pw.println  (",")

        }
        }
        pw.write("]}");
        pw.close();

        String id = MongoHelper.putFile(novo.absolutePath)

        def port = request.serverPort
        if (Environment.current == Environment.DEVELOPMENT) {
        port = 8080
        }

        redirect uri: "http://${request.serverName}:${port}/process/task/complete/${session.taskId}", params: [files: id]
    }
    
    @Transactional
    def delete(Fase2 questionInstance) {
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
