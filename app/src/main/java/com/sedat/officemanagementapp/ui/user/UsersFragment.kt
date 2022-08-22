package com.sedat.officemanagementapp.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sedat.officemanagementapp.R
import com.sedat.officemanagementapp.ui.user.adapter.UsersAdapter
import com.sedat.officemanagementapp.constants.PopupMenu
import com.sedat.officemanagementapp.databinding.BottomSheetDialogCustomBinding
import com.sedat.officemanagementapp.databinding.FragmentUsersBinding
import com.sedat.officemanagementapp.listener.PopupMenuItemClickListener
import com.sedat.officemanagementapp.core.model.Department
import com.sedat.officemanagementapp.core.model.User
import com.sedat.officemanagementapp.core.model.Work
import com.sedat.officemanagementapp.core.model.WorksAndStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment(), PopupMenuItemClickListener {

    private var _binding: FragmentUsersBinding ?= null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: UsersAdapter
    private val viewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            recyclerUsers.adapter = adapter

            fabUserProfile.setOnClickListener {
               showBottomSheetDialog(null)
            }
        }

        adapter.onMoreBtnClick { user, btnView ->
            PopupMenu().showPopUpForMoreBtn(
                requireContext(),
                null,
                null,
                user,
                btnView,
                this
            )
        }

        observe()
    }

    private fun observe(){
        viewModel.workList.observe(viewLifecycleOwner){
            adapter.differ.submitList(it)
        }
    }

    private fun showBottomSheetDialog(user: User?){
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetTheme)
        val view = BottomSheetDialogCustomBinding.inflate(LayoutInflater.from(requireContext()))

        with(view){

            if(user == null){ //insert new user
                bindBottomSheetDialogForInsertUser(this, dialog)
            }
            else
                bindBottomSheetDialogForUpdateUser(user, this, dialog)
        }

        dialog.setCancelable(true)
        dialog.setContentView(view.root)
        dialog.show()
    }

    private fun bindBottomSheetDialogForUpdateUser(
        user: User,
        bottomSheetBinding: BottomSheetDialogCustomBinding,
        dialog: BottomSheetDialog
    ){
        with(bottomSheetBinding){
            var selectedDepartment: Department?= null
            var userStatus: Int = -1

            btnInsertOrUpdate.text = "Güncelle"

            userName.setText(user.userName)
            password.setText(user.password)

            viewModel.getAllDepartment().observe(viewLifecycleOwner){ list ->
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    list.map { department ->
                        department.name
                    }
                )

                spinnerDepartment.adapter = arrayAdapter

                spinnerDepartment.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                        selectedDepartment = list[position]
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }

                spinnerDepartment.setSelection(
                    list.indices.find {
                        list[it].id == user.departmentId
                    } ?: 1
                )
            }

            btnUser.setOnClickListener {
                userStatus = 1
                changeUserStatusBtnBack(1, btnUser, btnStaff, btnAdmin)
            }
            btnStaff.setOnClickListener {
                userStatus = 2
                changeUserStatusBtnBack(2, btnUser, btnStaff, btnAdmin)
            }
            btnAdmin.setOnClickListener {
                userStatus = 3
                changeUserStatusBtnBack(3, btnUser, btnStaff, btnAdmin)
            }

            when(user.statusId){
                1 -> {
                    changeUserStatusBtnBack(1, btnUser, btnStaff, btnAdmin)
                    userStatus = 1
                }
                2 -> {
                    changeUserStatusBtnBack(2, btnUser, btnStaff, btnAdmin)
                    userStatus = 2
                }
                3 -> {
                    changeUserStatusBtnBack(3, btnUser, btnStaff, btnAdmin)
                    userStatus = 3
                }
            }

            btnInsertOrUpdate.setOnClickListener {
                val username = userName.text.toString()
                val password = password.text.toString()

                if(
                    username.isNotEmpty() &&
                    password.isNotEmpty() &&
                    selectedDepartment != null &&
                    userStatus != -1
                ){
                    val newUser = User(user.id, username, password, selectedDepartment!!.id, userStatus)

                    viewModel.updateUser(newUser)
                }
                dialog.dismiss()
            }
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun bindBottomSheetDialogForInsertUser(
        bottomSheetBinding: BottomSheetDialogCustomBinding,
        dialog: BottomSheetDialog
    ){
        with(bottomSheetBinding){
            btnInsertOrUpdate.text = "Ekle"

            var selectedDepartment: Department?= null
            var userStatus: Int = -1

            viewModel.getAllDepartment().observe(viewLifecycleOwner){
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    it.map { department ->
                        department.name
                    }
                )
                arrayAdapter.setDropDownViewResource(R.layout.spinner_item)

                spinnerDepartment.adapter = arrayAdapter

                spinnerDepartment.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                        selectedDepartment = it[position]
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }
            }

            btnUser.setOnClickListener {
                userStatus = 1
                changeUserStatusBtnBack(1, btnUser, btnStaff, btnAdmin)
            }
            btnStaff.setOnClickListener {
                userStatus = 2
                changeUserStatusBtnBack(2, btnUser, btnStaff, btnAdmin)
            }
            btnAdmin.setOnClickListener {
                userStatus = 3
                changeUserStatusBtnBack(3, btnUser, btnStaff, btnAdmin)
            }

            btnInsertOrUpdate.setOnClickListener {
                val username = userName.text.toString()
                val password = password.text.toString()

                if(
                    username.isNotEmpty() &&
                    password.isNotEmpty() &&
                    selectedDepartment != null &&
                    userStatus != -1
                ){
                    val newUser = User(0, username, password, selectedDepartment!!.id, userStatus)

                    viewModel.insertUser(newUser)
                }
                dialog.dismiss()
            }
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    //kullanıcının admin, kullanıcı durumuna göre buton rengini değiştirir.
    private fun changeUserStatusBtnBack(
        select: Int,
        btnUser: AppCompatButton,
        btnStaff: AppCompatButton,
        btnAdmin: AppCompatButton
    ){
        when (select) {
            1 -> {
                btnUser.setBackgroundResource(R.drawable.button_back2)
                btnStaff.setBackgroundResource(R.drawable.button_back3)
                btnAdmin.setBackgroundResource(R.drawable.button_back3)
            }
            2 -> {
                btnUser.setBackgroundResource(R.drawable.button_back3)
                btnStaff.setBackgroundResource(R.drawable.button_back2)
                btnAdmin.setBackgroundResource(R.drawable.button_back3)
            }
            3 -> {
                btnUser.setBackgroundResource(R.drawable.button_back3)
                btnStaff.setBackgroundResource(R.drawable.button_back3)
                btnAdmin.setBackgroundResource(R.drawable.button_back2)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun deleteWithPopup(department: Department?, work: Work?, user: User?) {
        if(user != null){
            viewModel.deleteUser(user)
        }
    }

    override fun updateWithPopup(department: Department?, work: Work?, user: User?) {
        if(user != null)
            showBottomSheetDialog(user)
    }

    override fun insertAccess(department: Department?) {

    }

    override fun insertWorkToDepartment(department: Department?) {

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
}
