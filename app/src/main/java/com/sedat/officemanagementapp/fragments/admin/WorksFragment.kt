package com.sedat.officemanagementapp.fragments.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sedat.officemanagementapp.adapter.admin.WorksAdapter
import com.sedat.officemanagementapp.constants.CustomAlertDialog
import com.sedat.officemanagementapp.constants.PopupMenu
import com.sedat.officemanagementapp.constants.ShowToast
import com.sedat.officemanagementapp.databinding.FragmentWorksBinding
import com.sedat.officemanagementapp.listener.AlertDialogButtonListener
import com.sedat.officemanagementapp.listener.PopupMenuItemClickListener
import com.sedat.officemanagementapp.core.model.Department
import com.sedat.officemanagementapp.core.model.User
import com.sedat.officemanagementapp.core.model.Work
import com.sedat.officemanagementapp.core.model.WorksAndStatus
import com.sedat.officemanagementapp.viewmodel.admin.WorksViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorksFragment : Fragment(), PopupMenuItemClickListener, AlertDialogButtonListener {

    private var _binding: FragmentWorksBinding ?= null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: WorksAdapter
    @Inject
    lateinit var showToast: ShowToast
    private val viewModel: WorksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            recyclerWorks.adapter = adapter

            swipeRefreshWorks.setOnRefreshListener {
                viewModel.getAllWork()
            }

            fabAddWork.setOnClickListener {
                CustomAlertDialog().showAlertDialog(
                    requireContext(),
                    1,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "İş adı girin...",
                    this@WorksFragment
                )
            }
        }

        adapter.onMoreBtnClick { work, btnView ->
            PopupMenu().showPopUpForMoreBtn(
                requireContext(),
                null,
                work,
                null,
                btnView,
                this
            )
        }

        observe()
    }

    private fun observe(){
        viewModel.workList.observe(viewLifecycleOwner){

            adapter.differ.submitList(it)
            //binding.swipeRefreshWorks.isRefreshing = false

        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            if(!it)
                binding.swipeRefreshWorks.isRefreshing = false
        }
    }

    private fun updateWork(work: Work, newWorkName: String){
        val newWork = Work(work.id, newWorkName)
        viewModel.updateWork(newWork)
    }

    private fun insertWork(workName: String){
        viewModel.insertWork(workName).observe(viewLifecycleOwner){
            if(it.code() == 200 && it.body() != null)
                viewModel.getAllWork()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun deleteWithPopup(department: Department?, work: Work?, user: User?) {
        if(work != null){
            viewModel.deleteWork(work).observe(viewLifecycleOwner) {
                if(it.code() == 200 && it.body() != null){
                    viewModel.getAllWork()
                    showToast.show("İş silindi")
                }
                else if(it.code() == 404 )
                    showToast.show("İş bulunamadı")
                else
                    showToast.show(it.message())
            }
        }
    }

    override fun updateWithPopup(department: Department?, work: Work?, user: User?) {
        CustomAlertDialog().showAlertDialog(
            requireContext(),
            2,
            null,
            null,
            work,
            null,
            null,
            "İş adı girin...",
            this
        )
    }

    override fun insertAccess(department: Department?) {
        return
    }

    override fun insertWorkToDepartment(department: Department?) {
        return
    }

    override fun logOutBtnClick() {
        return
    }

    override fun reportBtnClick(worksAndStatus: WorksAndStatus) {
        return
    }

    override fun deleteDepartmentAndWorkBtnClick(worksAndStatus: WorksAndStatus) {
        return
    }

    override fun doneBtnClick(worksAndStatus: WorksAndStatus) {
        return
    }

    override fun updateWithAlertDialog(
        type: Int,
        text: String?,
        department: Department?,
        work: Work?
    ) {
        if(text != null && work != null)
            updateWork(work, text)
    }

    override fun insertWithAlertDialog(type: Int, text: String?) {
        if(text != null)
            insertWork(text)
    }
}