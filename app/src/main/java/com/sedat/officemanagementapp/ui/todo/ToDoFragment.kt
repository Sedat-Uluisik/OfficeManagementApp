package com.sedat.officemanagementapp.ui.todo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.sedat.officemanagementapp.ui.todo.adapter.WorkStatusAdapter
import com.sedat.officemanagementapp.constants.PopupMenu
import com.sedat.officemanagementapp.constants.SharedPref
import com.sedat.officemanagementapp.core.model.*
import com.sedat.officemanagementapp.databinding.FragmentToDoBinding
import com.sedat.officemanagementapp.listener.PopupMenuItemClickListener
import com.sedat.officemanagementapp.utils.Constants.package_name
import com.sedat.officemanagementapp.utils.gone
import com.sedat.officemanagementapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ToDoFragment : Fragment(), PopupMenuItemClickListener {

    private var _binding: FragmentToDoBinding ?= null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapterWorkStatus: WorkStatusAdapter
    private var sharedPreferences: SharedPreferences ?= null

    private val viewModel: WorksStatesViewModel by viewModels()

    private var userLogin: UserLogin?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoBinding.inflate(inflater, container, false)

        sharedPreferences = activity?.getSharedPreferences(package_name, Context.MODE_PRIVATE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences?.let {
            val _userLogin = SharedPref(it).getUserNameAndPassword()

            userLogin = _userLogin
        }

        bind()

        adapterWorkStatus.onItemLongClick { workAndStatus, layoutView ->
            userLogin?.let {

                PopupMenu().showPopupMenuForDeleteOrReportWork(
                    requireContext(),
                    layoutView,
                    workAndStatus,
                    it.status,
                    this
                )
            }
        }

        observe()
        //recyclerViewScrollListener()
    }

    private fun bind(){
        with(binding){
            recyclerWorkStatus.adapter = adapterWorkStatus
            recyclerWorkStatus.setHasFixedSize(true)

            userLogin?.let {
                if(userLogin!!.status == 3 || userLogin!!.status == 2)
                    viewModel.getAllWorkStatus()
                else if(userLogin!!.status == 1)
                    viewModel.getWorkStatusForDepartment(userLogin!!.departmentId)
            }

            refreshLayout.setOnRefreshListener {
                if(userLogin!!.status == 3 || userLogin!!.status == 2)
                    viewModel.getAllWorkStatus()
                else if(userLogin!!.status == 1)
                    viewModel.getWorkStatusForDepartment(userLogin!!.departmentId)
            }
        }
    }

    private fun observe(){
        viewModel.workStatusList.observe(viewLifecycleOwner){
            if(it != null && it.isNotEmpty()){

                userLogin?.let { user ->
                    val name = it.find { worksAndStatus ->
                        user.departmentId == worksAndStatus.idDepartment
                    }
                    name?.let {
                        binding.departmentNameText.visible()
                        binding.departmentNameText.text = name.departmentName
                    } ?: binding.departmentNameText.gone()

                }

                adapterWorkStatus.differ.submitList(it)
                binding.refreshLayout.isRefreshing = false
            }
        }
    }

    /*private fun recyclerViewScrollListener(){
        binding.recyclerWorkStatus.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0 && binding.departmentNameText.isVisible)
                    binding.departmentNameText.visibility = View.GONE
                else if(dy < 0 && !binding.departmentNameText.isVisible)
                    binding.departmentNameText.visibility = View.VISIBLE
            }
        })
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun deleteWithPopup(department: Department?, work: Work?, user: User?) {
        TODO("Not yet implemented")
    }

    override fun updateWithPopup(department: Department?, work: Work?, user: User?) {
        TODO("Not yet implemented")
    }

    override fun insertAccess(department: Department?) {
        TODO("Not yet implemented")
    }

    override fun insertWorkToDepartment(department: Department?) {
        TODO("Not yet implemented")
    }

    override fun logOutBtnClick() {
        TODO("Not yet implemented")
    }

    override fun reportBtnClick(worksAndStatus: WorksAndStatus) {
        userLogin?.let { userLogin ->
            if(worksAndStatus.status != null){
                if (worksAndStatus.status != 0){
                    viewModel.changeWorkStatus(
                        userLogin.departmentId,
                        worksAndStatus.idDepartment!!,
                        worksAndStatus.idWork!!,
                        if(userLogin.status != 2) 0 else 1,
                        userLogin.status
                    )
                }else
                    Toast.makeText(requireContext(), "İş zaten bildirildi.", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    override fun deleteDepartmentAndWorkBtnClick(worksAndStatus: WorksAndStatus) {
        viewModel.deleteDepartmentAndWork(worksAndStatus.idDepartment!!, worksAndStatus.idWork!!)
    }

    override fun doneBtnClick(worksAndStatus: WorksAndStatus) {
        userLogin?.let { userLogin ->
            if(worksAndStatus.status != null){
                if (worksAndStatus.status == 0 && userLogin.status == 2){
                    viewModel.updateWorkStatusToDone(
                        worksAndStatus.idDepartment!!,
                        worksAndStatus.idWork!!,
                        1
                    )
                }
            }
        }
    }
}