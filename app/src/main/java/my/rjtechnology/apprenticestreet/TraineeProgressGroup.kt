package my.rjtechnology.apprenticestreet

class TraineeProgressGroup {
    var groupName:String=""
    val progress = ArrayList<TraineeProgress>()



    fun addProgress(name:String){
        val progressRam=TraineeProgress()
       progressRam.progressName=name
        progress.add(progressRam)
    }
}