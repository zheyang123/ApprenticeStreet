package my.rjtechnology.apprenticestreet.models

import android.graphics.Bitmap

class CompanyApproval {
    var traineeName=""
    var traineeID=""
    var profileImage =  Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    var job=""
    var jobID = ""
    var learningOutcome = ArrayList<LearningOutcome>()
}