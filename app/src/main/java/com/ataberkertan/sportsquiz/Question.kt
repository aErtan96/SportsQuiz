package com.ataberkertan.sportsquiz

class Question{
    var question:String?= null
        get() = field
        set(value) {
            field = value
        }
    var option1:String?= null
        get() = field
        set(value) {
            field = value
        }
    var option2:String?= null
        get() = field
        set(value) {
            field = value
        }
    var option3:String?= null
        get() = field
        set(value) {
            field = value
        }
    var option4:String?= null
        get() = field
        set(value) {
            field = value
        }
    var answer:String?= null
        get() = field
        set(value) {
            field = value
        }


    constructor(question:String,option1:String,option2:String,option3:String,option4:String,answer:String){
        this.question = question
        this.option1 = option1
        this.option2 = option2
        this.option3 = option3
        this.option4 = option4
        this.answer = answer

    }

}
