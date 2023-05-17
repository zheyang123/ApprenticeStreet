package my.rjtechnology.apprenticestreet.ui.progress

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import my.rjtechnology.apprenticestreet.AppDatabase
import my.rjtechnology.apprenticestreet.dao.LoginDao
import my.rjtechnology.apprenticestreet.models.AppiledProgress

class ProgressViewModel ( private val application: Application):AndroidViewModel(application){
// ProgressViewModel( private val application: Application , private val onDone: (String) -> Unit ={}) : AndroidViewModel(application){
  //ProgressViewModel( private val application: Application , private val onDone: (String) -> Unit = {}) : AndroidViewModel(application){
//
    var appliedJobList = ArrayList<AppiledProgress>()
    var id = ""
    val haveJob:Boolean = false



}