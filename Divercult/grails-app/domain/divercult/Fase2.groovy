package divercult

class Fase2 {

    long ownerId
    String taskId
    String content
    String school
    String exp
    int bonus

    static constraints = {
        ownerId     blank: false, nullable: false
        taskId      nullable: true
        content     blank: false, nullable: false
        school     blank: false, nullable: false
        exp  blank: false, nullable: false
    }
}
