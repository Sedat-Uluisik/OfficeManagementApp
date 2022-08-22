package com.sedat.officemanagementapp.ui.department

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sedat.officemanagementapp.R
import com.sedat.officemanagementapp.ui.department.adapter.DepartmentAccessAdapter
import com.sedat.officemanagementapp.ui.work.adapter.InsertWorkForDepartmentAdapter
import com.sedat.officemanagementapp.constants.CustomAlertDialog
import com.sedat.officemanagementapp.constants.ShowToast
import com.sedat.officemanagementapp.listener.AlertDialogButtonListener
import com.sedat.officemanagementapp.listener.PopupMenuItemClickListener
import com.sedat.officemanagementapp.core.model.Department
import com.sedat.officemanagementapp.core.model.User
import com.sedat.officemanagementapp.core.model.Work
import com.sedat.officemanagementapp.core.model.WorksAndStatus
import com.sedat.officemanagementapp.databinding.BottomSheetDialogDepartmentAccessBinding
import com.sedat.officemanagementapp.databinding.FragmentDepartmentsBinding
import com.sedat.officemanagementapp.ui.department.adapter.DepartmentAdapter
import com.sedat.officemanagementapp.viewmodel.admin.DepartmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DepartmentsFragment : Fragment(), PopupMenuItemClickListener, AlertDialogButtonListener {

    private var _binding: com.sedat.officemanagementapp.databinding.FragmentDepartmentsBinding?= null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: DepartmentAdapter
    @Inject
    lateinit var adapterAccess: DepartmentAccessAdapter
    @Inject
    lateinit var adapterInsertWork: InsertWorkForDepartmentAdapter
    @Inject
    lateinit var showToast: ShowToast

    private val viewModel : DepartmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepartmentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            recyclerDepartment.adapter = adapter
            swipeRefreshDepartment.setOnRefreshListener {
                //viewModel.getAllDepartments()
                observe()
            }

            fabAddDepartment.setOnClickListener {
                CustomAlertDialog().showAlertDialog(
                    requireContext(),
                    1,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "Departman ekle...",
                    this@DepartmentsFragment
                )
            }
        }

        adapter.onMoreBtnClick { department, btn_view ->
            com.sedat.officemanagementapp.constants.PopupMenu().showPopUpForMoreBtn(
                requireContext(),
                department,
                null,
                null,
                btn_view,
                this
            )
        }

        observe()
    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.departmentState.collect{

                it.data?.let { list ->

                    adapter.differ.submitList(list)
                    binding.swipeRefreshDepartment.isRefreshing = false
                }
            }
        }
    }

    private fun showBottomSheetDialog(
        department: Department,
        //true/false değerine göre recyclerView'da departmanları ya da işleri listelemek için kullanılıyor.
        //true -> list departments - false -> list works
        departmentOrWork: Boolean
    ){
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetTheme)
        val view = BottomSheetDialogDepartmentAccessBinding.inflate(LayoutInflater.from(requireContext()))

        with(view){
            departmentName.text = getString(R.string.department_name, department.name)
            if(departmentOrWork){
                recyclerAccess.adapter = adapterAccess
                adapterAccess.selectedItemList = arrayListOf()
                //adapterAccess.differ.submitList(viewModel.departmentList.value)
                viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                    viewModel.departmentState.collect{
                        it.data?.let { list ->
                            adapterAccess.differ.submitList(list)
                        }
                    }
                }
            }else{
                recyclerAccess.adapter = adapterInsertWork
                adapterInsertWork.selectedItemList = arrayListOf()
                viewModel.getAllWorks().observe(viewLifecycleOwner){
                    if(it.isNotEmpty())
                        adapterInsertWork.differ.submitList(it)
                }
            }

            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            insertBtn.setOnClickListener {
                if(departmentOrWork){
                    if(adapterAccess.selectedItemList.isNotEmpty()){
                        viewModel.insertDepartmentAccess(
                            department.id,
                            adapterAccess.selectedItemList.toList()).observe(viewLifecycleOwner){
                            if(it.code() == 200 && it.body() != null){
                                showToast.show("Erişimler eklendi")
                                dialog.dismiss()
                            }
                            else
                                showToast.show("Bir hata oluştu")
                        }
                    }else
                        showToast.show("Lütfen departman seçiniz")
                }else{
                    if(adapterInsertWork.selectedItemList.isNotEmpty()){
                        viewModel.insertWorkForDepartment(
                            department.id,
                            adapterInsertWork.selectedItemList.toList()
                        ).observe(viewLifecycleOwner){
                            if(it == 200){
                                showToast.show("İşler eklendi")
                                dialog.dismiss()
                            }
                            else
                                showToast.show("Bir hata oluştu")
                        }
                    }else
                        showToast.show("Lütfen bir iş seçiniz")
                }
            }
        }

        dialog.setCancelable(true)
        dialog.setContentView(view.root)
        dialog.show()
    }

    private fun updateDepartment(department: Department, newDepartmentName: String){
        val newDepartment = Department(department.id, newDepartmentName)
        viewModel.updateDepartment(newDepartment)
    }

    private fun insertDepartment(departmentName: String){
        viewModel.insertDepartment(departmentName).observe(viewLifecycleOwner){
            if(it.isSuccessful && it.body() != null && it.code() == 200){
                viewModel.getAllDepartments()
                showToast.show("Departman eklendi.")
            }else if(it.code() == 409)
                showToast.show("Departman zaten mevcut.")
            else
                showToast.show(it.message())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun deleteWithPopup(department: Department?, work: Work?, user: User?) {
        if(department != null){
            viewModel.deleteDepartment(department).observe(viewLifecycleOwner) {
                if(it.code() == 200){
                    viewModel.getAllDepartments()
                    showToast.show("Silindi")
                }else if(it.code() == 404)
                    showToast.show("Departman bulunamadı")
            }
        }
    }

    override fun updateWithPopup(department: Department?, work: Work?, user: User?) {
        CustomAlertDialog().showAlertDialog(
            requireContext(),
            2,
            department,
            null,
            null,
            null,
            null,
            "Departman ekle...",
            this
        )
    }

    override fun insertAccess(department: Department?) {
        if(department != null){
            showBottomSheetDialog(department, true) //insert department
        }
    }

    override fun insertWorkToDepartment(department: Department?) {
        if(department != null){
            showBottomSheetDialog(department, false) //insert work
        }
    }

    override fun logOutBtnClick() {
        TODO("Not yet implemented")
    }

    override fun reportBtnClick(worksAndStatus: WorksAndStatus) {
        TODO("Not yet implemented")
    }

    override fun deleteDepartmentAndWorkBtnClick(worksAndStatus: WorksAndStatus) {
        TODO("Not yet implemented")
    }

    override fun doneBtnClick(worksAndStatus: WorksAndStatus) {
        TODO("Not yet implemented")
    }

    override fun updateWithAlertDialog(
        type: Int,
        text: String?,
        department: Department?,
        work: Work?
    ) {
        if(text != null && department != null)
            updateDepartment(department,text)
    }

    override fun insertWithAlertDialog(
        type: Int,
        text: String?
    ) {
        if(text != null)
            insertDepartment(text)
    }
}