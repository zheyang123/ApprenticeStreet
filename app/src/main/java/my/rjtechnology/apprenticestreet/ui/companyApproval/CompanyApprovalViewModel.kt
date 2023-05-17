package my.rjtechnology.apprenticestreet.ui.companyApproval

import androidx.lifecycle.ViewModel
import my.rjtechnology.apprenticestreet.models.CompanyApproval
import my.rjtechnology.apprenticestreet.models.LearningOutcome

class CompanyApprovalViewModel:ViewModel() {
val approvalList = ArrayList<CompanyApproval>()
var learningOutcome = ArrayList<LearningOutcome>()
var id=""
}