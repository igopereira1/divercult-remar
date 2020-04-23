package divercult

class Fase1 {

    long ownerId
    String taskId
    String content

    static constraints = {
        ownerId     blank: false, nullable: false
        taskId      nullable: true
        content     blank: false, nullable: false
    }
}
