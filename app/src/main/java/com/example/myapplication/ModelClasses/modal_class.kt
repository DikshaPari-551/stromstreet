package layout

import android.widget.TextView

class modal_class ( chat: String?){
    private var chat:String
    init {
        this.chat=chat!!
    }
    fun getchat(): String? {
        return chat
    }
    fun setchat(text: String?) {
        chat = text!!
    }
}